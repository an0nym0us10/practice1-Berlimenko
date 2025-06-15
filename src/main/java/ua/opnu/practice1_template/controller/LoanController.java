package ua.opnu.practice1_template.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    @Autowired
    public LoanController(LoanService service) {
        this.service = service;
    }

    @Operation(summary = "Видати книгу читачу")
    @PostMapping
    // Обробляє POST-запит для створення нової видачі книги
    public ResponseEntity<Loan> issueLoan(@RequestBody Loan loan) {
        return new ResponseEntity<>(service.issueLoan(loan), HttpStatus.CREATED);
        // Повертає створену видачу з кодом статусу 201 (CREATED)
    }

    @Operation(summary = "Отримати список виданих книг")
    @GetMapping("/all")
    // Обробляє GET-запит для отримання всіх видач
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(service.getAllLoans());
        // Повертає список усіх видач з кодом статусу 200 (OK)
    }

    @Operation(summary = "Отримати історію видачі читача")
    @GetMapping("/reader/{readerId}")
    // Обробляє GET-запит для отримання видач за ID читача
    public ResponseEntity<List<Loan>> getLoansByReader(@PathVariable Long readerId) {
        return ResponseEntity.ok(service.getLoansByReaderId(readerId));
        // Повертає список видач читача з кодом статусу 200 (OK)
    }

    @Operation(summary = "Отримати історію видачі книги")
    @GetMapping("/book/{bookId}")
    // Обробляє GET-запит для отримання видач за ID книги
    public ResponseEntity<List<Loan>> getLoansByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(service.getLoansByBookId(bookId));
        // Повертає список видач книги з кодом статусу 200 (OK)
    }

    @Operation(summary = "Повернути книгу")
    @PutMapping("/return/{id}")
    // Обробляє PUT-запит для позначення повернення книги
    public ResponseEntity<Void> returnBook(
            @PathVariable Long id,
            @RequestParam("returnDate") String returnDate) {
        service.returnBook(id, LocalDate.parse(returnDate));
        return ResponseEntity.noContent().build();
        // Повертає порожню відповідь з кодом статусу 204 (NO CONTENT)
    }
}