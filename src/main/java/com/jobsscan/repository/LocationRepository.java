package com.jobsscan.repository;

import com.jobsscan.domain.LocationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

    LocationEntity getLocationByCityAndRemote(String city, String remote);

}
