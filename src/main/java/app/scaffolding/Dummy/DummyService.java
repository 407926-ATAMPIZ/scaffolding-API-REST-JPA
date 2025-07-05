package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;
import app.scaffolding.common.Calidad;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface de la capa de servicio de ejemplo para la clase Dummy.
 * Esta interfaz define los métodos que deben ser implementados por cualquier clase que la implemente.
 */
@Service
public interface DummyService {
    List<DummyResponseDto> getAll();

    DummyResponseDto getById(Long id);

    DummyResponseDto create(DummyCreateDto dummyDto);

    DummyResponseDto update(Long id, DummyCreateDto dummyDto);

    void delete(Long id);

    List<DummyResponseDto> search(String dummyField, LocalDate fromDate, Calidad calidad);
}
