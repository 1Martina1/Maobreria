package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.user.InsertUserRequestDTO;
import com.maosmeo.maolibreria.dto.user.UserLoginDTO;
import com.maosmeo.maolibreria.dto.user.UserLoginRequestDTO;
import com.maosmeo.maolibreria.exceptions.ExistingResourceException;
import com.maosmeo.maolibreria.exceptions.ResourceNotFoundException;
import com.maosmeo.maolibreria.repository.UserRepository;
import com.maosmeo.maolibreria.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String insertNewUser(InsertUserRequestDTO insertUserRequestDTO){
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

        return "Il salvataggio è avvenuto con successo";
    }

//    public UserDetails userLogin(String email, String password){
//        Optional<UserEntity> loginByEmailAndPassword = userRepository.findByEmailAndPassword(email, password);
//
//        if(loginByEmailAndPassword.isEmpty()){
//            throw new ResourceNotFoundException("La mail o la password sono errati");
//        }
//
//        //creazione token
//        return UserDetails;
//    }

}
