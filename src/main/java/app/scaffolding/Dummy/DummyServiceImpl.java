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
                map(this::toResponseDto).toList();
        return dummyResponseDtoList;
    }

    @Override
    public DummyResponseDto getById(Long id) {
        Dummy dummy = dummyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dummy con id " + id + " no encontrado."));
        DummyResponseDto mapped = this.toResponseDto(dummy);
        return mapped;
    }

    @Override
    public DummyResponseDto create(DummyCreateDto dummyDto) {
        //mapear a entity
        Dummy dummy = this.toDummy(dummyDto);
        //guardar
        dummy = dummyRepository.save(dummy);
        //mapear a dto
        DummyResponseDto mapped = this.toResponseDto(dummy);
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
        DummyResponseDto mapped = this.toResponseDto(dummy);
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

    /**
     * @param dummyField
     * @param fromDate
     * @return
     */
    @Override
    public List<DummyResponseDto> search(String dummyField, LocalDate fromDate) {
        List<Dummy> dummyList;
        if(dummyField != null && fromDate != null) {
            dummyList = dummyRepository.findByDummyFieldContainingAndDummyFechaBefore(dummyField, fromDate);
        }else if(dummyField == null && fromDate == null) {
            return getAll();
        }else if(dummyField != null){
            dummyList = dummyRepository.findByDummyFieldContaining(dummyField);
        }else {
            dummyList = dummyRepository.findByDummyFechaBefore(fromDate);
        }
        return dummyList.stream().map(this::toResponseDto).toList();
    }

    private DummyResponseDto toResponseDto(Dummy dummy) {
        return DummyResponseDto.builder().id(dummy.getId()).dummyField(dummy.getDummyField()).dummyFecha(dummy.getDummyFecha()).build();
    }

    private Dummy toDummy(DummyCreateDto dummyCreateDto) {
        return Dummy.builder().dummyField(dummyCreateDto.getDummyField()).dummyFecha(LocalDate.now()).build();
    }
}
