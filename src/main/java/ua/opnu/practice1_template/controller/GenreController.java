package ua.opnu.practice1_template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Автоматично "вставляє" об'єкт GenreService у конструктор
    @Autowired
    public GenreController(GenreService service) {
        this.service = service;
    }

    // Обробляє POST-запит для створення нового жанру
    @PostMapping
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        return new ResponseEntity<>(service.createGenre(genre), HttpStatus.CREATED); // Повертає створений жанр з кодом статусу 201 (CREATED)
    }

    // Обробляє GET-запит для отримання всіх жанрів
    @GetMapping("/all")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(service.getAllGenres()); // Повертає список усіх жанрів з кодом статусу 200 (OK)
    }

    // Обробляє POST-запит для призначення жанру книзі
    @PostMapping("/assign")
    public ResponseEntity<Void> assignGenreToBook(@RequestParam Long bookId, @RequestParam Long genreId) {
        service.assignGenreToBook(bookId, genreId); // Викликає метод призначення жанру книзі
        return ResponseEntity.noContent().build(); // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }

    // Обробляє GET-запит для отримання книг за ID жанру
    @GetMapping("/{genreId}/books")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable Long genreId) {
        return ResponseEntity.ok(service.getBooksByGenreId(genreId)); // Повертає список книг жанру з кодом статусу 200 (OK)
    }

    // Обробляє DELETE-запит для видалення жанру
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        service.deleteGenre(id); // Викликає метод видалення жанру
        return ResponseEntity.noContent().build(); // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }
}