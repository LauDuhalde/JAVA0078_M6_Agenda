package cl.web.modelo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.stereotype.Component;
import jakarta.validation.constraints.NotEmpty;


import java.time.LocalDate;

@Data // Genera getters, setters, toString, equals
@NoArgsConstructor // Genera constructor vacío
@AllArgsConstructor // Genera constructor con todos los parámetros
//@Component // Marca esta clase como bean de Spring
public class Evento {

	@NotEmpty(message = "El título es obligatorio")
    private String titulo;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotEmpty(message = "La descripción es obligatoria")
    private String descripcion;

    @NotEmpty(message = "El responsable es obligatorio")
    private String responsable;
}

