package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.user.InsertUserRequestDTO;
import com.maosmeo.maolibreria.dto.user.UserLoginResponseDTO;
import com.maosmeo.maolibreria.exceptions.ExistingResourceException;
import com.maosmeo.maolibreria.exceptions.ResourceNotFoundException;
import com.maosmeo.maolibreria.repository.UserRepository;
import com.maosmeo.maolibreria.repository.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationMs}")
    private long expirationMs;

    @Autowired
    private UserRepository userRepository;


    public UserLoginResponseDTO insertNewUser(InsertUserRequestDTO insertUserRequestDTO){
        Optional<UserEntity> userByEmail = userRepository.findByEmail(insertUserRequestDTO.getEmail());

        if(userByEmail.isPresent()) {
            throw new ExistingResourceException("Utente già presente");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(insertUserRequestDTO.getName());
        userEntity.setSurname(insertUserRequestDTO.getSurname());
        userEntity.setEmail(insertUserRequestDTO.getEmail());
        userEntity.setBirthDate(insertUserRequestDTO.getBirthDate());
        userEntity.setTelephone(insertUserRequestDTO.getTelephone());
        userEntity.setPassword(insertUserRequestDTO.getPassword());

        userRepository.save(userEntity);

        return generateToken(userEntity);
    }

    public UserLoginResponseDTO userLogin(String email, String password){

        Optional<UserEntity> loginByEmailAndPassword = userRepository.findByEmailAndPassword(email, password);
        if(loginByEmailAndPassword.isEmpty()){
            throw new ResourceNotFoundException("La mail o la password sono errati");
        }

        return generateToken(loginByEmailAndPassword.get());
    }

    private UserLoginResponseDTO generateToken(UserEntity userEntity) {
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
