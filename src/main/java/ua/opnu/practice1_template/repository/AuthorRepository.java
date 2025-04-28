package ua.opnu.practice1_template.repository;

import org.springframework.data.repository.CrudRepository;
import ua.opnu.practice1_template.model.Author;

public interface AuthorRepository extends CrudRepository <Author, Long>{

}
