package com.jobsscan.service;

import com.jobsscan.dto.VacancyDto;
import com.jobsscan.dto.SearchJobRequestDto;

import java.util.List;

public interface ScrapService {

    List<VacancyDto> getAvailableJobs(SearchJobRequestDto searchJobRequest);
}
