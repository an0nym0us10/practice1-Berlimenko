package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id // Позначає унікальний ідентифікатор видачі
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматично створює новий ID для кожної видачі
    private Long id;

    @ManyToOne // Позначає, що одна книга може бути видана в одній видачі
    @JoinColumn(name = "book_id") // Вказує на стовпець, який пов'язує видачу з книгою
    private Book book;

    @ManyToOne // Позначає, що один читач може мати багато видач
    @JoinColumn(name = "reader_id") // Вказує на стовпець, який пов'язує видачу з читачем
    private Reader reader;

    // Дата видачі книги
    private LocalDate loanDate;
    // Дата повернення книги
    private LocalDate returnDate;

    public Loan() {}

    // Конструктор з параметрами для створення видачі
    public Loan(Long id, Book book, Reader reader, LocalDate loanDate, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    // Геттери та сеттери
    // Повертає ID видачі
    public Long getId() {
        return id;
    }

    // Встановлює ID видачі
    public void setId(Long id) {
        this.id = id;
    }

    // Повертає книгу, яка видана
    public Book getBook() {
        return book;
    }

    // Встановлює книгу для видачі
    public void setBook(Book book) {
        this.book = book;
    }

    // Повертає читача, який взяв книгу
    public Reader getReader() {
        return reader;
    }

    // Встановлює читача для видачі
    public void setReader(Reader reader) {
        this.reader = reader;
    }

    // Повертає дату видачі книги
    public LocalDate getLoanDate() {
        return loanDate;
    }

    // Встановлює дату видачі книги
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    // Повертає дату повернення книги
    public LocalDate getReturnDate() {
        return returnDate;
    }

    // Встановлює дату повернення книги
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}