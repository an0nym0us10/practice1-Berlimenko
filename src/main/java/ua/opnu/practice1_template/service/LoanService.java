package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Loan;
import ua.opnu.practice1_template.repository.LoanRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    // Поле для репозиторію, який відповідає за доступ до даних видач
    private final LoanRepository loanRepository;

    @Autowired // Автоматично "вставляє" об'єкт LoanRepository у конструктор
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    // Видає книгу читачу
    public Loan issueLoan(Loan loan) {
        // Перевірка чи книга вже видана і ще не повернена
        if (loanRepository.existsByBookIdAndReturnDateIsNull(loan.getBook().getId())) {
            throw new RuntimeException("Книга вже видана і ще не повернена");
        }
        // Зберігає нову видачу та повертає її
        return loanRepository.save(loan);
    }

    // Отримує список усіх видач
    public List<Loan> getAllLoans() {
        // Повертає всі записи з таблиці видач
        return (List<Loan>) loanRepository.findAll();
    }

    // Отримує список видач за ID читача
    public List<Loan> getLoansByReaderId(Long readerId) {
        // Шукає видачі за ID читача
        return loanRepository.findByReaderId(readerId);
    }

    // Отримує список видач за ID книги
    public List<Loan> getLoansByBookId(Long bookId) {
        // Шукає видачі за ID книги
        return loanRepository.findByBookId(bookId);
    }

    // Позначає книгу як повернену
    public void returnBook(Long id, LocalDate returnDate) {
        // Отримує видачу за ID, якщо не знайдено — викидає виняток
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        // Встановлює дату повернення
        loan.setReturnDate(returnDate);
        // Зберігає оновлену видачу
        loanRepository.save(loan);
    }
}