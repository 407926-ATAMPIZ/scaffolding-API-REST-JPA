package app.scaffolding.Dummy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase de dominio o entidad de ejemplo. Representa una tabla de la base de datos
 */
@Getter
@Setter
@Entity
public class Dummy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "dummy_field", nullable = false)
    private String dummyField;

}
