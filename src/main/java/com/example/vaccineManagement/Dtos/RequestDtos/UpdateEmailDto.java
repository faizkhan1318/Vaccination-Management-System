package com.example.vaccineManagement.Dtos.RequestDtos;

import lombok.*;

@Data
public class UpdateEmailDto {
    private int userId;
    private String newEmailId;
}
