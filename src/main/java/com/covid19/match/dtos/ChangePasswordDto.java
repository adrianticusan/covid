package com.covid19.match.dtos;

import com.covid19.match.validators.OldPasswordConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordDto {
    @OldPasswordConstraint(message = "{messages.errors.password.match}")
    @Size(min = 6)
    @NotNull
    private String oldPassword;
    @Size(min = 6)
    @NotNull
    private String newPassword;
}
