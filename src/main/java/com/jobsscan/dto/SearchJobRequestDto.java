package com.jobsscan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchJobRequestDto {

    private String q;

    private String filter;

    private Integer page = 1;

}
