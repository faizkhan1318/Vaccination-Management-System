package com.example.vaccineManagement.Dtos.ResponseDtos;

import com.example.vaccineManagement.Enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDtoForCenter {

    private String name;

    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;
}
