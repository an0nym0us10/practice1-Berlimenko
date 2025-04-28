package ua.opnu.practice1_template.model;

import jakarta.persistence.*;

@Entity
public class Genre {

    @Id // Позначає унікальний ідентифікатор жанру
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматично створює новий ID для кожного жанру
    private Long id;

    // Назва жанру
    private String name;

    // Порожній конструктор для JPA
    public Genre() {}

    // Конструктор з параметрами для створення жанру
    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттери та сеттери
    // Повертає ID жанру
    public Long getId() {
        return id;
    }

    // Встановлює ID жанру
    public void setId(Long id) {
        this.id = id;
    }

    // Повертає назву жанру
    public String getName() {
        return name;
    }

    // Встановлює назву жанру
    public void setName(String name) {
        this.name = name;
    }
}