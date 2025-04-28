package ua.opnu.practice1_template.repository;

import org.springframework.data.repository.CrudRepository;
import ua.opnu.practice1_template.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}


