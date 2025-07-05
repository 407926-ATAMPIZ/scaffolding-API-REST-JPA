package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyResponseDto;
import app.scaffolding.common.Calidad;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Clase de dominio o entidad de ejemplo. Representa una tabla de la base de datos
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Dummy {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Calidad calidad;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dummy_field", nullable = false)
    private String dummyField;

    @Column(name = "dummy_fecha", nullable = false)
    private LocalDate dummyFecha;
}
