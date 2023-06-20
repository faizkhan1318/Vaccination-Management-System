package com.example.vaccineManagement.Services;

import com.example.vaccineManagement.Dtos.RequestDtos.AppointmentReqDto;
import com.example.vaccineManagement.Exceptions.DoctorNotFound;
import com.example.vaccineManagement.Exceptions.UserNotFound;
import com.example.vaccineManagement.Models.Appointment;
import com.example.vaccineManagement.Models.Doctor;
import com.example.vaccineManagement.Models.User;
import com.example.vaccineManagement.Repository.AppointmentRepository;
import com.example.vaccineManagement.Repository.DoctorRepository;
import com.example.vaccineManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;

    public String bookAppointment(AppointmentReqDto appointmentReqDto)throws UserNotFound, DoctorNotFound {
        Optional<Doctor> doctorOptional = doctorRepository.findById(appointmentReqDto.getDocId());

        if(doctorOptional.isEmpty()){
            throw  new DoctorNotFound("Doctor Not Found");
        }
        Optional<User> userOptional = userRepository.findById(appointmentReqDto.getUserId());
        if(userOptional.isEmpty()){
            throw new UserNotFound("User Not Found");
        }

        Doctor doctor = doctorOptional.get();
        User user = userOptional.get();

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(appointmentReqDto.getAppointmentTime());
        appointment.setAppointmentDate(appointmentReqDto.getAppointmentDate());
        //setting the foreign key
        appointment.setUser(user);
        appointment.setDoctor(doctor);

        // now i want to save the appointment and also want to create an id and then i saved its parent
        //saving it before so get the PK of that appointment table
        appointment = appointmentRepository.save(appointment);

        //now add these id into the parent list because we dont want to create new id again
        doctor.getAppointmentList().add(appointment);
        user.getAppointmentList().add(appointment);

        //now save its parent
        doctorRepository.save(doctor);
        userRepository.save(user);

        return "Appointment booked successfully";
    }


}
