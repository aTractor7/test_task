package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;

    @Email
    @Size(max = 50, message = "Email name should be shorter then 50 characters.")
    private String email;

    @NotBlank
    @Size(max = 50, message = "First name should be shorter then 50 characters.")
    private String firstName;

    @NotBlank
    @Size(max = 50, message = "Last name should be shorter then 50 characters.")
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "Birth date is required.")
    @Past(message = "Birth date should be in past")
    private LocalDate birthDate;

    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "This not looks like phone number.")
    private String phoneNumber;

    private AddressDTO addressDTO;
}
