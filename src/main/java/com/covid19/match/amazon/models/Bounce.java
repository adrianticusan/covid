package com.covid19.match.amazon.models;

import lombok.Data;

@Data
public class Bounce {
    String bounceType;
    BouncedRecipient [] bouncedRecipients;
}
