package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Book;
import ua.opnu.practice1_template.model.Author;
import ua.opnu.practice1_template.model.Genre;
import ua.opnu.practice1_template.repository.AuthorRepository;
import ua.opnu.practice1_template.repository.BookRepository;
import ua.opnu.practice1_template.repository.GenreRepository;

import java.util.List;

@Service
public class BookService {

    // Поля для репозиторіїв, які відповідають за доступ до даних
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired // Автоматично "вставляє" об'єкти репозиторіїв у конструктор
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    // Додає нову книгу до бази даних
    public Book addNewBook(Book book) {
        // Перевірка чи існує автор книги
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Автора не знайдено"));

        // Перевірка чи існує жанр книги
        Genre genre = null;
        if (book.getGenre() != null) {
            genre = genreRepository.findById(book.getGenre().getId())
                    .orElseThrow(() -> new RuntimeException("Жанр не знайдено"));
        }

        // Встановлює автора та жанр для книги
        book.setAuthor(author);
        book.setGenre(genre);

        // Зберігає книгу та повертає збережену
        return bookRepository.save(book);
    }

    // Отримує список усіх книг
    public List<Book> getAllBooks() {
        // Повертає всі записи з таблиці книг
        return (List<Book>) bookRepository.findAll();
    }

    // Отримує книгу за її ID
    public Book getBookById(Long id) {
        // Шукає книгу за ID, якщо не знайдено — попередження
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // Оновлює дані книги за ID
    public Book updateBook(Long id, Book updatedBook) {
        // Отримує існуючу книгу
        Book book = getBookById(id);

        // Перевірка чи існує автор
        Author author = authorRepository.findById(updatedBook.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // Перевірка чи існує жанр (якщо вказано)
        Genre genre = null;
        if (updatedBook.getGenre() != null) {
            genre = genreRepository.findById(updatedBook.getGenre().getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found"));
        }

        // Оновлює дані книги
        book.setTitle(updatedBook.getTitle());
        book.setIsbn(updatedBook.getIsbn());
        book.setPublishedYear(updatedBook.getPublishedYear());
        book.setAuthor(author);
        book.setGenre(genre);

        // Зберігає оновлену книгу
        return bookRepository.save(book);
    }

    // Видаляє книгу за ID
    public void deleteBook(Long id) {
        // Видаляє запис з таблиці книг за ID
        bookRepository.deleteById(id);
    }

    // Отримує список книг за ID автора
    public List<Book> getBooksByAuthorId(Long authorId) {
        // Шукає книги за ID автора
        return bookRepository.findByAuthorId(authorId);
    }

    // Отримує список книг за ID жанру
    public List<Book> getBooksByGenreId(Long genreId) {
        // Шукає книги за ID жанру
        return bookRepository.findByGenreId(genreId);
    }
}