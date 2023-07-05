package com.example.vaccineManagement.Services;

import com.example.vaccineManagement.Dtos.RequestDtos.AppointmentReqDto;
import com.example.vaccineManagement.Dtos.ResponseDtos.DoctorDtoForCenter;
import com.example.vaccineManagement.Enums.Gender;
import com.example.vaccineManagement.Exceptions.DoctorNotFound;
import com.example.vaccineManagement.Exceptions.UserNotFound;
import com.example.vaccineManagement.Exceptions.VaccinationAddressNotFound;
import com.example.vaccineManagement.Models.Appointment;
import com.example.vaccineManagement.Models.Doctor;
import com.example.vaccineManagement.Models.User;
import com.example.vaccineManagement.Models.VaccinationCenter;
import com.example.vaccineManagement.Repository.AppointmentRepository;
import com.example.vaccineManagement.Repository.DoctorRepository;
import com.example.vaccineManagement.Repository.UserRepository;
import com.example.vaccineManagement.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;
    @Autowired
    private JavaMailSender mailSender;

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

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String body = "Dear"+user.getName()+",\n\nI hope this mail finds you well. \n" +
                "I am writing to inform you that your appointment has been successfully booked. \n" +
                "We are pleased to confirm that your preferred date and time have been secured.\n \n" +
                "Appointment Details:\n\n" +
                "Date: "+appointment.getAppointmentDate()+"\n" +
                "Time: "+appointment.getAppointmentTime()+"\n" +
                "Location: "+doctor.getVaccinationCenter().getAddress()+"\n\n"+
                "Thank You";

        simpleMailMessage.setFrom("");
        simpleMailMessage.setTo(user.getEmailId());
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject("Appointment Confirmation");
        mailSender.send(simpleMailMessage);

        return "Appointment booked successfully";
    }


    public List<DoctorDtoForCenter> getAllDoctorByCenterId(Integer centerId) throws VaccinationAddressNotFound {
        Optional<VaccinationCenter> centerOptional= vaccinationCenterRepository.findById(centerId);
        if(centerOptional.isEmpty()){
            throw new VaccinationAddressNotFound("Vaccination Center Not Found");
        }

        List<Doctor> doctors = centerOptional.get().getDoctorList();
        List<DoctorDtoForCenter> doctorList = new ArrayList<>();
        for(Doctor doctor : doctors){
            DoctorDtoForCenter doctorDtoForCenter = DoctorDtoForCenter.builder()
                    .name(doctor.getName())
                    .age(doctor.getAge())
                    .gender(doctor.getGender())
                    .build();
            doctorList.add(doctorDtoForCenter);
        }
        return doctorList;
    }

    public List<DoctorDtoForCenter> getAllMaleDoctorsByCenterId(Integer centerId) throws VaccinationAddressNotFound{
        Optional<VaccinationCenter> centerOptional= vaccinationCenterRepository.findById(centerId);
        if(centerOptional.isEmpty()){
            throw new VaccinationAddressNotFound("Vaccination Center Not Found");
        }

        List<Doctor> doctors = centerOptional.get().getDoctorList();
        List<DoctorDtoForCenter> doctorList = new ArrayList<>();
        for(Doctor doctor : doctors){
            DoctorDtoForCenter doctorDtoForCenter = DoctorDtoForCenter.builder()
                    .name(doctor.getName())
                    .age(doctor.getAge())
                    .gender(doctor.getGender())
                    .build();
            if(doctor.getGender().equals(Gender.MALE)) {
                doctorList.add(doctorDtoForCenter);
            }
        }
        return doctorList;
    }

    public List<DoctorDtoForCenter> getAllFemaleDoctorsByCenterId(Integer centerId) throws VaccinationAddressNotFound {
        Optional<VaccinationCenter> centerOptional= vaccinationCenterRepository.findById(centerId);
        if(centerOptional.isEmpty()){
            throw new VaccinationAddressNotFound("Vaccination Center Not Found");
        }

        List<Doctor> doctors = centerOptional.get().getDoctorList();
        List<DoctorDtoForCenter> doctorList = new ArrayList<>();
        for(Doctor doctor : doctors){
            DoctorDtoForCenter doctorDtoForCenter = DoctorDtoForCenter.builder()
                    .name(doctor.getName())
                    .age(doctor.getAge())
                    .gender(doctor.getGender())
                    .build();
            if(doctor.getGender().equals(Gender.FEMALE)) {
                doctorList.add(doctorDtoForCenter);
            }
        }
        return doctorList;
    }
}
