package ua.opnu.practice1_template.repository;

import org.springframework.data.repository.CrudRepository;
import ua.opnu.practice1_template.model.Reader;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
}

