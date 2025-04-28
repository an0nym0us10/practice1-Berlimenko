package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Book;
import ua.opnu.practice1_template.model.Genre;
import ua.opnu.practice1_template.repository.BookRepository;
import ua.opnu.practice1_template.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {

    // Поля для репозиторіїв, які відповідають за доступ до даних
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired // Автоматично "вставляє" об'єкти репозиторіїв у конструктор
    public GenreService(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    // Створює новий жанр
    public Genre createGenre(Genre genre) {
        // Зберігає жанр та повертає збережений
        return genreRepository.save(genre);
    }

    // Отримує список усіх жанрів
    public List<Genre> getAllGenres() {
        // Повертає всі записи з таблиці жанрів
        return (List<Genre>) genreRepository.findAll();
    }

    // Призначає жанр книзі
    public void assignGenreToBook(Long bookId, Long genreId) {
        // Отримує книгу за ID, якщо не знайдено — попередження
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книгу не знайдено"));
        // Отримує жанр за ID, якщо не знайдено — попередження
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Жанр не знайдено"));

        // Встановлює жанр для книги
        book.setGenre(genre);
        // Зберігає оновлену книгу
        bookRepository.save(book);
    }

    // Отримує список книг за ID жанру
    public List<Book> getBooksByGenreId(Long genreId) {
        return bookRepository.findByGenreId(genreId);
    }

    // Видаляє жанр за ID
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}