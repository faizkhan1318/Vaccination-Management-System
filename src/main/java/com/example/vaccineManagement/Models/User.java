package com.example.vaccineManagement.Models;

import com.example.vaccineManagement.Enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // it genrated uniqueid automatically
    private int userId;
    @Column(name = "user_name")
    private String name;
    private int age;
    @Column(unique = true)
    private String emailId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String mobileNo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Dose dose;


}
