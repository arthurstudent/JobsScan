package com.jobsscan.service;

import com.jobsscan.dto.ResponseDto;
import com.jobsscan.dto.SearchJobRequestDto;

public interface ScrapService {

    ResponseDto getAvailableJobs(SearchJobRequestDto searchJobRequest);
}
