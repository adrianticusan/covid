package com.covid19.match.external.google.api;

import com.covid19.match.external.google.api.model.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleRecaptchaApi {
    private String googleRecaptchaSecret;
    private String recaptchaUrl;
    private RestTemplate restTemplate;

    @Autowired
    public GoogleRecaptchaApi(@Value("${google.recaptcha.secretKey}") String googleRecaptchaSecret,
                              @Value("${google.recaptcha.url}") String recaptchaUrl, RestTemplate restTemplate) {
        this.googleRecaptchaSecret = googleRecaptchaSecret;
        this.recaptchaUrl = recaptchaUrl;
        this.restTemplate = restTemplate;
    }

    public boolean validateCaptcha(String token) {
        String uriString = UriComponentsBuilder.fromHttpUrl(recaptchaUrl)
                .queryParam("secret", googleRecaptchaSecret)
                .queryParam("response", token)
                .toUriString();

        ResponseEntity<RecaptchaResponse> recaptchaResponse = restTemplate.postForEntity(uriString, null, RecaptchaResponse.class);

        return recaptchaResponse.getStatusCode().is2xxSuccessful() && recaptchaResponse.getBody().isSuccess();
    }

}
