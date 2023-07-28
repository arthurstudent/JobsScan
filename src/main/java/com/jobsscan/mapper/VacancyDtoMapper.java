package com.jobsscan.mapper;

import com.jobsscan.domain.LocationEntity;
import com.jobsscan.domain.VacancyEntity;
import com.jobsscan.dto.LaborFunctionDto;
import com.jobsscan.dto.LocationDto;
import com.jobsscan.dto.VacancyDto;
import com.jobsscan.exception.ParseDataException;
import com.jobsscan.utils.GeneratorUtils;
import com.jobsscan.utils.CustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class VacancyDtoMapper {

    private static final String COMA = ",";

    private final LocationDtoMapper locationDtoMapper;

    private final LaborFuncDtoMapper laborFuncDtoMapper;

    public List<VacancyDto> fromJobsDocsListToResponseDtosList(List<Document> documents) {
        return CustomUtils.collectionNullCheck(documents) ? new ArrayList<>() :
                documents.stream()
                        .map(doc -> fromDocumentToVacancyDto(doc))
                        .sorted(Comparator.comparing(VacancyDto::getPostedDate)).toList();
    }

    public VacancyDto fromVacancyToVacancyDto(VacancyEntity vacancyEntity) {

        var optMappedResponseDto = Optional.ofNullable(new ModelMapper().map(vacancyEntity, VacancyDto.class));

        if (optMappedResponseDto.isEmpty()) {
            log.warn("VacancyMapper: Unable to map Vacancy to VacancyDto");
            return new VacancyDto();
        }

        Set<LaborFunctionDto> laborFunctionDtos = vacancyEntity.getLabels().stream()
                .map(el-> laborFuncDtoMapper.generalLaborFuncDtoMapper(el, LaborFunctionDto.class))
                .collect(Collectors.toSet());

        var location = locationDtoMapper.generalLocationDtoMapper(vacancyEntity.getLocations()
                .stream()
                .findFirst()
                .orElseGet(LocationEntity::new), LocationDto.class);

        var mappedVacancyDto = optMappedResponseDto.get();
        mappedVacancyDto.setLocation(location);
        mappedVacancyDto.setLaborFunctions(laborFunctionDtos);

        return mappedVacancyDto;
    }

    private VacancyDto fromDocumentToVacancyDto(Document document) {

        var elementsByClass = document.getElementsByClass("sc-dmqHEX sc-hLseeU lcnVyb cBWbiv");

        var logoLink = elementsByClass.select("img").attr("src");
        var companyName = elementsByClass.select("img").attr("alt");

        var elements = document.getElementsByClass("sc-beqWaB sc-gueYoa dmdAKU MYFxR");

        var laborFunction = elements.select("div > div:nth-child(1)").text();
        var location = elements.select("div > div:nth-child(2)").text();

        var locationDto = locationDtoMapper.fromStringToLocationDto(location);

        var postedDate = elements.select("div > div:nth-child(3)").text();
        long unixTimesStamp = fromStringToUnixTimesStamp(postedDate);

        var jobTitle = document.getElementsByClass("sc-beqWaB jqWDOR").text();

        var companyUrl = document.getElementsByClass("sc-beqWaB dqlQzp").select("a").attr("href");
        var description = String.valueOf(Jsoup.parse(document
                .getElementsByClass("sc-beqWaB fmCCHr").html()));

        return VacancyDto.builder()
                .logoLink(logoLink)
                .companyName(companyName)
                .laborFunction(laborFuncDtoMapper.toLaborDto(laborFunction))
                .locationDto(locationDto)
                .postedDate(unixTimesStamp)
                .jobTitle(jobTitle)
                .companyUrl(companyUrl)
                .description(description)
                .publicId(GeneratorUtils.generateId())
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
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    private long totUnixTime(int year, int month, int day) {
        var calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        var givenDate = calendar.getTime();
        return givenDate.toInstant().getEpochSecond();
    }
}
