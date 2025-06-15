package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Author;
import ua.opnu.practice1_template.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {

    // Поле для репозиторію, який відповідає за доступ до даних авторів
    private final AuthorRepository authorRepository;

    @Autowired // Автоматично "вставляє" об'єкт AuthorRepository у конструктор
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Додає нового автора до бази даних
    public Author addNewAuthor(Author author) {
        return authorRepository.save(author); // Зберігає автора та повертає збереженого
    }

    // Отримує список усіх авторів
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll(); // Повертає всі записи з таблиці авторів
    }

    // Отримує автора за його ID
    public Author getAuthorById(Long id) {
        // Шукає автора за ID, якщо не знайдено — викидає виняток
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автора не знайдено"));
    }

    // Оновлює дані автора за ID
    public Author updateAuthor(Long id, Author updatedAuthor) {
        // Отримує існуючого автора
        Author author = getAuthorById(id);
        // Оновлює ім'я та дату народження
        author.setName(updatedAuthor.getName());
        author.setBirthDate(updatedAuthor.getBirthDate());
        // Зберігає оновленого автора
        return authorRepository.save(author);
    }

    // Видаляє автора за ID
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}