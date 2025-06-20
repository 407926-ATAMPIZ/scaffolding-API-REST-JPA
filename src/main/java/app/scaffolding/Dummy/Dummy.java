package app.scaffolding.Dummy;

import jakarta.persistence.*;

/**
 * Clase de dominio o entidad de ejemplo. Representa una tabla de la base de datos
 */
@Entity
public class Dummy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String dummyField;
}
