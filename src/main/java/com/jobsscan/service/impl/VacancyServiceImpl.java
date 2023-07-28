package com.jobsscan.service.impl;

import com.jobsscan.domain.LabelEntity;
import com.jobsscan.domain.LocationEntity;
import com.jobsscan.domain.VacancyEntity;
import com.jobsscan.dto.VacancyDto;
import com.jobsscan.exception.VacancyServiceException;
import com.jobsscan.mapper.VacancyDtoMapper;
import com.jobsscan.repository.LabelRepository;
import com.jobsscan.repository.LocationRepository;
import com.jobsscan.repository.VacancyRepository;
import com.jobsscan.service.VacancyService;
import com.jobsscan.validator.VacancyValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyDtoMapper vacancyMapper;

    private final LabelRepository labelRepository;

    private final LocationRepository locationRepository;

    private final VacancyRepository vacancyRepository;

    private final VacancyValidator vacancyValidator;

    @Transactional
    @Override
    public VacancyDto saveVacancy(@NonNull VacancyDto vacancyDto) {

        vacancyValidator.validateVacancyPresence(vacancyRepository.existsByPublicId(vacancyDto.getPublicId()),
                vacancyDto.getPublicId(), "Vacancy with provided id: %s is already exist");

        var vacancyEntity = new ModelMapper().map(vacancyDto, VacancyEntity.class);

        vacancyEntity.setLabels(vacancyDto.getLaborFunctions()
                .stream()
                .map(laborFunctionDto ->
                        vacancyMapper.getLaborFuncDtoMapper()
                                .generalLaborFuncDtoMapper(laborFunctionDto, LabelEntity.class))
                .map(lab -> {
                    LabelEntity byName = labelRepository.findByLaborFunction(lab.getLaborFunction());
                    if (byName == null) {
                        lab.addVacancy(vacancyEntity);
                        return lab;
                    }
                    byName.addVacancy(vacancyEntity);
                    return byName;
                }).collect(Collectors.toSet()));

        vacancyEntity.setLocations(Set.of(vacancyMapper.getLocationDtoMapper()
                        .generalLocationDtoMapper(vacancyDto.getLocation(), LocationEntity.class))
                .stream()
                .map(loc -> {
                    LocationEntity locationByCityAndRemote =
                            locationRepository.getLocationByCityAndRemote(loc.getCity(), loc.getRemote());
                    if (locationByCityAndRemote == null) {
                        loc.addVacancy(vacancyEntity);
                        return loc;
                    }
                    locationByCityAndRemote.addVacancy(vacancyEntity);
                    return locationByCityAndRemote;
                }).collect(Collectors.toSet()));

        var optSavedJob = Optional.of(vacancyRepository.save(vacancyEntity));
        optSavedJob.ifPresent(sj -> log.info("VacancyServiceImpl: Vacancy with provided id: {} was saved", sj.getPublicId()));
        return vacancyMapper.fromVacancyToVacancyDto(optSavedJob
                .orElseThrow(()-> new VacancyServiceException("Vacancy with provided id: %s was not saved".formatted(vacancyDto.getPublicId()))));
    }

    @Override
    public VacancyDto findByPublicId(String vacancyPublicId) {
        var byVacancyPublicId = Optional.ofNullable(vacancyRepository.findByPublicId(vacancyPublicId));

        byVacancyPublicId.ifPresent(el -> log.info("VacancyServiceImpl: Vacancy with provided id: {} is found", vacancyPublicId));

        return vacancyMapper.fromVacancyToVacancyDto(byVacancyPublicId
                .orElseThrow(() -> new VacancyServiceException("Vacancy with provided id: %s is not found".formatted(vacancyPublicId))));
    }

    @Override
    public List<VacancyDto> getSavedVacancies(int page, int limit) {

        if (page > 0) page = page - 1;
        var pageable = PageRequest.of(page, limit);
        Page<VacancyEntity> vacancyPage = vacancyRepository.findAll(pageable);

        List<VacancyEntity> vacancies = vacancyPage.getContent();

        vacancyValidator.validateVacancyList(vacancies, "Vacancies are not found. Page number is too big or db is empty");
        return vacancies.stream()
                .map(vacancyMapper::fromVacancyToVacancyDto)
                .sorted(Comparator.comparing(VacancyDto::getPostedDate))
                .toList();
    }

    @Override
    public List<VacancyDto> getVacanciesByCountry(String country) {
        List<VacancyEntity> vacanciesByCountry = vacancyRepository.getVacanciesByCountry(country);
        vacancyValidator.validateVacancyList(vacanciesByCountry, "Vacancies are not found. There is no saved vacancies in %s".formatted(country));
        return vacanciesByCountry.stream()
                .map(vacancyMapper::fromVacancyToVacancyDto)
                .toList();
    }

    @Override
    public List<VacancyDto> getVacanciesByCity(String city) {
        List<VacancyEntity> vacanciesByCity = vacancyRepository.getVacanciesByCity(city);
        vacancyValidator.validateVacancyList(vacanciesByCity, "Vacancies are not found.  There is no saved vacancies in %s".formatted(city));
        return vacanciesByCity.stream()
                .map(vacancyMapper::fromVacancyToVacancyDto)
                .toList();
    }

    public List<VacancyDto> getSortedByParam(String paramName, boolean isDescending) {
        List<VacancyEntity> sortedBy;

        if (isDescending) {
            sortedBy = IterableUtils.toList(vacancyRepository.findAll(Sort.by(paramName).descending()));
        } else {
            sortedBy = IterableUtils.toList(vacancyRepository.findAll(Sort.by(paramName)));
        }

        vacancyValidator.validateVacancyList(sortedBy, "Unable to sort vacancies by %s".formatted(paramName));
        return sortedBy.stream()
                .map(vacancyMapper::fromVacancyToVacancyDto)
                .toList();
    }

    @Transactional
    @Override
    public void deleteByPublicId(String publicId) {
        vacancyValidator.validateVacancyPresence(!vacancyRepository.existsByPublicId(publicId), publicId,
                "Vacancy with provided id: %s is not found");
        vacancyRepository.deleteByPublicId(publicId);
        log.info("Vacancy with id {} was deleted", publicId);
    }
}
