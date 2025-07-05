package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;
import app.scaffolding.common.Calidad;
import app.scaffolding.common.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DummyServiceImplTest {
    DummyServiceImpl sut; //System Under Test
    DummyRepository dummyRepository;

    @BeforeEach
    void setUp() {
        dummyRepository = mock(DummyRepository.class);
        sut = new DummyServiceImpl(dummyRepository);
    }

    @Test
    void getAll_shouldReturnAllDummys() {
        // Arrange: armamos dummys simulados que devuelve el repo
        Dummy dummy = Dummy.builder().id(1L).dummyField("dummy1").calidad(Calidad.BASICO).build();
        Dummy dummy2 = Dummy.builder().id(2L).dummyField("dummy2").calidad(Calidad.BASICO).build();
        List<Dummy> dummyList = List.of(dummy, dummy2);
        // asignamos comportamiento al mock del repo
        when(dummyRepository.findAll()).thenReturn(dummyList);
        // Act: llamamos al metodo a testear
        List<DummyResponseDto> result = sut.getAll();
        // Assert: Verificar que se hayan hecho bien los mapeos
        assertEquals(2, result.size());
        assertEquals("dummy1", result.get(0).getDummyField());
        assertEquals(Calidad.BASICO, result.get(0).getCalidad());
        assertEquals("dummy2", result.get(1).getDummyField());
        assertEquals(Calidad.BASICO, result.get(1).getCalidad());
    }

    @Test
    void getById_shouldReturnDummyResponse_whenDummyExists() { // Test cuando el recurso existe -> retornar el dtoResponse
        // Arrange
        Long id = 1L;
        Dummy dummy = Dummy.builder()
                .id(id)
                .dummyField("Campo test")
                .dummyFecha(LocalDate.of(2025, 7, 3))
                .build();

        when(dummyRepository.findById(id)).thenReturn(Optional.of(dummy));

        // Act
        DummyResponseDto result = sut.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Campo test", result.getDummyField());
        assertEquals(LocalDate.of(2025, 7, 3), result.getDummyFecha());
    }

    @Test
    void getById_shouldThrow_whenDummyNotFound() {
        // Arrange
        Long id = 99L;
        when(dummyRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> sut.getById(id));
        assertTrue(ex.getMessage().contains("Dummy con id " + id + " no encontrado"));
    }

    @Test
    void create_shouldSaveDummyAndReturnResponseDto() {
        // Arrange
        DummyCreateDto dummyCreateDto = DummyCreateDto.builder().dummyField("campoTest").build();

        Dummy dummyToSave = Dummy.builder().dummyField("campoTest").dummyFecha(LocalDate.now()).build();
        Dummy savedDummy = Dummy.builder().id(1L).dummyField("campoTest").dummyFecha(LocalDate.now()).build();

        when(dummyRepository.save(any(Dummy.class))).thenReturn(savedDummy);
        // Act
        DummyResponseDto response = sut.create(dummyCreateDto);
        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("campoTest", response.getDummyField());
        assertEquals(LocalDate.now(), response.getDummyFecha());
        verify(dummyRepository).save(any(Dummy.class));
    }


    @Test
    void update_shouldUpdateDummyAndReturnResponseDto_whenIdExists() {
        // Arrange
        Long id = 1L;
        DummyCreateDto dummyCreateDto = DummyCreateDto.builder().dummyField("nuevo valor").build();
        Dummy existing = Dummy.builder().id(id).dummyField("valor anterior")
                .dummyFecha(LocalDate.of(2023, 1, 1))
                .build();
        Dummy updated = Dummy.builder().id(id).dummyField("nuevo valor")
                .dummyFecha(existing.getDummyFecha()) // la fecha no cambia
                .build();
        when(dummyRepository.findById(id)).thenReturn(Optional.of(existing));
        when(dummyRepository.save(any(Dummy.class))).thenReturn(updated);
        // Act
        DummyResponseDto response = sut.update(id, dummyCreateDto);
        // Assert
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("nuevo valor", response.getDummyField());
        assertEquals(existing.getDummyFecha(), response.getDummyFecha());

        verify(dummyRepository).findById(id);
        verify(dummyRepository).save(existing);
    }


    @Test
    void delete_shouldRemoveDummy_whenIdExists() {
        // Arrange
        Long id = 1L;
        Dummy dummy = Dummy.builder()
                .id(id)
                .dummyField("valor")
                .dummyFecha(LocalDate.now())
                .build();

        when(dummyRepository.findById(id)).thenReturn(Optional.of(dummy));
        doNothing().when(dummyRepository).delete(dummy);

        // Act
        sut.delete(id);

        // Assert
        verify(dummyRepository).findById(id);
        verify(dummyRepository).delete(dummy);
    }

}