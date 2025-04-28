package ua.opnu.practice1_template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Loan;
import ua.opnu.practice1_template.service.LoanService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    // Поле для сервісу, який містить логіку роботи з видачами книг
    private final LoanService service;

    // Автоматично "вставляє" об'єкт LoanService у конструктор
    @Autowired
    public LoanController(LoanService service) {
        this.service = service;
    }

    // Обробляє POST-запит для створення нової видачі книги
    @PostMapping
    public ResponseEntity<Loan> issueLoan(@RequestBody Loan loan) {
        return new ResponseEntity<>(service.issueLoan(loan), HttpStatus.CREATED); // Повертає створену видачу з кодом статусу 201 (CREATED)
    }

    // Обробляє GET-запит для отримання всіх видач
    @GetMapping("/all")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(service.getAllLoans()); // Повертає список усіх видач з кодом статусу 200 (OK)
    }

    // Обробляє GET-запит для отримання видач за ID читача
    @GetMapping("/reader/{readerId}")
    public ResponseEntity<List<Loan>> getLoansByReader(@PathVariable Long readerId) {
        return ResponseEntity.ok(service.getLoansByReaderId(readerId)); // Повертає список видач читача з кодом статусу 200 (OK)
    }

    // Обробляє GET-запит для отримання видач за ID книги
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Loan>> getLoansByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(service.getLoansByBookId(bookId)); // Повертає список видач книги з кодом статусу 200 (OK)
    }

    // Обробляє PUT-запит для позначення повернення книги
    @PutMapping("/return/{id}")
    public ResponseEntity<Void> returnBook(@PathVariable Long id, @RequestParam("returnDate") String returnDate) {
        service.returnBook(id, LocalDate.parse(returnDate)); // Викликає метод повернення книги з конвертацією рядка в LocalDate
        return ResponseEntity.noContent().build(); // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }
}