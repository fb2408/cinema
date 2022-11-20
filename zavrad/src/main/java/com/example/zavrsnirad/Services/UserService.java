package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.City;
import com.example.zavrsnirad.Models.UserModel;
import com.example.zavrsnirad.Repositories.CityRepository;
import com.example.zavrsnirad.Repositories.UserRepository;
import com.example.zavrsnirad.dto.RegisterFormDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel registerNewUser(RegisterFormDTO registerFormDTO) {
        if (userRepository.existsByEmailOrUsername(registerFormDTO.getUserMail(), registerFormDTO.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        UserModel userModel = new UserModel();
        userModel.setEmail(registerFormDTO.getUserMail());
        userModel.setUsername(registerFormDTO.getUsername());
        userModel.setFirstname(registerFormDTO.getUserFirstName());
        userModel.setLastname(registerFormDTO.getUserLastname());
        userModel.setAdress(registerFormDTO.getAdress());
        City city = null;
        try {
            city = cityRepository.findById(1).orElseThrow(() -> new Exception("City not founded"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userModel.setCity(city);
        userModel.setPassword(bCryptPasswordEncoder.encode(registerFormDTO.getPassword()));
        userModel.setConfirmed(true);
        userModel.setType("client");

        System.out.println(userModel);
        return userRepository.save(userModel);
    }

    public UserModel findUserByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(UserModel user) {
        userRepository.save(user);
    }
}
