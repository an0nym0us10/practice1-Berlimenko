package ua.opnu.practice1_template.repository;

import org.springframework.data.repository.CrudRepository;
import ua.opnu.practice1_template.model.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findByReaderId(Long readerId);

    List<Loan> findByBookId(Long bookId);

    // Перевірка чи є ця книга у наявності (тобто чи повернули її)
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}


