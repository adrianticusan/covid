package com.covid19.match.external.amazon.models;

import lombok.Data;

@Data
public class ComplaintMessage extends NotificationMessage {
    Complaint complaint;

}
