package com.jobsscan.mapper;

import com.jobsscan.dto.LocationDto;
import com.jobsscan.exception.ParseDataException;
import com.jobsscan.utils.GeocodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Map;

import static com.jobsscan.utils.GeocodeEnum.COUNTRY_CODE;
import static com.jobsscan.utils.GeocodeEnum.CITY;
import static com.jobsscan.utils.GeocodeEnum.NA;

@Slf4j
@Component
public class LocationDtoMapper {

    private static final String REMOTE = "Remote";

    private final GeocodeMapper geocodeMapper;

    private final ModelMapper modelMapper;

    public LocationDtoMapper(GeocodeMapper geocodeMapper, ModelMapper modelMapper) {
        this.geocodeMapper = geocodeMapper;
        this.modelMapper = modelMapper;
    }

    public LocationDto fromStringToLocationDto(String location) {

        if (StringUtils.isBlank(location)) {
            log.warn("LocationDtoMapper: location is absent");
            return new LocationDto(NA.getName(), NA.getName(), NA.getName());
        }

        return location.contains(REMOTE) ? new LocationDto(NA.getName(), NA.getName(), REMOTE) :
                extractAddress(location);
    }

//    public LocationEntity fromLocationDtoToLocalEntity(LocationDto locationDto) {
//        if (locationDto == null) {
//            log.warn("Unable to map LocationDto to LocationEntity because locationDto is null");
//            return new LocationEntity();
//        }
//        return modelMapper.map(locationDto, LocationEntity.class);
//    }

    public <T, U> U generalLocationDtoLocationEntityMapper(T t, Class<U> className) {
        if (t == null) {
            log.error("LocationDtoMapper: unable to map object to class {}", className.getName());
             throw new ParseDataException("Unable to map object to class" + className.getName());
        }
        return (U) modelMapper.map(t, className);
    }

    private LocationDto extractAddress(String location) {

        Map<String, String> geocodingResource = geocodeMapper.getGeocodingResource(location);

        if (geocodingResource.isEmpty()) {
            log.warn("LocationDtoMapper: Location is not presented");
            return new LocationDto(NA.getName(), GeocodeEnum.NA.getName(), GeocodeEnum.NA.getName());
        }

        String country = StringUtils.defaultIfBlank(geocodingResource.get(COUNTRY_CODE.getName()), NA.getName());
        String city = StringUtils.defaultIfBlank(geocodingResource.get(CITY.getName()), NA.getName());

        return new LocationDto(country, city, NA.getName());
    }
}
