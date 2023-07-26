package com.jobsscan.service.impl;

import com.jobsscan.config.ScrapProperties;
import com.jobsscan.dto.VacancyDto;
import com.jobsscan.dto.SearchJobRequestDto;
import com.jobsscan.exception.ParseDataException;
import com.jobsscan.mapper.VacancyDtoMapper;
import com.jobsscan.service.ScrapService;
import com.jobsscan.utils.ScrapHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ScrapServiceImpl implements ScrapService {

    private final ScrapProperties scrapProperties;

    private final ScrapHelper scrapHelper;

    private final VacancyDtoMapper vacancyDtoMapper;

    public ScrapServiceImpl(ScrapProperties scrapProperties, ScrapHelper scrapHelper, VacancyDtoMapper vacancyDtoMapper) {
        this.scrapProperties = scrapProperties;
        this.scrapHelper = scrapHelper;
        this.vacancyDtoMapper = vacancyDtoMapper;
    }

    public List<VacancyDto> getAvailableJobs(SearchJobRequestDto searchJobRequest) {

        String urlForScraping = scrapHelper.buildUrlForScraping(searchJobRequest, scrapProperties);

        List<String> urlsList = extractUrlsFromDoc(urlForScraping);

        List<Document> documentList = scrapDocsByUrls(urlsList);

        return vacancyDtoMapper.fromJobsDocsListToResponseDtosList(documentList);
    }

    private List<String> extractUrlsFromDoc(String url) {

        try {
            Document document = Jsoup.connect(url).get();
            Optional<Element> element = Optional.ofNullable(document
                    .getElementsByClass("infinite-scroll-component sc-beqWaB biNQIL").first());

            return element.map(value -> value
                    .getElementsByClass("sc-beqWaB sc-gueYoa lpllVF MYFxR job-info")
                    .select("h4 > a")
                    .stream()
                    .map(el -> el.attr("href"))
                    .toList()).orElseGet(ArrayList::new);
        } catch (IOException ex) {
            throw new ParseDataException(ex.getMessage());
        }
    }

    private List<Document> scrapDocsByUrls(List<String> list) {
        return list.stream().filter(link -> link.startsWith("/")).limit(10)
                .map(link -> scrapProperties.getUrl() + link)
                .map(link -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return Jsoup.connect(link).get();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), completableFutureList ->
                                completableFutureList.stream()
                                        .map(CompletableFuture::join)
                ))
                .toList();
    }
}
