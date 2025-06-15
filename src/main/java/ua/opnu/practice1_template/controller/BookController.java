package ua.opnu.practice1_template.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Book;
import ua.opnu.practice1_template.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    // Поле для сервісу, який містить логіку роботи з книгами
    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @Operation(summary = "Додати нову книгу")
    @PostMapping
    // Обробляє POST-запит для створення нової книги
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(service.addNewBook(book), HttpStatus.CREATED);
        // Повертає створену книгу з кодом статусу 201 (CREATED)
    }

    @Operation(summary = "Отримати всі книги")
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(service.getAllBooks());
        // Повертає список усіх книг з кодом статусу 200 (OK)
    }

    @Operation(summary = "Отримати книгу за id")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBookById(id));
        // Повертає книгу з кодом статусу 200 (OK)
    }

    @Operation(summary = "Оновити книгу")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(id, book));
        // Повертає оновлену книгу з кодом статусу 200 (OK)
    }

    @Operation(summary = "Видалити книгу")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
        // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }

    @Operation(summary = "Отримати книги певного автора")
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(service.getBooksByAuthorId(authorId));
        // Повертає список книг автора з кодом статусу 200 (OK)
    }

    @Operation(summary = "Отримати книги за жанром")
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable Long genreId) {
        return ResponseEntity.ok(service.getBooksByGenreId(genreId));
        // Повертає список книг жанру з кодом статусу 200 (OK)
    }
}