package app.scaffolding.Dummy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase controller de ejemplo. Expone los endpoints de la API REST.
 */
@RestController
@RequestMapping("/api/v1/dummy")
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
    public ResponseEntity<Dummy> getDummy() {
        List<Dummy> dummyList = dummyService.getDummy();
        return null;
    }

    @GetMapping
    public ResponseEntity<Dummy> getDummyById(@PathVariable Long id) {
        Dummy dummy = dummyService.getDummyById(id);
        return null;
    }

    @PostMapping
    public ResponseEntity<Dummy> createDummy(DummyDto dummyDto) {
        Dummy dummy = dummyService.createDummy(dummyDto);
        return null;
    }

    @PutMapping
    public ResponseEntity<Dummy> updateDummy(DummyDto dummyDto) {
        Dummy dummy = dummyService.updateDummy(dummyDto);
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDummy(@PathVariable Long id) {
        dummyService.deleteDummy(id);
        return null;
    }
}