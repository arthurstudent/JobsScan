package com.jobsscan.service.impl;

import com.jobsscan.domain.LocationEntity;
import com.jobsscan.repository.LocationRepository;
import com.jobsscan.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public LocationEntity saveIfNotExist(LocationEntity locationEntity) {
        Optional<LocationEntity> location = Optional.ofNullable(locationRepository
                .getLocation(locationEntity.getCountry(),
                        locationEntity.getCity(),
                        locationEntity.getRemote()));

        if (location.isEmpty()) {
            return locationRepository.save(locationEntity);
        }
        return locationEntity;
    }
}
