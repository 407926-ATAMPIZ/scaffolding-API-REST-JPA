package app.scaffolding.Dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase de servicio de ejemplo para la clase Dummy.
 * Implementa todos los métodos de la interfaz DummyService.
 */
@Service
public class DummyServiceImpl implements DummyService{

    private final DummyRepository dummyRepository;

    public DummyServiceImpl(final DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    @Override
    public List<Dummy> getDummy() {
        return null;
    }

    @Override
    public Dummy getDummyById(Long id) {
        return null;
    }

    @Override
    public Dummy createDummy(DummyDto dummyDto) {
        return null;
    }

    @Override
    public Dummy updateDummy(DummyDto dummyDto) {
        return null;
    }

    @Override
    public void deleteDummy(Long id) {

    }
}
