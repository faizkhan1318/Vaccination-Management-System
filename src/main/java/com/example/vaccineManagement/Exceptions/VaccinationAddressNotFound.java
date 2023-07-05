package com.example.vaccineManagement.Exceptions;

import com.example.vaccineManagement.Repository.VaccinationCenterRepository;

public class VaccinationAddressNotFound extends Exception{

    public VaccinationAddressNotFound(String message){
        super(message);
    }

}
