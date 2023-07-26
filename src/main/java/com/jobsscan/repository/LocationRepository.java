package com.jobsscan.repository;

import com.jobsscan.domain.LocationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

//    @Query(value = "INSERT INTO locations (country, city, remote) VALUES(:country, :city, :remote) " +
//            "ON CONFLICT (city, country) DO NOTHING", nativeQuery = true)
//    void saveIfNotExist(@Param("country") String country, @Param("city") String city, @Param("remote") String remote);

    @Query(value = "SELECT * From locations l where l.country=:country AND l.city=:city AND l.remote=:remote"
            , nativeQuery = true)
    LocationEntity getLocation(@Param("country") String country, @Param("city") String city, @Param("remote") String remote);
}
