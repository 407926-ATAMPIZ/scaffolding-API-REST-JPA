package app.scaffolding.Dummy;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface de la capa de servicio de ejemplo para la clase Dummy.
 * Esta interfaz define los métodos que deben ser implementados por cualquier clase que la implemente.
 */
public interface DummyService {
    List<Dummy> getDummy();

    Dummy getDummyById(Long id);

    Dummy createDummy(DummyDto dummyDto);

    Dummy updateDummy(DummyDto dummyDto);

    void deleteDummy(Long id);
}
