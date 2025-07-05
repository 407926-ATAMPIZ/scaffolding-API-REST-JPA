package app.scaffolding.Dummy.dto;

import app.scaffolding.Dummy.Dummy;
import app.scaffolding.common.Calidad;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Dto de ejemplo de la clase Dummy.
 * Es un objeto de transferencia de datos (DTO) que se utiliza para transferir solo los datos necesarios desde o hacia la capa de presentación.
 * DTO for {@link Dummy}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DummyCreateDto implements Serializable {
    @Size(message = "Este campo debe tener entre 1 y 10 digitos", min = 1, max = 10)
    @NotBlank(message = "Este campo es obligatorio")
    @JsonProperty("dummy_field")
    String dummyField;
    
    @NotNull(message = "La calidad no puede ser null")
    private Calidad calidad;
}