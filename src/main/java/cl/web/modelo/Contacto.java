package cl.web.modelo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
/*
 * Clase que representa un contacto.
 * se usa lombok para crear un objeto con sus atributos.
 *Data es para que se puedan usar los getters y setters.
 * Noargs es para que se puedan crear objetos sin atributos.
 * AllArgsConstructor es para que se puedan crear objetos con todos sus atributos.
 * component es para que se pueda registrar en el contexto de spring.
 */

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    @NotEmpty(message = "El nombre es obligatorio")
    private String nombre;

    @NotEmpty(message = "El correo es obligatorio")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String correo;

    @NotEmpty(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono debe contener entre 9 y 15 dígitos")
    private String telefono;
}