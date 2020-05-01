package com.covid19.match.external.amazon.models;

import lombok.Data;

@Data
public class BounceMessage extends NotificationMessage {
    Bounce bounce;
}
