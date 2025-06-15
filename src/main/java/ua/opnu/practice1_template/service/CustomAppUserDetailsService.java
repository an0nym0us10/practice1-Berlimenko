package ua.opnu.practice1_template.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.repository.AppUserRepository;

@Service
public class CustomAppUserDetailsService
        implements UserDetailsService {

    // Репозиторій для пошуку користувачів за username
    private final AppUserRepository repo;

    public CustomAppUserDetailsService(AppUserRepository repo) {
        this.repo = repo;
    }

    /*
    Завантажує UserDetails за заданим username.
    @param username логін користувача
    @return UserDetails, якщо знайдено
    @throws UsernameNotFoundException якщо користувача не існує
    */

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return repo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Користувача не знайдено: " + username));
    }
}