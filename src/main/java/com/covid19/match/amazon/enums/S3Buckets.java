package com.covid19.match.amazon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  S3Buckets {
    EUROPE_BUCKET(Regions.EUROPE, "upload-ids"),
    USA_BUCKET(Regions.USA, "upload-ids");

    Regions region;
    String name;
}
