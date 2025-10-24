package cl.web.controller;

import cl.web.modelo.Contacto;
import cl.web.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
/*
* Clase que representa el controlador de la vista de contactos.
* La anotacion @Controller indica que esta clase es un bean de spring.
* @requestmapping indica que esta clase es un controlador para la ruta /contactos.
* @autowired indica que esta clase necesita autowired para inyectar dependencias.
* @Get, Post, Put, Delete indican que los metodos son de tipo rest.
 */


@Controller
@RequestMapping("/contactos")
public class AgendaController {
    private static final Logger logger = LoggerFactory.getLogger(AgendaController.class);

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        logger.info("Mostrando formulario de nuevo contacto");
        model.addAttribute("contacto", new Contacto());
        model.addAttribute("titulo", "Registrar Nuevo Contacto");
        return "form";
    }

    @PostMapping("/guardar")
    public String guardarContacto(@Valid @ModelAttribute("contacto") Contacto contacto,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        logger.info("Intentando guardar contacto: {}", contacto.getNombre());

        if (result.hasErrors()) {
            logger.warn("Errores de validación al guardar contacto");
            model.addAttribute("titulo", "Registrar Nuevo Contacto");
            return "form";
        }

        boolean registrado = agendaService.registrar(contacto);

        if (registrado) {
            redirectAttributes.addFlashAttribute("mensaje", "Contacto registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            logger.info("Contacto guardado exitosamente: {}", contacto.getNombre());
            return "redirect:/contactos/listar";
        } else {
            model.addAttribute("titulo", "Registrar Nuevo Contacto");
            model.addAttribute("error", "No se pudo registrar el contacto. Es posible que el correo ya esté registrado.");
            logger.error("No se pudo guardar el contacto: {}", contacto.getCorreo());
            return "form";
        }
    }

    @GetMapping("/listar")
    public String listarContactos(Model model) {
        logger.info("Listando todos los contactos");

        List<Contacto> contactos = agendaService.listar();

        model.addAttribute("contactos", contactos);
        model.addAttribute("titulo", "Lista de Contactos");
        model.addAttribute("totalContactos", contactos.size());

        logger.debug("Total de contactos: {}", contactos.size());

        return "list";
    }

    @GetMapping("/buscar")
    public String buscarContacto(@RequestParam(value = "nombre", required = false) String nombre,
                                 Model model) {

        logger.info("Buscando contacto con nombre: {}", nombre);

        if (nombre == null || nombre.trim().isEmpty()) {
            return "redirect:/contactos/listar";
        }

        Optional<Contacto> contactoEncontrado = agendaService.buscarPorNombre(nombre);

        if (contactoEncontrado.isPresent()) {
            List<Contacto> contactos = List.of(contactoEncontrado.get());
            model.addAttribute("contactos", contactos);
            model.addAttribute("mensaje", "Contacto encontrado");
            model.addAttribute("tipoMensaje", "info");
        } else {
            model.addAttribute("contactos", List.of());
            model.addAttribute("error", "No se encontró ningún contacto con ese nombre");
        }

        model.addAttribute("titulo", "Resultados de Búsqueda");
        model.addAttribute("busqueda", nombre);

        return "list";
    }

    @GetMapping
    public String index() {
        logger.info("Acceso a ruta raíz de contactos, redirigiendo a listar");
        return "redirect:/contactos/listar";
    }
}
