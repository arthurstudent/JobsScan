package com.jobsscan.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum URIEnum {

    FILTER("filer"),
    QUERY("q"),
    PAGE("page"),
    API_KEY("apiKey"),
    LANGUAGE("lang");

    private final String name;
}
