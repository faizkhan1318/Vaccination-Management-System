package com.example.vaccineManagement.Services;

import com.example.vaccineManagement.Dtos.RequestDtos.UpdateEmailDto;
import com.example.vaccineManagement.Exceptions.UserNotFound;
import com.example.vaccineManagement.Models.Dose;
import com.example.vaccineManagement.Models.User;
import com.example.vaccineManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User addUser(User user){
        user = userRepository.save(user);
        return user;
    }
    public Date getVaccDate(Integer userId){
        User user = userRepository.findById(userId).get();

        Dose dose = user.getDose();

        return dose.getVaccinationDate();
    }
    public String updateEmail(UpdateEmailDto updateEmailDto){
        int userId = updateEmailDto.getUserId();

        User user = userRepository.findById(userId).get();

        user.setEmailId(updateEmailDto.getNewEmailId());

        userRepository.save(user);

        return "Old EmailId Modified with New One";
    }

    public User getUserByEmail(String email) throws UserNotFound {
        Optional<User> userOptional = userRepository.findByEmailId(email);
        if(userOptional.isEmpty()){
            throw new UserNotFound("user not found");
        }
        return userOptional.get();
    }
}
