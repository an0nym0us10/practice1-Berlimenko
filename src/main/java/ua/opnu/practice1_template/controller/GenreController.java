package ua.opnu.practice1_template.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Book;
import ua.opnu.practice1_template.model.Genre;
import ua.opnu.practice1_template.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    // Поле для сервісу, який містить логіку роботи з жанрами
    private final GenreService service;

    @Autowired
    public GenreController(GenreService service) {
        this.service = service;
    }

    @Operation(summary = "Додати новий жанр")
    @PostMapping
    // Обробляє POST-запит для створення нового жанру
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        return new ResponseEntity<>(service.createGenre(genre), HttpStatus.CREATED);
        // Повертає створений жанр з кодом статусу 201 (CREATED)
    }

    @Operation(summary = "Отримати список жанрів")
    @GetMapping("/all")
    // Обробляє GET-запит для отримання всіх жанрів
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(service.getAllGenres());
        // Повертає список усіх жанрів з кодом статусу 200 (OK)
    }

    @Operation(summary = "Призначити жанр для книги")
    @PostMapping("/assign")
    // Обробляє POST-запит для призначення жанру книзі
    public ResponseEntity<Void> assignGenreToBook(
            @RequestParam Long bookId,
            @RequestParam Long genreId) {
        service.assignGenreToBook(bookId, genreId);
        return ResponseEntity.noContent().build();
        // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }

    @Operation(summary = "Отримати книги за жанром")
    @GetMapping("/{genreId}/books")
    // Обробляє GET-запит для отримання книг за ID жанру
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable Long genreId) {
        return ResponseEntity.ok(service.getBooksByGenreId(genreId));
        // Повертає список книг жанру з кодом статусу 200 (OK)
    }

    @Operation(summary = "Видалити жанр")
    @DeleteMapping("/{id}")
    // Обробляє DELETE-запит для видалення жанру
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        service.deleteGenre(id);
        return ResponseEntity.noContent().build();
        // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }
}