package com.jobsscan.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeocodeEnum {

    COUNTRY_CODE("countryCode"),
    CITY("city"),
    ADDRESS("address"),
    ITEMS("items"),
    NA("No Data");

    private final String name;
}
