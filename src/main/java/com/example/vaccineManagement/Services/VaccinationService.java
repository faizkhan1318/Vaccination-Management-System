package com.example.vaccineManagement.Services;

import com.example.vaccineManagement.Exceptions.VaccinationAddressNotFound;
import com.example.vaccineManagement.Models.VaccinationCenter;
import com.example.vaccineManagement.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationService {

    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;

    public String addVaccinationCenter(VaccinationCenter vaccinationCenter) throws VaccinationAddressNotFound {

        if(vaccinationCenter.getAddress()==null){
            throw new VaccinationAddressNotFound("Vaccination Address Not Found");
        }
        vaccinationCenterRepository.save(vaccinationCenter);

        return "Vaccination Center added Successfully";

    }

    public List<String> getAllVaccinationCentres() {
        List<VaccinationCenter> vaccinationCentres = vaccinationCenterRepository.findAll();
        List<String> centresName = new ArrayList<>();
        for(VaccinationCenter vaccinationCentre : vaccinationCentres) {
            centresName.add("name: "+vaccinationCentre.getCenterName()+", Address: "+vaccinationCentre.getAddress());
        }
        return centresName;
    }
}
