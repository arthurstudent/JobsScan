package com.jobsscan.service.impl;

import com.jobsscan.client.JsoupClient;
import com.jobsscan.config.ScrapProperties;
import com.jobsscan.dto.ResponseDto;
import com.jobsscan.dto.SearchJobRequestDto;
import com.jobsscan.dto.VacancyDto;
import com.jobsscan.mapper.VacancyDtoMapper;
import com.jobsscan.service.ScrapService;
import com.jobsscan.utils.ScrapHelper;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapServiceImpl implements ScrapService {

    private final ScrapProperties scrapProperties;

    private final VacancyDtoMapper vacancyDtoMapper;

    private final JsoupClient jsoupClient;

    public ScrapServiceImpl(ScrapProperties scrapProperties,
                            VacancyDtoMapper vacancyDtoMapper,
                            JsoupClient jsoupClient) {
        this.scrapProperties = scrapProperties;
        this.vacancyDtoMapper = vacancyDtoMapper;
        this.jsoupClient = jsoupClient;
    }

    public ResponseDto getAvailableJobs(SearchJobRequestDto searchJobRequest) {

        var urlForScraping = ScrapHelper.buildUrlForScraping(searchJobRequest, scrapProperties);

        List<String> uriList = jsoupClient.extractUrlsFromDoc(urlForScraping);

        List<Document> documentList = jsoupClient.scrapDocsByUrls(uriList, scrapProperties.getUri());

        List<String> vacUrlsFromCompanies = ScrapHelper.getVacUrlsFromCompanies(uriList);

        List<VacancyDto> vacancyDtos = vacancyDtoMapper.fromJobsDocsListToResponseDtosList(documentList);

        return new ResponseDto(vacancyDtos, vacUrlsFromCompanies);
    }
}
