package com.danney.auth.sec;

import com.danney.auth.db.User;
import com.danney.auth.user.UserJwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String SECRET_KEY = "secret";
    private final Long EXPIRATION = 1000L * 60L * 60L * 10L; // 10 hrs

    public String userFrom(String jwt) {
        return claim(jwt, Claims::getSubject);
    }

    public Date expirationFrom(String jwt) {
        return claim(jwt, Claims::getExpiration);
    }

    private <T> T claim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = allClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims allClaims(String jwt) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
    }

    private Boolean isExpired(String jwt) {
        return expirationFrom(jwt).before(new Date());
    }

    public UserJwt generate(User user) {
        Map<String, Object> claims = new HashMap<>();
        return new UserJwt(createToken(claims, user.getUsername()));
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION);
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(now).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validate(String jwt, User user) {
        final String username = userFrom(jwt);
        return username.equals(user.getUsername()) && !isExpired(jwt);
    }
}
