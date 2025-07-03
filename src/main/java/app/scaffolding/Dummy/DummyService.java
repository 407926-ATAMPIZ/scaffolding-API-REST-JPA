package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;

import java.util.List;

/**
 * Interface de la capa de servicio de ejemplo para la clase Dummy.
 * Esta interfaz define los métodos que deben ser implementados por cualquier clase que la implemente.
 */
public interface DummyService {
    List<DummyResponseDto> getAll();

    DummyResponseDto getById(Long id);

    DummyResponseDto create(DummyCreateDto dummyDto);

    DummyResponseDto update(DummyCreateDto dummyDto);

    void delete(Long id);
}
