package ua.opnu.practice1_template.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.security.JwtProvider;
import ua.opnu.practice1_template.service.AppUserService;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Сервіс для роботи з користувачами (реєстрація)
    private final AppUserService userService;
    // Менеджер аутентифікації Spring Security
    private final AuthenticationManager authenticationManager;
    // Компонент для генерації та валідації JWT
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthController(AppUserService userService,
                          AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Реєстрація нового користувача.
     * Приймає username, password та роль (за замовчуванням READER).
     * Повертає статус 201 та текстове підтвердження або 400 з повідомленням про помилку.
     */
    @Operation(summary = "Реєстрація нового користувача")
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(defaultValue = "READER") String role) {
        try {
            userService.register(username, password, role);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Користувач успішно зареєстрований");
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .badRequest()
                    .body("Помилка реєстрації: " + ex.getMessage());
        }
    }

    /*
    Аутентифікація користувача за логіном/паролем.
    Повертає JWT-токен у тілі відповіді:
    "Успішний вхід." та "Token: {jwt}"
    Або помилку 401/500 з текстом.
     */

    @Operation(summary = "Автентифікація з генерацією JWT-токену")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {
        try {
            // Перевірка логіна та паролю
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            // Генерація токену
            String token = jwtProvider.generateToken(authentication);
            String body = "Успішний вхід.\nToken: " + token;
            return ResponseEntity.ok(body);
        } catch (BadCredentialsException ex) {
            // Невірні облікові дані
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Невірний логін або пароль");
        } catch (Exception ex) {
            // Інша помилка
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Помилка: " + ex.getMessage());
        }
    }

    /**
     * Початок OAuth2-флоу (редирект на Google).
     * Приховано в Swagger UI.
     */
    @Operation(hidden = true)
    @GetMapping("/login/oauth2")
    public void oauth2LoginRedirect(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/oauth2/authorization/google");
    }
}
