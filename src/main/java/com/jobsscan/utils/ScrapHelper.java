package com.jobsscan.utils;

import com.jobsscan.config.ScrapProperties;
import com.jobsscan.dto.SearchJobRequestDto;
import com.jobsscan.exception.URIException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
@Component
public class ScrapHelper {

    public String buildUrlForScraping(SearchJobRequestDto searchJobRequestDto, ScrapProperties scrapProperties) {

        if (searchJobRequestDto == null) {
            searchJobRequestDto = new SearchJobRequestDto();
        }

        Map<String, String> filterMap = scrapProperties.getFilter();

        String filter = StringUtils.defaultIfBlank(filterMap.get(searchJobRequestDto.getFilter()), StringUtils.EMPTY);
        String query = StringUtils.defaultIfBlank(searchJobRequestDto.getQ(), StringUtils.EMPTY);

        URIBuilder uriBuilder;

        try {
            uriBuilder = new URIBuilder(scrapProperties.getUrl() + scrapProperties.getJobsPathVariable());
        } catch (URISyntaxException e) {
            log.error("ScrapHelper: unable to build URI");
            throw new URIException(e.getMessage());
        }
        uriBuilder.addParameter(URIEnum.FILTER.getName(), filter);
        uriBuilder.addParameter(URIEnum.QUERY.getName(), query);
        uriBuilder.addParameter(URIEnum.PAGE.getName(), String.valueOf(searchJobRequestDto.getPage()));

        return StringUtils.defaultIfBlank(uriBuilder.toString(), StringUtils.EMPTY);
    }
}
