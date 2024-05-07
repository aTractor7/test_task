package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class DatesDTO {

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "You should enter two dates.")
    @Past(message = "Birth date should be in past")
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "You should enter two dates.")
    private LocalDate end;
}
