package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.user.UserLoginResponseDTO;
import com.maosmeo.maolibreria.exceptions.ResourceNotFoundException;
import com.maosmeo.maolibreria.repository.UserRepository;
import com.maosmeo.maolibreria.repository.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationMs}")
    private long expirationMs;

    @Autowired
    protected UserRepository userRepository;

    protected UserLoginResponseDTO generateToken(UserEntity userEntity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userEntity.getName());
        claims.put("email", userEntity.getEmail());

        String token = Jwts.builder()
                .setSubject(userEntity.getEmail())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        UserLoginResponseDTO response = new UserLoginResponseDTO();
        response.setToken(token);

        return response;
    }

    public Boolean checkToken(String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
                String email = (String) claims.get("email");

                UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
                return user.getRoleEntity().getIsTechUser();
            } catch (Exception e) {
                throw  new RuntimeException(); //da gestire
            }
        } else {
            throw  new RuntimeException(); //da gestire
        }
    }

    private String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
}
