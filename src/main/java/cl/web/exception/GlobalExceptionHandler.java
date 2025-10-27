package cl.web.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeParseException;

// Clase para manejar excepciones globales
// la anotacion @ControllerAdice indica que esta clase se aplica a todas las clases que tengan la anotacion @Controller
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DateTimeParseException.class)
    public String handleDateTimeParseException(DateTimeParseException e,
                                               RedirectAttributes redirectAttributes) {
        logger.error("Error al parsear fecha: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", "Formato de fecha inválido. Use el formato yyyy-MM-dd");
        return "redirect:/agenda/listar";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException e, Model model) {
        logger.error("Error de puntero nulo: {}", e.getMessage(), e);
        model.addAttribute("error", "Error: Se intentó acceder a un dato inexistente");
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e,
                                                 RedirectAttributes redirectAttributes) {
        logger.error("Argumento ilegal: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", "Datos inválidos proporcionados");
        return "redirect:/agenda/listar";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        logger.error("Excepción no controlada: {}", e.getMessage(), e);
        model.addAttribute("error", "Ha ocurrido un error inesperado. Por favor contacte al administrador.");
        model.addAttribute("detalleError", e.getMessage());
        return "error";
    }
}
