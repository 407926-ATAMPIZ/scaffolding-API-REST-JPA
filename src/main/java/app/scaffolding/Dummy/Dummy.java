package app.scaffolding.Dummy;

import jakarta.persistence.*;
import lombok.*;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "dummy_field", nullable = false)
    private String dummyField;

}
