package com.example.zavrsnirad.config;

import com.example.zavrsnirad.Models.UserModel;
import com.example.zavrsnirad.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepository){
        userRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel user = userRepo.findByEmail(s);
        System.out.println(s);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user.isAdmin()){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        }
        boolean disabled = !user.getConfirmed();
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .disabled(disabled)
                .authorities((authorities))
                .build();
    }
}
