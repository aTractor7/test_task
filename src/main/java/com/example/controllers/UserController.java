package com.example.controllers;

import com.example.dto.AddressDTO;
import com.example.dto.DatesDTO;
import com.example.dto.UserDTO;
import com.example.entity.Address;
import com.example.entity.User;
import com.example.services.AddressService;
import com.example.services.UserService;
import com.example.util.dto_entity_converters.AddressDTOConverter;
import com.example.util.dto_entity_converters.UserDTOConverter;
import com.example.util.error.ErrorResponse;
import com.example.util.validators.DatesDTOValidator;
import com.example.util.validators.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.util.error.ErrorsUtil.generateErrorMessage;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AddressService addressService;
    private final UserValidator userValidator;
    private final DatesDTOValidator datesDTOValidator;
    private final UserDTOConverter userDTOConverter;
    private final AddressDTOConverter addressDTOConverter;

    @Autowired
    public UserController(UserService userService, AddressService addressService, UserValidator userValidator, DatesDTOValidator datesDTOValidator, UserDTOConverter userDTOConverter, AddressDTOConverter addressDTOConverter) {
        this.userService = userService;
        this.addressService = addressService;
        this.userValidator = userValidator;
        this.datesDTOValidator = datesDTOValidator;
        this.userDTOConverter = userDTOConverter;
        this.addressDTOConverter = addressDTOConverter;
    }

    @GetMapping()
    public List<UserDTO> getAll() {
        return userService.findAll().stream().map(userDTOConverter::convertToUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/dates")
    public List<UserDTO> getAllByBirthDateBetween(@RequestBody @Valid DatesDTO datesDTO, BindingResult bindingResult) {
        datesDTOValidator.validate(datesDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(generateErrorMessage(bindingResult.getFieldErrors()));
        }

        return userService.findAllByBirthDateBetween(datesDTO.getStart(), datesDTO.getEnd())
                .stream().map(userDTOConverter::convertToUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getOne(@PathVariable int id) {
        return userDTOConverter.convertToUserDTO(userService.findOne(id));
    }


    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws URISyntaxException {
        User user = userDTOConverter.convertToUser(userDTO);

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(generateErrorMessage(bindingResult.getFieldErrors()));
        }

        userService.save(user);

        URI location = new URI("/users/" + user.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable int id, @RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult) {
        User user = userDTOConverter.convertToUser(userDTO);

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(generateErrorMessage(bindingResult.getFieldErrors()));
        }

        userService.update(id, user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{userId}/address")
    public ResponseEntity<HttpStatus> updateAddress(@PathVariable int userId, @RequestBody @Valid AddressDTO addressDTO,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(generateErrorMessage(bindingResult.getFieldErrors()));
        }

        Address address = addressDTOConverter.convertToAddress(addressDTO);

        User user = userService.findOne(userId);
        int addressId = user.getAddress().getId();

        address.setUser(user);

        addressService.update(addressId, address);


        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NoSuchElementException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
