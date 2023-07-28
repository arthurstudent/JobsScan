package com.jobsscan.controller;

import com.jobsscan.dto.ResponseDto;
import com.jobsscan.dto.SearchJobRequestDto;
import com.jobsscan.service.ScrapService;
import com.jobsscan.service.impl.ScrapServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs_scan/")
public class ScrapController {

    private final ScrapService scraperService;

    public ScrapController(ScrapServiceImpl scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping(path = "/getAvailableJobs",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseDto getVehicleByModel(SearchJobRequestDto searchJobRequestDto) {
        return scraperService.getAvailableJobs(searchJobRequestDto);
    }
}
