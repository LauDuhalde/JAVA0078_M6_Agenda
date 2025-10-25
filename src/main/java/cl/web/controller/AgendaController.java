package cl.web.controller;

import cl.web.modelo.Evento;
import cl.web.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller // Marca esta clase como controlador MVC de Spring
@RequestMapping("/agenda") // Define la ruta base para todas las peticiones
public class AgendaController {

    private static final Logger logger = LoggerFactory.getLogger(AgendaController.class);

    @Autowired // Inyección automática de dependencias por Spring
    private AgendaService agendaService;

    @GetMapping("/nuevo") // Mapea peticiones GET a /agenda/nuevo
    public String mostrarFormulario(Model model) {
        logger.info("Mostrando formulario de nuevo evento");
        model.addAttribute("evento", new Evento());
        model.addAttribute("titulo", "Registrar Nuevo Evento");
        return "form";
    }

    @PostMapping("/guardar") // Mapea peticiones POST a /agenda/guardar
    public String guardarEvento(@Valid @ModelAttribute("evento") Evento evento, // @Valid activa validaciones
                                BindingResult result, // Captura errores de validación
                                RedirectAttributes redirectAttributes,
                                Model model) {

        logger.info("Intentando guardar evento: {}", evento.getTitulo());

        if (result.hasErrors()) {
            logger.warn("Errores de validación al guardar evento");
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            return "form";
        }

        boolean agregado = agendaService.agregarEvento(evento);

        if (agregado) {
            redirectAttributes.addFlashAttribute("mensaje", "Evento registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            logger.info("Evento guardado: {}", evento.getTitulo());
            return "redirect:/agenda/listar";
        } else {
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "No se pudo registrar el evento");
            return "form";
        }
    }

    @GetMapping("/listar") // Mapea peticiones GET a /agenda/listar
    public String listarEventos(Model model) {
        logger.info("Listando eventos agrupados por fecha");

        Map<LocalDate, List<Evento>> eventosAgrupados = agendaService.agruparPorFecha();

        model.addAttribute("eventosAgrupados", eventosAgrupados);
        model.addAttribute("titulo", "Agenda de Eventos");
        model.addAttribute("totalEventos", agendaService.contarEventos());

        return "list";
    }

    @GetMapping("/buscar") // Mapea peticiones GET a /agenda/buscar
    public String buscarPorFecha(@RequestParam(value = "fecha", required = false) String fechaStr, // @RequestParam extrae parámetros de URL
                                 Model model) {

        logger.info("Buscando eventos por fecha: {}", fechaStr);

        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return "redirect:/agenda/listar";
        }

        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            List<Evento> eventos = agendaService.buscarPorFecha(fecha);

            model.addAttribute("eventos", eventos);
            model.addAttribute("fechaBusqueda", fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            model.addAttribute("titulo", "Eventos del " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            if (eventos.isEmpty()) {
                model.addAttribute("mensaje", "No hay eventos para esta fecha");
            }

        } catch (Exception e) {
            logger.error("Error al parsear fecha: {}", fechaStr);
            model.addAttribute("error", "Fecha inválida");
        }

        return "busqueda";
    }

    @GetMapping // Mapea peticiones GET a /agenda
    public String index() {
        return "redirect:/agenda/listar";
    }
}