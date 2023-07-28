package com.jobsscan.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ResponseDto {

    private final List<VacancyDto> vacancyDtoList;

    private final List<String> urlsToCompanyVacancies;

}
