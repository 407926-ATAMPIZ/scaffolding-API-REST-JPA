package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase de servicio de ejemplo para la clase Dummy.
 * Implementa todos los métodos de la interfaz DummyService.
 */
@Service
public class DummyServiceImpl implements DummyService {

    private final DummyRepository dummyRepository;

    public DummyServiceImpl(final DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    @Override
    public List<DummyResponseDto> getAll() {
        List<Dummy> dummyList = dummyRepository.findAll();

        return null;
    }

    @Override
    public DummyResponseDto getById(Long id) {
        return null;
    }

    @Override
    public DummyResponseDto create(DummyCreateDto dummyDto) {
        return null;
    }

    @Override
    public DummyResponseDto update(DummyCreateDto dummyDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
