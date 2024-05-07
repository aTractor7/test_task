package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {

    //TODO: move massage to properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    @Email
    @Size(max = 50, message = "Email name should be shorter then 50 characters.")
    private String email;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 50, message = "First name should be shorter then 50 characters.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(max = 50, message = "Last name should be shorter then 50 characters.")
    private String lastName;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Birth date is required.")
    @Past(message = "Birth date should be in past")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "This not looks like phone number.")
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Address address;

    public User(int id, String email, String firstName, String lastName, LocalDate birthDate, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
}
