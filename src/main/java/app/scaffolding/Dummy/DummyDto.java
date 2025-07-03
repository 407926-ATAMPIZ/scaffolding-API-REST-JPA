package app.scaffolding.Dummy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Dto de ejemplo de la clase Dummy.
 * Es un objeto de transferencia de datos (DTO) que se utiliza para transferir solo los datos necesarios desde o hacia la capa de presentación.
 * DTO for {@link Dummy}
 */
@Data
@Builder
public class DummyDto implements Serializable {
    @Size(message = "Este campo debe tener entre 1 y 10 digitos", min = 1, max = 10)
    @NotBlank(message = "Este campo es obligatorio")
    String dummyField;
}