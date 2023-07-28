package com.jobsscan.mapper;

import com.jobsscan.dto.LocationDto;
import com.jobsscan.utils.enums.GeocodeEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.jobsscan.utils.GeneratorUtils.generateId;
import static com.jobsscan.utils.enums.GeocodeEnum.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationDtoMapper {

    private static final String REMOTE = "Remote";

    private final GeocodeMapper geocodeMapper;

    public LocationDto fromStringToLocationDto(String location) {

        if (StringUtils.isBlank(location)) {
            log.warn("LocationDtoMapper: location is absent");
            return new LocationDto(generateId(), NA.getName(), NA.getName(), NA.getName());
        }

        return location.contains(REMOTE) ? new LocationDto(generateId(), NA.getName(), NA.getName(), REMOTE) :
                extractAddress(location);
    }

    public <T, U> U generalLocationDtoMapper(@NonNull T t, @NonNull Class<U> className) {
        return (U) new ModelMapper().map(t, className);
    }

    private LocationDto extractAddress(String location) {

        Map<String, String> geocodingResource = geocodeMapper.getGeocodingResource(location);

        if (geocodingResource.isEmpty()) {
            log.warn("LocationDtoMapper: Location is not presented");
            return new LocationDto(generateId(), NA.getName(), GeocodeEnum.NA.getName(), GeocodeEnum.NA.getName());
        }

        var country = StringUtils.defaultIfBlank(geocodingResource.get(COUNTRY_CODE.getName()), NA.getName());
        var city = StringUtils.defaultIfBlank(geocodingResource.get(CITY.getName()), NA.getName());

        return new LocationDto(generateId(), country, city, NA.getName());
    }
}
