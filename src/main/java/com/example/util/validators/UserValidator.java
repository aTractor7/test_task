package com.example.util.validators;

import com.example.entity.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Value("${validation.user.min_age}")
    private int minAge;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if(user.getBirthDate().plusYears(minAge).isAfter(LocalDate.now())) {
            errors.rejectValue("birthDate", "400", "Sorry, you should be at lest " + minAge + " years old.");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "400", "Sorry, user with this email is already exist.");
        }
    }
}
