package com.jobsscan.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeocodeEnum {

    COUNTRY_CODE("countryCode"),
    CITY("city"),
    ADDRESS("address"),
    ITEMS("items"),
    NA("N/A");

    private final String name;
}
