package app.scaffolding.Dummy.dto;

import app.scaffolding.Dummy.Dummy;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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
    @JsonProperty("fecha") //nombre alternativo al/del que se serializa/deserializa en JSON
    @JsonFormat(pattern = "yyyy-MM-dd") //formato al/del que se serializa/deserializa en JSON (MM MES, EN MINUSCULA ES MINUTOS)
    private LocalDate dummyFecha;
}