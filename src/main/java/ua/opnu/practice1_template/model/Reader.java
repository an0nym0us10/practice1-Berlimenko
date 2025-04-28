package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reader {

    @Id // Позначає унікальний ідентифікатор читача
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматично створює новий ID для кожного читача
    private Long id;

    // Ім'я читача
    private String firstName;
    // Прізвище читача
    private String lastName;
    // Номер бібліотечного квитка
    private String libraryCardNumber;
    // Дата реєстрації читача
    private LocalDate registeredAt;

    public Reader() {}

    // Конструктор з параметрами для створення читача
    public Reader(Long id, String firstName, String lastName, String libraryCardNumber, LocalDate registeredAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.libraryCardNumber = libraryCardNumber;
        this.registeredAt = registeredAt;
    }

    // Геттери та сеттери
    // Повертає ID читача
    public Long getId() {
        return id;
    }

    // Встановлює ID читача
    public void setId(Long id) {
        this.id = id;
    }

    // Повертає ім'я читача
    public String getFirstName() {
        return firstName;
    }

    // Встановлює ім'я читача
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Повертає прізвище читача
    public String getLastName() {
        return lastName;
    }

    // Встановлює прізвище читача
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Повертає номер бібліотечного квитка
    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    // Встановлює номер бібліотечного квитка
    public void setLibraryCardNumber(String libraryCardNumber) {
        this.libraryCardNumber = libraryCardNumber;
    }

    // Повертає дату реєстрації читача
    public LocalDate getRegisteredAt() {
        return registeredAt;
    }

    // Встановлює дату реєстрації читача
    public void setRegisteredAt(LocalDate registeredAt) {
        this.registeredAt = registeredAt;
    }
}