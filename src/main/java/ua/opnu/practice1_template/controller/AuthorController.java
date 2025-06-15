package ua.opnu.practice1_template.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Author;
import ua.opnu.practice1_template.service.AuthorService;

import java.util.List;

@RestController
public class AuthorController {

    // Поле для сервісу, який містить логіку роботи з авторами
    private AuthorService service;

    // @Autowired автоматично "вставляє" об'єкт AuthorService у конструктор
    @Autowired
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @Operation(summary = "Додати нового автора")
    // @PostMapping("/author") обробляє POST-запит для створення нового автора
    @PostMapping("/author")
    public ResponseEntity<Author> addNewAuthor(@RequestBody Author author) {
        // @RequestBody отримує дані автора з тіла запиту
        return new ResponseEntity<>(service.addNewAuthor(author), HttpStatus.CREATED);
        // Повертає створеного автора з кодом статусу 201 (CREATED)
    }

    @Operation(summary = "Отримати всіх авторів")
    // Обробляє GET-запит для отримання всіх авторів
    @GetMapping("/author/all")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(service.getAllAuthors());
        // Повертає список усіх авторів з кодом статусу 200 (OK)
    }

    @Operation(summary = "Отримати автора за id")
    // Обробляє GET-запит для отримання автора за ID
    @GetMapping("/author/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        // @PathVariable отримує ID з URL
        return ResponseEntity.ok(service.getAuthorById(id));
        // Повертає автора з кодом статусу 200 (OK)
    }

    @Operation(summary = "Оновити автора")
    @PutMapping("/author/{id}")
    // Обробляє PUT-запит для оновлення автора
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return ResponseEntity.ok(service.updateAuthor(id, author));
        // Повертає оновленого автора з кодом статусу 200 (OK)
    }

    @Operation(summary = "Видалити автора")
    @DeleteMapping("/author/{id}")
    // Обробляє DELETE-запит для видалення автора
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        service.deleteAuthor(id);
        return ResponseEntity.noContent().build();
        // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }
}