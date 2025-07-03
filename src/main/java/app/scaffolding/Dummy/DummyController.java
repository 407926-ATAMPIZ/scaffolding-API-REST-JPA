package app.scaffolding.Dummy;

import app.scaffolding.Dummy.dto.DummyCreateDto;
import app.scaffolding.Dummy.dto.DummyResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Clase controller de ejemplo. Expone los endpoints de la API REST.
 */
@RestController
@RequestMapping("/api/v1/dummies")
public class DummyController {
    private final DummyService dummyService;

    //En lugar de usar @Autowired, se utiliza la inyección de dependencias a través del constructor.
    // Esto es para evitar problemas de inicialización (null pointer exceptions) y para facilitar las pruebas unitarias.
    // Al hacerlo en el constructor, Spring se encargará de inyectar la instancia de DummyService cuando se cree una instancia de DummyController.
    // Esto hace obligatoria la inyección de DummyService al crear DummyController, lo que mejora la claridad del código y la gestión de dependencias.
    // Si quisieramos que la dependencia fuera opcional, puede usarse inyección de dependencias a través de un setter o un metodo específico.
    public DummyController(final DummyService dummyService) {
        this.dummyService = dummyService;
    }

    // Aquí se pueden definir los endpoints de la API REST utilizando anotaciones como @GetMapping, @PostMapping, etc.
    // Ejemplo de un endpoint:
    @GetMapping
    public ResponseEntity<List<DummyResponseDto>> getAll() {
        List<DummyResponseDto> dummyList = dummyService.getAll();
        return ResponseEntity.ok(dummyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DummyResponseDto> getById(@PathVariable Long id) {
        DummyResponseDto dummy = dummyService.getById(id);
        if (dummy != null) {
            return ResponseEntity.ok().body(dummy);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DummyResponseDto> create(@Valid DummyCreateDto dummyDto) {
        DummyResponseDto createdDummy = dummyService.create(dummyDto);
        if (createdDummy != null) {
            URI location = URI.create("/api/v1/dummies/" + createdDummy.getId());
            return ResponseEntity.created(location).body(createdDummy);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping
    public ResponseEntity<DummyResponseDto> update(DummyCreateDto dummyDto) {
        DummyResponseDto updatedDummy = dummyService.update(dummyDto);
        return ResponseEntity.ok().body(updatedDummy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dummyService.delete(id);
        return null;
    }
}