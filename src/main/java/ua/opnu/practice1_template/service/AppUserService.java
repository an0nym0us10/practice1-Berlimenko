package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.AppUser;
import ua.opnu.practice1_template.repository.AppUserRepository;

@Service
public class AppUserService {

    // Репозиторій для зберігання та пошуку користувачів
    private final AppUserRepository userRepository;

    // Кодер паролів (BCrypt)
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    Реєструє нового користувача з роллю READER або LIBRARIAN.
    @param username унікальний логін
    @param password сирий пароль
    @param role роль користувача
    @return збережений AppUser
     */

    public AppUser register(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Користувач з таким логіном вже існує");
        }
        // Проверяем роль
        if (!"READER".equalsIgnoreCase(role) &&
                !"LIBRARIAN".equalsIgnoreCase(role)) {
            throw new RuntimeException("Невірна роль: " + role +
                    ". Дозволені: READER, LIBRARIAN");
        }

        // Створення та збереження нового користувача
        AppUser u = new AppUser(username,
                passwordEncoder.encode(password), // Хешування пароля
                role.toUpperCase()); // Форматування ролі у верхньому регістрі
        return userRepository.save(u);
    }
}