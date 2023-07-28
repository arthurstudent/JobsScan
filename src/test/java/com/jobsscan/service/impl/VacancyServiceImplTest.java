package com.jobsscan.service.impl;

import com.jobsscan.Utils.EntityCreatorTestUtils;
import com.jobsscan.domain.VacancyEntity;
import com.jobsscan.dto.LaborFunctionDto;
import com.jobsscan.dto.LocationDto;
import com.jobsscan.dto.VacancyDto;
import com.jobsscan.mapper.GeocodeMapper;
import com.jobsscan.mapper.LaborFuncDtoMapper;
import com.jobsscan.mapper.LocationDtoMapper;
import com.jobsscan.mapper.VacancyDtoMapper;
import com.jobsscan.repository.LabelRepository;
import com.jobsscan.repository.LocationRepository;
import com.jobsscan.repository.VacancyRepository;
import com.jobsscan.validator.VacancyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static com.jobsscan.Utils.EntityCreatorTestUtils.buildLabelEntity;
import static com.jobsscan.Utils.EntityCreatorTestUtils.buildLocationEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VacancyServiceImplTest {

    @Mock
    GeocodeMapper geocodeMapper;

    @Mock
    LabelRepository labelRepository;

    @Mock
    LocationRepository locationRepository;

    @Mock
    VacancyRepository vacancyRepository;

    @Mock
    VacancyValidator vacancyValidator;

    VacancyServiceImpl vacancyService;

    VacancyDtoMapper vacancyMapper;

    LaborFuncDtoMapper laborFuncDtoMapper;

    LocationDtoMapper locationDtoMapper;

    VacancyDto vacancyDto;

    LocationDto locationDto;

    LaborFunctionDto laborFunctionDto;

    VacancyEntity vacancyEntity;

    @BeforeEach
    void setUp() {
        vacancyDto = buildVacancyDto();

        laborFuncDtoMapper = new LaborFuncDtoMapper();
        locationDtoMapper = new LocationDtoMapper(geocodeMapper);
        vacancyMapper = new VacancyDtoMapper(locationDtoMapper, laborFuncDtoMapper);

        vacancyService = new VacancyServiceImpl(vacancyMapper, labelRepository,
                locationRepository, vacancyRepository, vacancyValidator);

        vacancyEntity = buildVacationEntity();
    }

    @Test
    void saveVacancy() {
        doNothing().when(vacancyValidator).validateVacancyPresence(anyBoolean(), anyString(), anyString());
        when(labelRepository.findByLaborFunction(anyString())).thenReturn(null);
        when(vacancyRepository.save(any(VacancyEntity.class))).thenReturn(vacancyEntity);

        VacancyDto savedVacancy = vacancyService.saveVacancy(vacancyDto);

        assertNotNull(savedVacancy);
        assertEquals(vacancyDto.getPublicId(), savedVacancy.getPublicId());

        assertEquals(vacancyDto.getLocation().getCountry(), savedVacancy.getLocation().getCountry());
        assertEquals(vacancyDto.getLocation().getPublicId(), savedVacancy.getLocation().getPublicId());

        assertNotNull(vacancyDto.getLaborFunctions());
        assertEquals(vacancyDto.getLaborFunctions().size(), 1);
    }

    @Test
    void findByPublicId() {
        when(vacancyRepository.findByPublicId(anyString())).thenReturn(vacancyEntity);

        VacancyDto byPublicId = vacancyService.findByPublicId(EntityCreatorTestUtils.vacancyPublicId);

        assertNotNull(byPublicId);
        assertEquals(vacancyDto.getPublicId(), byPublicId.getPublicId());
    }

    private VacancyEntity buildVacationEntity() {

        VacancyEntity vacancyEntity = new VacancyEntity();
        vacancyEntity.addLocation(buildLocationEntity());
        vacancyEntity.addLabel(buildLabelEntity());
        vacancyEntity.setCompanyName(EntityCreatorTestUtils.companyName);
        vacancyEntity.setDescription(EntityCreatorTestUtils.description);
        vacancyEntity.setPostedDate(EntityCreatorTestUtils.postedDate);
        vacancyEntity.setJobTitle(EntityCreatorTestUtils.jobTitle);
        vacancyEntity.setLogoLink(EntityCreatorTestUtils.logoLink);
        vacancyEntity.setPublicId(EntityCreatorTestUtils.vacancyPublicId);
        vacancyEntity.setCompanyUrl(EntityCreatorTestUtils.companyUrl);

        return vacancyEntity;
    }

    private VacancyDto buildVacancyDto() {
        locationDto = new LocationDto(EntityCreatorTestUtils.locationPublicId, EntityCreatorTestUtils.country,
                EntityCreatorTestUtils.city, EntityCreatorTestUtils.remote);
        laborFunctionDto = new LaborFunctionDto(EntityCreatorTestUtils.laborFuncPublicId, EntityCreatorTestUtils.laborFuncName);

        return VacancyDto.builder()
                .locationDto(locationDto)
                .laborFunction(Set.of(laborFunctionDto))
                .companyName(EntityCreatorTestUtils.companyName)
                .companyUrl(EntityCreatorTestUtils.companyUrl)
                .description(EntityCreatorTestUtils.description)
                .postedDate(EntityCreatorTestUtils.postedDate)
                .publicId(EntityCreatorTestUtils.vacancyPublicId)
                .logoLink(EntityCreatorTestUtils.logoLink)
                .jobTitle(EntityCreatorTestUtils.jobTitle)
                .build();
    }
}