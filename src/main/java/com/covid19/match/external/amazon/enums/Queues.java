package com.covid19.match.external.amazon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Queues {
    USA_COMPLAINS(QueueType.COMPLAIN, Regions.USA, "covid19_com_sqs_complain"),
    USA_BOUNCES(QueueType.BOUNCE, Regions.USA, "covid19_com_sqs_bounce");
    QueueType queueType;
    Regions region;
    String name;
}
