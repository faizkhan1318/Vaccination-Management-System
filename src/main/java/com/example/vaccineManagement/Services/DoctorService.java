package com.example.vaccineManagement.Services;

import com.example.vaccineManagement.Dtos.RequestDtos.AssociateDocDto;
import com.example.vaccineManagement.Exceptions.CenterNotFound;
import com.example.vaccineManagement.Exceptions.DoctorAlreadyExistsException;
import com.example.vaccineManagement.Exceptions.DoctorNotFound;
import com.example.vaccineManagement.Exceptions.EmailIdEmptyException;
import com.example.vaccineManagement.Models.Doctor;
import com.example.vaccineManagement.Models.VaccinationCenter;
import com.example.vaccineManagement.Repository.DoctorRepository;
import com.example.vaccineManagement.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

    public String add(Doctor doctor) throws EmailIdEmptyException, DoctorAlreadyExistsException {

        if(doctor.getEmailId()==(null)){
            throw new EmailIdEmptyException("Email ID is mandatory");
        }
        if(doctorRepository.findByEmailId(doctor.getEmailId())!=null){
            throw new DoctorAlreadyExistsException("Doctor emailId already Register");
        }

        doctorRepository.save(doctor);

        return "Doctor has been added in database";
    }

    public String associateDoctor(AssociateDocDto associateDocDto)throws DoctorNotFound, CenterNotFound {

        Integer docId = associateDocDto.getDocId();
        Optional<Doctor> doctorOpt = doctorRepository.findById(docId);

        if(doctorOpt.isEmpty()){
            throw new DoctorNotFound("Doctor id is wrong");
        }

        Integer centerId = associateDocDto.getCenterId();
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);

        if(centerOpt.isEmpty()){
            throw new CenterNotFound("Center id Incorrect");
        }

        Doctor doctor = doctorOpt.get();
        VaccinationCenter vaccinationCenter = centerOpt.get();

        doctor.setVaccinationCenter(vaccinationCenter);//Setting the Forieng key

        //adding the list of doctor in vaccination center
        vaccinationCenter.getDoctorList().add(doctor);

        vaccinationCenterRepository.save(vaccinationCenter);

        return "Doctor has been associated to the center";

    }
}
