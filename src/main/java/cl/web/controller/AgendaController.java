// AgendaController.java - LIMPIO SIN @ExceptionHandler
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
import java.util.TreeMap;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    private static final Logger logger = LoggerFactory.getLogger(AgendaController.class);

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        logger.info("Mostrando formulario de nuevo evento");
        model.addAttribute("evento", new Evento());
        model.addAttribute("titulo", "Registrar Nuevo Evento");
        return "form";
    }

    @PostMapping("/guardar")
    public String guardarEvento(@Valid @ModelAttribute("evento") Evento evento,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        logger.info("Intentando guardar evento: {}", evento != null ? evento.getTitulo() : "null");

        if (result.hasErrors()) {
            logger.warn("Errores de validación detectados:");
            result.getAllErrors().forEach(error ->
                    logger.warn(" - {}", error.getDefaultMessage())
            );
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "Por favor corrija los errores en el formulario");
            return "form";
        }

        if (evento == null) {
            logger.error("Evento recibido es nulo");
            model.addAttribute("evento", new Evento());
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "Error al procesar el evento. Intente nuevamente.");
            return "form";
        }

        if (evento.getFecha() == null) {
            logger.warn("Fecha del evento es nula");
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "La fecha del evento es obligatoria");
            return "form";
        }

        if (evento.getFecha().isBefore(LocalDate.now())) {
            logger.warn("Intento de registrar evento con fecha pasada: {}", evento.getFecha());
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "No se pueden registrar eventos con fechas pasadas");
            return "form";
        }

        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty()) {
            logger.warn("Título vacío detectado");
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "El título del evento es obligatorio");
            return "form";
        }

        if (evento.getDescripcion() == null || evento.getDescripcion().trim().isEmpty()) {
            logger.warn("Descripción vacía detectada");
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "La descripción del evento es obligatoria");
            return "form";
        }

        if (evento.getResponsable() == null || evento.getResponsable().trim().isEmpty()) {
            logger.warn("Responsable vacío detectado");
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "El responsable del evento es obligatorio");
            return "form";
        }

        boolean agregado = agendaService.agregarEvento(evento);

        if (agregado) {
            redirectAttributes.addFlashAttribute("mensaje", "Evento registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            logger.info("Evento guardado exitosamente: {}", evento.getTitulo());
            return "redirect:/agenda/listar";
        } else {
            logger.error("El servicio rechazó el evento: {}", evento.getTitulo());
            model.addAttribute("titulo", "Registrar Nuevo Evento");
            model.addAttribute("error", "No se pudo registrar el evento. Intente nuevamente.");
            return "form";
        }
    }

    @GetMapping("/listar")
    public String listarEventos(Model model) {
        logger.info("Listando eventos agrupados por fecha");

        Map<LocalDate, List<Evento>> eventosAgrupados = agendaService.agruparPorFecha();

        Map<String, List<Evento>> eventosAgrupadosFormateados = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        eventosAgrupados.forEach((fecha, listaEventos) -> {
            if (fecha != null) {
                eventosAgrupadosFormateados.put(fecha.format(formatter), listaEventos);
            } else {
                logger.warn("Fecha nula encontrada en eventos agrupados");
            }
        });

        model.addAttribute("eventosAgrupados", eventosAgrupadosFormateados);
        model.addAttribute("titulo", "Agenda de Eventos");
        model.addAttribute("totalEventos", agendaService.contarEventos());

        return "list";
    }

    @GetMapping("/buscar")
    public String buscarPorFecha(@RequestParam(value = "fecha", required = false) String fechaStr,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        logger.info("Buscando eventos por fecha: {}", fechaStr);

        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            logger.warn("Búsqueda sin parámetro de fecha");
            redirectAttributes.addFlashAttribute("error", "Debe proporcionar una fecha para buscar");
            return "redirect:/agenda/listar";
        }

        LocalDate fecha = LocalDate.parse(fechaStr);

        List<Evento> eventos = agendaService.buscarPorFecha(fecha);

        model.addAttribute("eventos", eventos);
        model.addAttribute("fechaBusqueda", fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.addAttribute("titulo", "Eventos del " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        if (eventos.isEmpty()) {
            model.addAttribute("mensaje", "No hay eventos registrados para esta fecha");
            logger.info("No se encontraron eventos para la fecha: {}", fecha);
        } else {
            logger.info("Se encontraron {} eventos para la fecha: {}", eventos.size(), fecha);
        }

        return "busqueda";
    }

    @GetMapping
    public String index() {
        logger.info("Redirigiendo desde raíz a listar");
        return "redirect:/agenda/listar";
    }
}