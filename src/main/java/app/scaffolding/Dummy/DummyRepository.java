package app.scaffolding.Dummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase repositorio de ejemplo para la clase Dummy. Expone métodos de acceso a datos implementando JPA.
 */
@Repository
public interface DummyRepository extends JpaRepository<Dummy, Long> {
    List<Dummy> findByDummyFieldContaining(String dummyField);

    List<Dummy> findByDummyFechaBefore(LocalDate fecha);

    List<Dummy> findByDummyFieldContainingAndDummyFechaBefore(String dummyField, LocalDate fecha);

}
