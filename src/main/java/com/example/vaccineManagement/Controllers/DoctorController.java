package com.example.vaccineManagement.Controllers;

import com.example.vaccineManagement.Dtos.RequestDtos.AssociateDocDto;
import com.example.vaccineManagement.Models.Doctor;
import com.example.vaccineManagement.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @PostMapping("/add")
    public String addDoctor(@RequestBody Doctor doctor){

        try {
            String response = doctorService.add(doctor);
            return response;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @PostMapping("/associateWithCenter")
    public ResponseEntity<String> associateDoctor(@RequestBody AssociateDocDto associateDocDto){
        try{
            String result = doctorService.associateDoctor(associateDocDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
