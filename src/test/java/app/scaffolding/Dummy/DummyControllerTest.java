package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean; //anotación vieja, deprecated
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; //anotación nueva para mockear servicios en tests de controllers ¿?
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DummyController.class) // Levanta el contexto de Spring solo para el DummyController, sin cargar toda la aplicación.
class DummyControllerTest {

    @Autowired
    private MockMvc mockMvc; //Proporciona un objeto para hacer peticiones HTTP simuladas al controller.

    @MockitoBean // Crea un mock de DummyService que será inyectado automáticamente en el controller.
    private DummyService dummyService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos Java a JSON para usar en POST o PUT.

    @Test
    void getAll_shouldReturnListOfDummies() throws Exception {
        DummyResponseDto dummy1 = DummyResponseDto.builder().id(1L).dummyField("unDummy").build();
        DummyResponseDto dummy2 = DummyResponseDto.builder().id(2L).dummyField("otroDummy").build();

        // Testea Que el endpoint /api/v1/dummies devuelva una lista con los elementos mockeados.
        when(dummyService.getAll()).thenReturn(List.of(dummy1, dummy2));
        mockMvc.perform(get("/api/v1/dummies"))
                .andExpect(status().isOk())
                // Verifica que efectivamente devuelve dos objetos, lo cual prueba la respuesta JSON por el largo.
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void create_shouldReturnCreatedDummy() throws Exception {
        DummyCreateDto request = DummyCreateDto.builder().dummyField("nuevo").build();
        DummyResponseDto response = DummyResponseDto.builder().id(10L).dummyField("nuevo").build();

        when(dummyService.create(any())).thenReturn(response);
        // Testea se pueda crear un nuevo recurso correctamente
        mockMvc.perform(post("/api/v1/dummies") // Hacemos la request
                        .contentType(MediaType.APPLICATION_JSON) //definir que enviamos JSON
                        .content(objectMapper.writeValueAsString(request))) // La entidad como JSON en el body.
                //Testeamos el httpstatus
                .andExpect(status().isCreated())
                //el header devuelto (en este caso devuelvo la nueva URI)
                .andExpect(header().string("Location", "/api/v1/dummies/10"))
                //que los campos coincidan (aca hay uno solo, testeamos ese)
                .andExpect(jsonPath("$.dummy_field").value("nuevo"));
    }

    @Test
    void getById_shouldReturnDummy_whenExists() throws Exception {
        DummyResponseDto response = DummyResponseDto.builder().id(5L).dummyField("dummyEncontrado").build();

        when(dummyService.getById(5L)).thenReturn(response);
        //Acceso exitoso a un recurso existente por ID.
        mockMvc.perform(get("/api/v1/dummies/5"))
                // testeamos que el httpstatus sea ok
                .andExpect(status().isOk())
                // y el campo del recurso
                .andExpect(jsonPath("$.dummy_field").value("dummyEncontrado"));
    }

    @Test
    void getById_shouldReturn404_whenNotExists() throws Exception {
        when(dummyService.getById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/dummies/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        //hacemos DELETE y validamos que la repsuesta sea HTTP no content
        mockMvc.perform(delete("/api/v1/dummies/1"))
                .andExpect(status().isNoContent());
        //verificamos que se llame al dummyService.delete con el id pasado por parámetro
        Mockito.verify(dummyService).delete(1L);
    }
}
