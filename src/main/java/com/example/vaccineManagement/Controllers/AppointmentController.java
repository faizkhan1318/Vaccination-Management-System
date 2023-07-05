package com.example.vaccineManagement.Controllers;

import com.example.vaccineManagement.Dtos.RequestDtos.AppointmentReqDto;
import com.example.vaccineManagement.Dtos.ResponseDtos.DoctorDtoForCenter;
import com.example.vaccineManagement.Models.Appointment;
import com.example.vaccineManagement.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/book")
    public String bookAppointment(@RequestBody AppointmentReqDto appointmentReqDto){
        try{
            String result = appointmentService.bookAppointment(appointmentReqDto);
            return result;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @GetMapping("/allDoctors/{centerId}")
    public ResponseEntity<List<DoctorDtoForCenter>> getAllDoctorByCenterId(@PathVariable("centerId") Integer centerId){
        try {
            List<DoctorDtoForCenter> list = appointmentService.getAllDoctorByCenterId(centerId);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allMaleDoctors/{centerId}")
    public ResponseEntity<List<DoctorDtoForCenter>> getAllMaleDoctorsByCenterId(@PathVariable Integer centerId) {
        try {
            List<DoctorDtoForCenter> list = appointmentService.getAllMaleDoctorsByCenterId(centerId);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (Exception re) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/allFemaleDoctors/{centerId}")
    public ResponseEntity<List<DoctorDtoForCenter>> getAllFemaleDoctorsByCenterId(@PathVariable Integer centerId) {
        try {
            List<DoctorDtoForCenter> list = appointmentService.getAllFemaleDoctorsByCenterId(centerId);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (Exception re) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
