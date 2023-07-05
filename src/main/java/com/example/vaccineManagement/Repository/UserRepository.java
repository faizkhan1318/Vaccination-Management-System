package com.example.vaccineManagement.Repository;

import com.example.vaccineManagement.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //just define the function and call automatically
    public Optional<User> findByEmailId(String emailId);
    //prebuilt function : you can use it directly
}
