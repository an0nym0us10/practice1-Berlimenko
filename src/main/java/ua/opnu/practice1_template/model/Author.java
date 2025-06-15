package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// @Entity позначає, що цей клас відповідає таблиці в базі даних
@Entity
public class Author {

    @Id // @Id позначає унікальний ідентифікатор автора
    @GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue автоматично створює новий ID для кожного автора
    private Long id;

    private String name; // Ім'я автора
    private LocalDate birthDate; // Дата народження автора

    public Author() {}

    // Конструктор з параметрами для створення автора
    public Author(Long id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    // Геттери та сеттери

    // Повертає ID автора
    public Long getId() {
        return id;
    }

    // Встановлює ID автора
    public void setId(Long id) {
        this.id = id;
    }

    // Повертає ім'я автора
    public String getName() {
        return name;
    }

    // Встановлює ім'я автора
    public void setName(String name) {
        this.name = name;
    }

    // Повертає дату народження автора
    public LocalDate getBirthDate() {
        return birthDate;
    }

    // Встановлює дату народження автора
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}