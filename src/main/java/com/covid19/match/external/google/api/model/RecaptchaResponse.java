package com.covid19.match.external.google.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecaptchaResponse {
    boolean success;
    @JsonProperty("error-codes")
    String [] errorCodes;
}
