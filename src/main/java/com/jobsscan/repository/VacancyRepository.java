package com.jobsscan.repository;

import com.jobsscan.domain.VacancyEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends CrudRepository<VacancyEntity, Long>, PagingAndSortingRepository<VacancyEntity, Long> {

    boolean existsByPublicId(String vacancyPublicId);

    @EntityGraph(value = "labels_locations-entity-graph")
    VacancyEntity findByPublicId(String vacancyPublicId);

    @Query("select distinct vac from VacancyEntity vac inner join vac.locations loc where loc.country =:country order by vac.postedDate desc ")
    List<VacancyEntity> getVacanciesByCountry(@Param("country") String country);

    @Query("select distinct vac from VacancyEntity vac inner join vac.locations loc where loc.city =:city order by vac.postedDate desc")
    List<VacancyEntity> getVacanciesByCity(@Param("city") String city);

    void deleteByPublicId(String publicId);
}
