package com.example.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {

    private int id;

    @NotBlank
    @Size(max = 30, message = "Country name should be shorter then 30 characters.")
    private String country;

    @NotBlank
    @Size(max = 30, message = "City name should be shorter then 30 characters.")
    private String city;

    @Size(max = 40, message = "Street name should be shorter then 30 characters.")
    private String street;

    @Size(max = 20, message = "House index should be shorter then 20 characters.")
    private String house;

    @Column(name = "post_index")
    private String postIndex;
}
