package com.covid19.match.dtos;

import com.covid19.match.validators.TooManyMessagesConstraint;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ContactDto {
    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    @Email
    @TooManyMessagesConstraint
    private String email;

    @NotBlank
    @Size(min = 3, max = 150)
    private String notes;
}
