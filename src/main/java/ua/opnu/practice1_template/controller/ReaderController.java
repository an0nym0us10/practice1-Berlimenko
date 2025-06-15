package ua.opnu.practice1_template.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Reader;
import ua.opnu.practice1_template.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    // Поле для сервісу, який містить логіку роботи з читачами
    private final ReaderService service;

    // Автоматично "вставляє" об'єкт ReaderService у конструктор
    @Autowired
    public ReaderController(ReaderService service) {
        this.service = service;
    }

    @Operation(summary = "Зареєструвати нового читача")
    // Обробляє POST-запит для створення нового читача
    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody Reader reader) {
        return new ResponseEntity<>(
                service.addNewReader(reader),
                HttpStatus.CREATED
        );
        // Повертає створеного читача з кодом статусу 201 (CREATED)
    }

    @Operation(summary = "Отримати всіх читачів")
    // Обробляє GET-запит для отримання всіх читачів
    @GetMapping("/all")
    public ResponseEntity<List<Reader>> getAllReaders() {
        return ResponseEntity.ok(
                service.getAllReaders()
        );
        // Повертає список усіх читачів з кодом статусу 200 (OK)
    }

    @Operation(summary = "Отримати читача за id")
    // Обробляє GET-запит для отримання читача за ID
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        return ResponseEntity.ok(
                service.getReaderById(id)
        );
        // Повертає читача з кодом статусу 200 (OK)
    }

    @Operation(summary = "Оновити дані читача")
    // Обробляє PUT-запит для оновлення читача
    @PutMapping("/{id}")
    public ResponseEntity<Reader> updateReader(
            @PathVariable Long id,
            @RequestBody Reader reader) {
        return ResponseEntity.ok(
                service.updateReader(id, reader)
        );
        // Повертає оновленого читача з кодом статусу 200 (OK)
    }

    @Operation(summary = "Видалити читача")
    // Обробляє DELETE-запит для видалення читача
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        service.deleteReader(id);
        return ResponseEntity.noContent().build();
        // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }
}
