package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;
import app.scaffolding.common.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        List<DummyResponseDto> dummyResponseDtoList = dummyList.stream().
                map(o -> {
                    DummyResponseDto mapped = DummyResponseDto.builder()
                            .id(o.getId())
                            .dummyField(o.getDummyField())
                            .dummyFecha(o.getDummyFecha())
                            .build();
                    return mapped;
                }).toList();
        return dummyResponseDtoList;
    }

    @Override
    public DummyResponseDto getById(Long id) {
        Dummy dummy = dummyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dummy con id " + id + " no encontrado."));
        DummyResponseDto mapped = DummyResponseDto.builder()
                .id(dummy.getId())
                .dummyField(dummy.getDummyField())
                .dummyFecha(dummy.getDummyFecha())
                .build();
        return mapped;
    }

    @Override
    public DummyResponseDto create(DummyCreateDto dummyDto) {
        //mapear a entity
        Dummy dummy = Dummy.builder()
                .dummyField(dummyDto.getDummyField())
                .dummyFecha(LocalDate.now())
                .build();
        //guardar
        dummy = dummyRepository.save(dummy);
        //mapear a dto
        DummyResponseDto mapped = DummyResponseDto.builder()
                .id(dummy.getId())
                .dummyField(dummy.getDummyField())
                .dummyFecha(dummy.getDummyFecha())
                .build();
        //retornar
        return mapped;
    }

    @Override
    public DummyResponseDto update(Long id, DummyCreateDto dummyDto) {
        //buscar
        Dummy dummy = dummyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dummy con id " + id + " no encontrado."));
        //actualizar
        dummy.setDummyField(dummyDto.getDummyField());
        //guardar
        dummy = dummyRepository.save(dummy);
        //mapear
        DummyResponseDto mapped = DummyResponseDto.builder()
                .id(dummy.getId())
                .dummyField(dummy.getDummyField())
                .dummyFecha(dummy.getDummyFecha())
                .build();
        //retornar
        return mapped;
    }

    @Override
    public void delete(Long id) {
        //buscar
        Dummy dummy = dummyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dummy con id " + id + " no encontrado."));
        //eliminar
        dummyRepository.delete(dummy);
    }
}
