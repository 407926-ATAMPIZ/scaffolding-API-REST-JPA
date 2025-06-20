package app.scaffolding.Dummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Clase repositorio de ejemplo para la clase Dummy. Expone métodos de acceso a datos implementando JPA.
 */
@Repository
public interface DummyRepository extends JpaRepository<Dummy, Long> {

}
