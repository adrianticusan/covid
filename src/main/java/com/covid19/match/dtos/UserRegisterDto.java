package com.covid19.match.dtos;

import com.covid19.match.validation.groups.VolunteerValidation;
import com.covid19.match.validators.EmailDuplicateConstraint;
import com.covid19.match.validators.RecaptchaConstraint;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import javax.validation.constraints.*;

@Data
public class UserRegisterDto {
    @NotBlank
    @Size(min = 3)
    private String firstName;

    @NotBlank
    @Size(min = 3)
    private String lastName;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{10})")
    @Size(min = 10)
    private String phoneNumber;

    @NotBlank
    @Email
    @EmailDuplicateConstraint
    private String email;

    @NotBlank(groups = VolunteerValidation.class)
    @Size(groups = VolunteerValidation.class, min = 6)
    private String password;

    @NotNull
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String locality;

    @NotBlank
    private String streetAddress;

    private String streetNumber;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Transient
    private String originalPassword;

    @RecaptchaConstraint
    private String captchaResponse;

    private Boolean isVolunteer;

    @NotNull(groups = VolunteerValidation.class)
    MultipartFile uploadedFile;
}
