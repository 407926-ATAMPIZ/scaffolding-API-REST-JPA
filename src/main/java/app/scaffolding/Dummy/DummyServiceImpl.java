package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        List<DummyResponseDto> dummyResponseDtoList = dummyList.stream().
                map(o -> {
                    DummyResponseDto mapped = DummyResponseDto.builder()
                            .id(o.getId())
                            .dummyField(o.getDummyField())
                            .build();
                    return mapped;
                }).toList();
        return dummyResponseDtoList;
    }

    @Override
    public DummyResponseDto getById(Long id) {
        Dummy dummy = dummyRepository.findById(id).orElse(null);
        if (dummy == null) {
            return null;
        }
        DummyResponseDto mapped = DummyResponseDto.builder()
                .id(dummy.getId())
                .dummyField(dummy.getDummyField())
                .build();
        return mapped;
    }

    @Override
    public DummyResponseDto create(DummyCreateDto dummyDto) {
        Dummy dummy = Dummy.builder()
                .dummyField(dummyDto.getDummyField())
                .build();
        dummy = dummyRepository.save(dummy);
        DummyResponseDto mapped = DummyResponseDto.builder()
                .id(dummy.getId())
                .dummyField(dummy.getDummyField())
                .build();
        return mapped;
    }

    @Override
    public DummyResponseDto update(Long id, DummyCreateDto dummyDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
