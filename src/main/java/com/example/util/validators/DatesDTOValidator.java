package com.example.util.validators;

import com.example.dto.DatesDTO;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DatesDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DatesDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DatesDTO dates = (DatesDTO) target;

        if(dates.getStart().isAfter(dates.getEnd())) {
            errors.rejectValue("start", "400", "Start date should be before end date.");
        }
    }
}
