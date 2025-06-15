package ua.opnu.practice1_template.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    // Секретний ключ для підпису токена (не менше 256 біт)
    private static final String SECRET_KEY = "my-super-secret-key-which-is-at-least-32-bytes-long!";
    private static final long EXPIRATION_MS = 86400000; // Термін життя токена в мілісекундах (24 години)

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    // Генерація JWT за даними аутентифікації
    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(authentication.getName()) // Тема токена — username
                .setIssuedAt(now) // Час випуску
                .setExpiration(expiryDate) // Час закінченн
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); // Формування рядка
    }

    /*
     Встановлюємо ключ для валідації
     Розбираємо JWS
     Повертаємо subject
     */

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // Некоректний або прострочений токен
        }
    }
}
