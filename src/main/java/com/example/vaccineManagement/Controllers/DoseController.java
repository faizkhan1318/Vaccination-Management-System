package com.example.vaccineManagement.Controllers;

import com.example.vaccineManagement.Services.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dose")
public class DoseController {
    @Autowired
    DoseService doseService;

    @PostMapping("/givedose")
    public String giveDose(@RequestParam("doseId") String doseId, @RequestParam("userId") Integer userId){
        return doseService.giveDose(doseId, userId);
    }
    @GetMapping("/totalDoseGiven")
    public ResponseEntity<Integer> countAllDosesGiven(){
        try{
            Integer result = doseService.countAllDoseGiven();
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
    }
}
