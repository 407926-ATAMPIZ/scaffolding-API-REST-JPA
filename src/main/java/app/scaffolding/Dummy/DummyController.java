package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase controller de ejemplo. Expone los endpoints de la API REST.
 */
@RestController // Marca la clase como un controller REST, que retorna directamente JSON en las respuestas.
@RequestMapping("/api/v1/dummies") // Define el prefijo común para todos los endpoints de esta clase.
@CrossOrigin // Permite peticiones desde otros orígenes (para desarrollo frontend separado).
public class DummyController {

    // Se usa inyección por constructor en lugar de @Autowired.
    // Es más seguro (evita nulls), facilita testing, y es la forma recomendada en Spring moderno.
    private final DummyService dummyService;

    // Inyección por constructor recomendada para claridad y testeo.
    public DummyController(final DummyService dummyService) {
        this.dummyService = dummyService;
    }

    // Aquí se pueden definir los endpoints de la API REST utilizando anotaciones como @GetMapping, @PostMapping, etc.
    // Ejemplo de un endpoint:
    @Operation(summary = "Lista todos los dummies presentes en la db") //@Operation mejora la documentación Swagger.
    @GetMapping
    public ResponseEntity<List<DummyResponseDto>> getAll() {
        List<DummyResponseDto> dummyList = dummyService.getAll();
        return ResponseEntity.ok(dummyList);
    }

    @Operation(summary = "Busca un dummy por id en la db")
    @GetMapping("/{id}")
    public ResponseEntity<DummyResponseDto> getById(
            @PathVariable Long id) {
        DummyResponseDto dummy = dummyService.getById(id);
        return ResponseEntity.ok().body(dummy); // 200 OK si existe
    }

    @GetMapping("/search")
    public ResponseEntity<List<DummyResponseDto>> searchDummies(
            @RequestParam(required = false) String dummyField,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate
    ) {
        List<DummyResponseDto> result = dummyService.search(dummyField, fromDate);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Crea un nuevo dummy y lo persiste en la db")
    @PostMapping
    public ResponseEntity<DummyResponseDto> create(
            @RequestBody @Valid DummyCreateDto dummyDto) { //@Valid valida que los datos del body cumplan con las restricciones del DTO.
        DummyResponseDto created = dummyService.create(dummyDto);
        URI location = URI.create("/api/v1/dummies/" + created.getId());
        return ResponseEntity.created(location).body(created); // 201 Created con header Location, que es lo correcto según el estándar REST.
    }


    @Operation(summary = "Actualiza completamente un dummy; todos los campos son requeridos")
    @PutMapping("/{id}")
    public ResponseEntity<DummyResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid DummyCreateDto dummyDto) { //Se usa el mismo DTO que en POST, con todos los campos requeridos → implica reemplazo completo (semántica de PUT).
        DummyResponseDto updatedDummy = dummyService.update(id, dummyDto);
        return ResponseEntity.ok().body(updatedDummy); // 200 OK si existe
    }

    @Operation(summary = "Elimina un dummy de la db")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dummyService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content si se elimina correctamente. La acción fue exitosa, y no hay más que decir
    }
}
//REGLA DE OROOO aaaAA
//Tipo de parámetro	        Anotación	                        Excepción lanzada
//Objeto (@RequestBody)	    @Valid	                            MethodArgumentNotValidException
//Parámetro simple	        @Min, @NotNull, etc. + @Validated	ConstraintViolationException

