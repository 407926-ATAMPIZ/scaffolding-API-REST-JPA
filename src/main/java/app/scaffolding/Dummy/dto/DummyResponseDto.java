package app.scaffolding.Dummy.dto;

import app.scaffolding.Dummy.Dummy;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Dto de ejemplo de la clase Dummy.
 * Es un objeto de transferencia de datos (DTO) que se utiliza para transferir solo los datos necesarios hacia la capa de presentación.
 * DTO for {@link Dummy}
 */
@Data
@Builder
public class DummyResponseDto implements Serializable {
    Long id;
    String dummyField;
}