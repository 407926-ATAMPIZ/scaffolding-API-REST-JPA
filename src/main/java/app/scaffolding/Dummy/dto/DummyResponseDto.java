package app.scaffolding.Dummy.dto;

import app.scaffolding.Dummy.Dummy;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Dto de ejemplo de la clase Dummy.
 * Es un objeto de transferencia de datos (DTO) que se utiliza para transferir solo los datos necesarios hacia la capa de presentación.
 * DTO for {@link Dummy}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DummyResponseDto implements Serializable {
    @JsonProperty("id")
    Long id;
    @JsonProperty("dummy_field")
    String dummyField;
}