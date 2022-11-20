package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByEmail(String email);

    boolean existsByEmailOrUsername(String email, String username);
}
