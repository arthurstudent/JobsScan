package com.jobsscan.controller;


import com.jobsscan.dto.VacancyDto;
import com.jobsscan.service.VacancyService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vacancies/")
public class VacancyController {

    private final VacancyService vacancyService;

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @PostMapping(path = "/saveVacancy",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public VacancyDto getSavedVacancies(@Valid @RequestBody VacancyDto vacancyDto) {
        return vacancyService.saveVacancy(vacancyDto);
    }

    @GetMapping(path = "/getVacancyById/{vacancyId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public VacancyDto getSavedVacancies(@PathVariable String vacancyId) {
        return vacancyService.findByPublicId(vacancyId);
    }

    @GetMapping(path = "/getSavedVacancies",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<VacancyDto> getSavedVacancies(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "limit", defaultValue = "25") int limit) {
        return vacancyService.getSavedVacancies(page, limit);
    }

    @GetMapping(path = "/getVacanciesByCountry/{country}")
    public List<VacancyDto> getVacanciesByCountry(@PathVariable String country) {
        return vacancyService.getVacanciesByCountry(country);
    }

    @GetMapping(path = "/getVacanciesByCity/{city}")
    public List<VacancyDto> getVacanciesByCity(@PathVariable String city) {
        return vacancyService.getVacanciesByCity(city);
    }

    @GetMapping(path = "/getSortedVacanciesBy")
    public List<VacancyDto> getSortedVacanciesBy(@RequestParam(value = "sortParam", defaultValue = "companyName") String sortParam,
                                                 @RequestParam(value = "isDesc", defaultValue = "false") boolean isDesc) {
        return vacancyService.getSortedByParam(sortParam, isDesc);
    }

    @DeleteMapping(path = "/deleteById/{vacancyId}")
    public void deleteVacancy(@PathVariable String vacancyId) {
        vacancyService.deleteByPublicId(vacancyId);
    }
}

