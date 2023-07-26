package com.jobsscan.controller;

import com.jobsscan.dto.VacancyDto;
import com.jobsscan.dto.SearchJobRequestDto;
import com.jobsscan.service.VacancyService;
import com.jobsscan.service.ScrapService;
import com.jobsscan.service.impl.ScrapServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class ScrapController {

    private final ScrapService scraperService;

    private final VacancyService vacancyService;

    public ScrapController(ScrapServiceImpl scraperService, VacancyService vacancyService) {
        this.scraperService = scraperService;
        this.vacancyService = vacancyService;
    }

    @GetMapping(path = "/getAvailableJobs",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<VacancyDto> getVehicleByModel(SearchJobRequestDto searchJobRequestDto) {
        return scraperService.getAvailableJobs(searchJobRequestDto);
    }


    @PostMapping(path = "/saveVacancy",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public VacancyDto saveVacancy(@RequestBody VacancyDto vacancyDto) {
        return vacancyService.saveVacancy(vacancyDto);
    }
}
