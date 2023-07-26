package com.jobsscan.mapper;

import com.jobsscan.domain.LabelEntity;
import com.jobsscan.domain.LocationEntity;
import com.jobsscan.domain.VacancyEntity;
import com.jobsscan.dto.LocationDto;
import com.jobsscan.dto.VacancyDto;
import com.jobsscan.exception.ParseDataException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
public class VacancyDtoMapper {

    private static final String COMA = ",";

    private final LocationDtoMapper locationDtoMapper;

    private final ModelMapper modelMapper;

    public VacancyDtoMapper(LocationDtoMapper locationDtoMapper, ModelMapper modelMapper) {
        this.locationDtoMapper = locationDtoMapper;
        this.modelMapper = modelMapper;
    }

    public List<VacancyDto> fromJobsDocsListToResponseDtosList(List<Document> documents) {
        return documents.stream().map(this::fromDocumentToVacancyDto)
                .sorted(Comparator.comparing(VacancyDto::getPostedDate)).toList();
    }

    public VacancyDto fromVacancyToVacancyDto(VacancyEntity vacancyEntity) {

        Optional<VacancyDto> optMappedResponseDto = Optional.ofNullable(modelMapper.map(vacancyEntity, VacancyDto.class));

        if (optMappedResponseDto.isEmpty()) {
            log.warn("VacancyMapper: Unable to map Vacancy to VacancyDto");
            return new VacancyDto();
        }

        Set<String> labels = vacancyEntity.getLabels()
                .stream()
                .map(LabelEntity::getLaborFunction)
                .collect(Collectors.toSet());

        LocationDto location = modelMapper
                .map(vacancyEntity.getLocations().stream().findFirst().orElseGet(LocationEntity::new), LocationDto.class);

        VacancyDto mappedVacancyDto = optMappedResponseDto.get();
        mappedVacancyDto.setLocation(location);
        mappedVacancyDto.setLaborFunction(labels);

        return mappedVacancyDto;
    }

    private VacancyDto fromDocumentToVacancyDto(Document document) {

        Elements elementsByClass = document.getElementsByClass("sc-dmqHEX sc-hLseeU lcnVyb cBWbiv");

        String logoLink = elementsByClass.select("img").attr("src");
        String companyName = elementsByClass.select("img").attr("alt");

        Elements elements = document.getElementsByClass("sc-beqWaB sc-gueYoa dmdAKU MYFxR");

        String laborFunction = elements.select("div > div:nth-child(1)").text();
        String location = elements.select("div > div:nth-child(2)").text();

        LocationDto locationDto = locationDtoMapper.fromStringToLocationDto(location);

        String postedDate = elements.select("div > div:nth-child(3)").text();
        long unixTimesStamp = fromStringToUnixTimesStamp(postedDate);

        String jobTitle = document.getElementsByClass("sc-beqWaB jqWDOR").text();

        String companyUrl = document.getElementsByClass("sc-beqWaB dqlQzp").select("a").attr("href");
        String description = String.valueOf(Jsoup.parse(document
                .getElementsByClass("sc-beqWaB fmCCHr").html()));

        return VacancyDto.builder()
                .logoLink(logoLink)
                .companyName(companyName)
                .laborFunction(split(laborFunction))
                .locationDto(locationDto)
                .postedDate(unixTimesStamp)
                .jobTitle(jobTitle)
                .companyUrl(companyUrl)
                .description(description)
                .build();
    }

    private long fromStringToUnixTimesStamp(String postedDate) {
        int indexOf = postedDate.indexOf(COMA);
        String[] split = StringUtils
                .substring(postedDate, indexOf + 1)
                .trim()
                .replaceAll(COMA, StringUtils.EMPTY)
                .split(StringUtils.SPACE);

        int month = fromNumberToMonth(() -> split[0].substring(0, 3));
        int date = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);

        return totUnixTime(year, month, date);
    }

    private int fromNumberToMonth(Supplier<String> monthName) {
        Date date;
        try {
            date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(monthName.get());
        } catch (ParseException e) {
            log.error("VacancyDtoMapper: Unable to parse date to SimpleDateFormat");
            throw new ParseDataException(e.getMessage());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    private long totUnixTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date givenDate = calendar.getTime();
        return givenDate.toInstant().getEpochSecond();
    }

    private Set<String> split(String value) {
        if (value.contains(COMA))
            return Arrays.stream(value.split(COMA)).collect(Collectors.toSet());
        return Set.of(value);
    }
}
