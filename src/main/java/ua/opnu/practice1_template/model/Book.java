package ua.opnu.practice1_template.model;
import jakarta.persistence.*;

@Entity // @Entity позначає, що цей клас відповідає таблиці книг у базі даних
public class Book {

    @Id // @Id позначає унікальний ідентифікатор книги
    @GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue автоматично створює новий ID для кожної книги


    private Long id; // Id-код
    private String title; // Назва книги
    private String isbn; // ISBN книги (унікальний код)
    private Integer publishedYear; // Рік видання книги

    /*
    У цій моделі реалізовано односторонній зв'язок: лише Book знає про Author.
    Це дозволяє уникнути проблеми нескінченної рекурсії при перетворенні об'єктів у JSON.
    CascadeType.ALL забезпечує автоматичне видалення книг при видаленні автора.
    Таким чином ми робимо односторонній зв'язок для того, щоб уникнути нескінченних циклів без застосування JsonIgnore або DTO.
    */

    @ManyToOne(cascade = CascadeType.ALL) // Позначає, що багато книг можуть належати одному автору
    @JoinColumn(name = "author_id", nullable = false) // Вказує на стовпець, який пов'язує книгу з автором
    private Author author;

    @ManyToOne // Позначає, що багато книг можуть належати одному жанру
    @JoinColumn(name = "genre_id") // Вказує на стовпець, який пов'язує книгу з жанром
    private Genre genre;

    // Порожній конструктор, потрібен для JPA
    public Book() {
    }

    // Конструктор з параметрами для створення книги
    public Book(Long id, String title, String isbn, Integer publishedYear, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.author = author;
        this.genre = genre;
    }

    // Геттери та сеттери

    // Повертає ID книги
    public Long getId() {
        return id;
    }

    // Встановлює ID книги
    public void setId(Long id) {
        this.id = id;
    }

    // Повертає назву книги
    public String getTitle() {
        return title;
    }

    // Встановлює назву книги
    public void setTitle(String title) {
        this.title = title;
    }

    // Повертає ISBN книги
    public String getIsbn() {
        return isbn;
    }

    // Встановлює ISBN книги
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Повертає рік видання книги
    public Integer getPublishedYear() {
        return publishedYear;
    }

    // Встановлює рік видання книги
    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    // Повертає автора книги
    public Author getAuthor() {
        return author;
    }

    // Встановлює автора книги
    public void setAuthor(Author author) {
        this.author = author;
    }

    // Повертає жанр книги
    public Genre getGenre() {
        return genre;
    }

    // Встановлює жанр книги
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
