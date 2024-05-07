package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "country")
    @NotBlank
    @Size(max = 30, message = "Country name should be shorter then 30 characters.")
    private String country;

    @Column(name = "city")
    @NotBlank
    @Size(max = 30, message = "City name should be shorter then 30 characters.")
    private String city;

    @Column(name = "street")
    @Size(max = 40, message = "Street name should be shorter then 30 characters.")
    private String street;

    @Column(name = "house")
    @Size(max = 20, message = "House index should be shorter then 20 characters.")
    private String house;

    @Column(name = "post_index")
    private String postIndex;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
