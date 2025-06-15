package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Reader;
import ua.opnu.practice1_template.repository.ReaderRepository;

import java.util.List;

@Service
public class ReaderService {

    // Поле для репозиторію, який відповідає за доступ до даних читачів
    private final ReaderRepository readerRepository;

    @Autowired // Автоматично "вставляє" об'єкт ReaderRepository у конструктор
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    // Додає нового читача до бази даних
    public Reader addNewReader(Reader reader) {
        // Зберігає читача та повертає збереженого
        return readerRepository.save(reader);
    }

    // Отримує список усіх читачів
    public List<Reader> getAllReaders() {
        // Повертає всі записи з таблиці читачів
        return (List<Reader>) readerRepository.findAll();
    }

    // Отримує читача за його ID
    public Reader getReaderById(Long id) {
        // Шукає читача за ID, якщо не знайдено — попередження
        return readerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Читача не знайдено"));
    }

    // Оновлює дані читача за ID
    public Reader updateReader(Long id, Reader updatedReader) {
        // Отримує існуючого читача
        Reader reader = getReaderById(id);
        // Оновлює дані читача
        reader.setFirstName(updatedReader.getFirstName());
        reader.setLastName(updatedReader.getLastName());
        reader.setLibraryCardNumber(updatedReader.getLibraryCardNumber());
        reader.setRegisteredAt(updatedReader.getRegisteredAt());
        // Зберігає оновленого читача
        return readerRepository.save(reader);
    }

    // Видаляє читача за ID
    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }
}