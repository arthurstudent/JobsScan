package com.jobsscan.service;

import com.jobsscan.dto.VacancyDto;

import java.util.List;

public interface VacancyService {

    VacancyDto saveVacancy(VacancyDto vacancyDto);

    VacancyDto findByPublicId(String PublicId);

    List<VacancyDto> getSavedVacancies(int page, int limit);

    List<VacancyDto> getVacanciesByCountry(String country);

    List<VacancyDto> getVacanciesByCity(String city);

    List<VacancyDto> getSortedByParam(String paramName, boolean isDescending);

    void deleteByPublicId(String publicId);
}
