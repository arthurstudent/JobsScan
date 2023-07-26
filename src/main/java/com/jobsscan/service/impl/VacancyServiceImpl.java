package com.jobsscan.service.impl;

import com.jobsscan.domain.LabelEntity;
import com.jobsscan.domain.LocationEntity;
import com.jobsscan.domain.VacancyEntity;
import com.jobsscan.dto.LocationDto;
import com.jobsscan.dto.VacancyDto;
import com.jobsscan.exception.VacancyException;
import com.jobsscan.mapper.LocationDtoMapper;
import com.jobsscan.mapper.VacancyDtoMapper;
import com.jobsscan.repository.JobRepository;
import com.jobsscan.service.LabelService;
import com.jobsscan.service.LocationService;
import com.jobsscan.service.VacancyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VacancyServiceImpl implements VacancyService {

    private final JobRepository repository;

    private final ModelMapper modelMapper;

    private final LocationService locationService;

    private final LabelService labelService;

    private final LocationDtoMapper locationDtoMapper;

    private final VacancyDtoMapper vacancyMapper;

    public VacancyServiceImpl(JobRepository repository, ModelMapper modelMapper,
                              LocationService locationService, LabelService labelService,
                              LocationDtoMapper locationDtoMapper, VacancyDtoMapper vacancyMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.locationService = locationService;
        this.labelService = labelService;
        this.locationDtoMapper = locationDtoMapper;
        this.vacancyMapper = vacancyMapper;
    }

    @Transactional
    @Override
    public VacancyDto saveVacancy(VacancyDto vacancyDto) {

        if (repository.findJobByCompanyUrl(vacancyDto.getCompanyUrl()) != null) {
            log.warn("VacancyServiceImpl: vacancy is already saved");
            throw new VacancyException("Already exist");
        }

        VacancyEntity vacancyEntity = modelMapper.map(vacancyDto, VacancyEntity.class);

        LocationDto locationDto = vacancyDto.getLocation();
        LocationEntity locationEntity = locationDtoMapper.generalLocationDtoLocationEntityMapper(locationDto, LocationEntity.class);
        Set<LocationEntity> savedIfExistLocations = Set.of(locationService.saveIfNotExist(locationEntity));

        Set<LabelEntity> labelEntities = vacancyDto.getLaborFunction().stream().map(str -> {
            LabelEntity labelEntity = new LabelEntity();
            labelEntity.setLaborFunction(str);
            return labelEntity;
        }).collect(Collectors.toSet());


        Set<LabelEntity> savedIfExistLabels = labelEntities.stream().map(labelService::saveIfNotExist).collect(Collectors.toSet());


        for (LabelEntity lab: savedIfExistLabels) {
            vacancyEntity.addLabel(lab);
            lab.addVacancy(vacancyEntity);
        }

        for (LocationEntity lab: savedIfExistLocations) {
            vacancyEntity.addLocation(lab);
            lab.addVacancy(vacancyEntity);
        }


//        jobEntity.setLocations(savedIfExistLocations);
//        jobEntity.setLabels(savedIfExistLabels);

        VacancyEntity savedJob = repository.save(vacancyEntity);

        return vacancyMapper.fromVacancyToVacancyDto(savedJob);
    }
}
