package com.jobsscan.utils;

import com.jobsscan.config.ScrapProperties;
import com.jobsscan.dto.SearchJobRequestDto;
import com.jobsscan.exception.URIException;
import com.jobsscan.utils.enums.URIEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@UtilityClass
public class ScrapHelper {

    public static String buildUrlForScraping(SearchJobRequestDto searchJobRequestDto, ScrapProperties scrapProperties) {

        if (searchJobRequestDto == null) {
            searchJobRequestDto = new SearchJobRequestDto();
        }

        Map<String, String> filterMap = scrapProperties.getFilter();

        var filter = StringUtils.defaultIfBlank(filterMap
                .get(searchJobRequestDto.getFilter().replaceAll(StringUtils.SPACE, StringUtils.EMPTY).toLowerCase()
                        .replaceAll("&", StringUtils.EMPTY)), StringUtils.EMPTY);

        var query = StringUtils.defaultIfBlank(searchJobRequestDto.getQ(), StringUtils.EMPTY);

        URIBuilder uriBuilder;

        try {
            uriBuilder = new URIBuilder(scrapProperties.getUri() + scrapProperties.getJobsPathVariable());
        } catch (URISyntaxException e) {
            log.error("ScrapHelper: unable to build URI");
            throw new URIException(e.getMessage());
        }
        uriBuilder.addParameter(URIEnum.FILTER.getName(), filter);
        uriBuilder.addParameter(URIEnum.QUERY.getName(), query);
        uriBuilder.addParameter(URIEnum.PAGE.getName(), String.valueOf(searchJobRequestDto.getPage()));

        return StringUtils.defaultIfBlank(uriBuilder.toString(), StringUtils.EMPTY);
    }

    public static List<String> getVacUrlsFromCompanies(List<String> urls) {
        return CustomUtils.collectionNullCheck(urls) ? new ArrayList<>() : urls.stream().filter(el -> !el.startsWith("/")).toList();
    }
}
