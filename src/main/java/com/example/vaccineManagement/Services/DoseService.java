package com.example.vaccineManagement.Services;

import com.example.vaccineManagement.Models.Dose;
import com.example.vaccineManagement.Models.User;
import com.example.vaccineManagement.Repository.DoseRepository;
import com.example.vaccineManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoseService {
    @Autowired
    DoseRepository doseRepository;

    @Autowired
    UserRepository userRepository;

    public String giveDose(String doseId, Integer userId){

        //model - give reference structures of tables
        //entity - object of that model

        User user = userRepository.findById(userId).get();

        //An entity of that model created
        //this entity will save in database
        Dose dose= new Dose();

        //first set the attributes
        dose.setDoseId(doseId);
        dose.setUser(user);

//       this is for unidirectional //then save the entity into the database
//        doseRepository.save(dose1);
        //for bidirectional we also save in users child class dose

        user.setDose(dose);

        //child will automatically saved becuase of cascading effect
        userRepository.save(user);
        return "set user successfully";

    }
}
