package com.covid19.match.external.pepipost;

import com.covid19.match.external.pepipost.model.From;
import com.covid19.match.external.pepipost.model.Personalization;
import com.covid19.match.external.pepipost.model.Request;
import com.covid19.match.external.pepipost.model.Settings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
@Slf4j
public class PepipostService {
    private String apiKey;
    private RestTemplate restTemplate;
    private static final String API_URL = "https://api.pepipost.com/v2/sendEmail";

    public PepipostService(@Value("${email.api.key}") String apiKey, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public void sendEmail(String fromEmail, String toEmail, String subject, String content, String fromName) {
        HttpHeaders headers = prepareHeaders();
        Settings settings = getSettings();

        From from = new From();
        from.setFromEmail(fromEmail);
        from.setFromName(fromName);
        Personalization personalization = new Personalization();
        personalization.setRecipient(toEmail);

        Request request = new Request();
        request.setContent(content);
        request.setSubject(subject);
        request.setFrom(from);
        request.setSettings(settings);
        request.setPersonalizations(Set.of(personalization));

        HttpEntity<Request> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        log.info("Mail response {}", response);
    }

    private Settings getSettings() {
        Settings settings = new Settings();
        settings.setFooter(true);
        settings.setUnsubscribe(false);
        settings.setClicktrack(true);
        settings.setOpentrack(true);
        return settings;
    }

    private HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("api_key", apiKey);
        return headers;
    }
}
