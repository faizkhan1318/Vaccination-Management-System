package com.example.vaccineManagement.Repository;

import com.example.vaccineManagement.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    public Doctor findByEmailId(String emailId);
}
