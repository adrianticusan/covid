package com.covid19.match.amazon.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  Queues {
    USA_COMPLAINS(QueueType.COMPLAIN, Regions.USA, "covid19_com_sqs_complain"),
    USA_BOUNCES(QueueType.BOUNCE, Regions.USA, "covid19_com_sqs_bounce");
    QueueType queueType;
    Regions region;
    String name;
}
