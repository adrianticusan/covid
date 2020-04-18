package com.covid19.match.enums;

import com.covid19.match.dtos.MailingDto;

public enum MailingTypes {
    REGISTER_USER("register.subject.user", "register.content.volunteer"),
    REGISTER_VOLUNTEER("register.subject.user", "register.content.volunteer");

    private MailingDto<?> mailingExtraInformation;
    private String contentKey;
    private String subjectKey;

    MailingTypes(String subjectKey, String contentKey) {
        this.subjectKey = subjectKey;
        this.contentKey = contentKey;
    }

}
