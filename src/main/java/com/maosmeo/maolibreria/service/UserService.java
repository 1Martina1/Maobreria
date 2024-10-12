package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.user.InsertUserRequestDTO;
import com.maosmeo.maolibreria.dto.user.UserLoginResponseDTO;
import com.maosmeo.maolibreria.exceptions.ExistingResourceException;
import com.maosmeo.maolibreria.exceptions.ResourceNotFoundException;
import com.maosmeo.maolibreria.exceptions.UnexpectedErrorException;
import com.maosmeo.maolibreria.repository.RoleRepository;
import com.maosmeo.maolibreria.repository.UserRepository;
import com.maosmeo.maolibreria.repository.entity.RoleEntity;
import com.maosmeo.maolibreria.repository.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService extends BaseService{

    @Autowired
    private RoleRepository roleRepository;


    public UserLoginResponseDTO insertNewUser(InsertUserRequestDTO insertUserRequestDTO){
        Optional<UserEntity> userByEmail = userRepository.findByEmail(insertUserRequestDTO.getEmail());

        if(userByEmail.isPresent()) {
            throw new ExistingResourceException("Utente già presente");
        }

        if(!simpleEmailValidator(insertUserRequestDTO.getEmail())){
            throw new UnexpectedErrorException("Inserire una mail valida");
        }

        if(!isBirthdayBeforeToday(insertUserRequestDTO.getBirthDate())){
            throw new UnexpectedErrorException("Inserire una data valida");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(insertUserRequestDTO.getName());
        userEntity.setSurname(insertUserRequestDTO.getSurname());
        userEntity.setEmail(insertUserRequestDTO.getEmail());
        userEntity.setBirthDate(insertUserRequestDTO.getBirthDate());
        userEntity.setTelephone(insertUserRequestDTO.getTelephone());
        userEntity.setPassword(insertUserRequestDTO.getPassword());
        RoleEntity roleEntity = roleRepository.findById(1).orElseThrow(() -> new UnexpectedErrorException("L'utente non è stato trovato"));

        userEntity.setRoleEntity(roleEntity);
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

    protected static Boolean simpleEmailValidator(String email){
        String EMAIL_PATTERN = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
        }

    protected static boolean isBirthdayBeforeToday(String epochTimestamp) {

        // Convertire il timestamp epoch in un oggetto Instant
        long epochMillis = Long.parseLong(epochTimestamp);
        Instant birthdayInstant = Instant.ofEpochMilli(epochMillis);

        // Convertire l'Instant in LocalDate
        LocalDate birthday = birthdayInstant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        return birthday.isBefore(today);

    }
}
