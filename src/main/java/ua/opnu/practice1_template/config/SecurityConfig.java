package ua.opnu.practice1_template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.opnu.practice1_template.security.JwtAuthFilter;
import ua.opnu.practice1_template.service.CustomAppUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAppUserDetailsService userDetailsService; // Сервіс для завантаження даних користувача
    private final JwtAuthFilter jwtAuthFilter; // Фільтр для обробки JWT-токенів

    public SecurityConfig(CustomAppUserDetailsService userDetailsService,
                          JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Відключаємо CSRF через stateless сесію
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Авторизація запитів за ролями
                .authorizeHttpRequests(auth -> auth
                        // Доступ без авторизації для реєстрації та логіну
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // GET-доступ (читання) для READER та LIBRARIAN
                        .requestMatchers(HttpMethod.GET,
                                "/book/**",
                                "/author/**",
                                "/genre/**",
                                "/reader/**",
                                "/loan/reader/**"
                        ).hasAnyRole("READER", "LIBRARIAN")

                        // PUT /loan/return доступний лише READER (повернення книги)
                        .requestMatchers(HttpMethod.PUT, "/loan/return/**").hasRole("READER")

                        // Повний CRUD лише для LIBRARIAN
                        .requestMatchers("/book/**").hasRole("LIBRARIAN")
                        .requestMatchers("/author/**").hasRole("LIBRARIAN")
                        .requestMatchers("/genre/**").hasRole("LIBRARIAN")
                        .requestMatchers("/reader/**").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/loan").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/loan/all").hasRole("LIBRARIAN")

                        // Все інше заборонено
                        .anyRequest().denyAll()
                )
                // Додаємо OAuth2 Login для Google
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/swagger-ui.html", true) // Редирект у разі успіху
                        .failureUrl("/auth/login?error") // При помилці
                )
                // JWT-фільтр
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        // Налаштування DAO-автентифікатора
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Підключаємо наш сервіс
        provider.setPasswordEncoder(passwordEncoder()); // Хешування паролів
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration cfg) throws Exception {

        // Повертаємо стандартний AuthenticationManager
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        // BCrypt для хешування паролів
        return new BCryptPasswordEncoder();
    }
}