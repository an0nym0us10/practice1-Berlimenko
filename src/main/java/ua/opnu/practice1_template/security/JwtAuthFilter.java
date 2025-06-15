package ua.opnu.practice1_template.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.opnu.practice1_template.model.AppUser;
import ua.opnu.practice1_template.service.AppUserService;
import ua.opnu.practice1_template.service.CustomAppUserDetailsService;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    // Провайдер для генерації/валидації JWT
    private JwtProvider jwtProvider = null;

    // Сервіс для завантаження деталей користувача
    public final CustomAppUserDetailsService AppUserDetailsService;

    @Autowired
    public JwtAuthFilter(JwtProvider jwtProvider, CustomAppUserDetailsService AppUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.AppUserDetailsService = AppUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Отримати токен з заголовка Authorization
        String token = getToken(request);

        if (token == null){
            filterChain.doFilter(request, response);
            return;
        }

        if(!jwtProvider.validateToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        // Отримати ім'я користувача з токена
        String username = jwtProvider.getUsernameFromToken(token);
        UserDetails userDetails = AppUserDetailsService.loadUserByUsername(username);

        // Створити аутентифікаційний об'єкт та встановити його в контекст
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Продовжити фільтрацію
        filterChain.doFilter(request, response);

    }

    // Допоміжний метод для вилучення токена
    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer "))
            return null;


        return bearerToken.substring(7, bearerToken.length());

    }
}