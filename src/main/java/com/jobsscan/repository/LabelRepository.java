package com.jobsscan.repository;

import com.jobsscan.domain.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LabelRepository extends JpaRepository<LabelEntity, Long> {

    @Query(value = "SELECT * From labels l where l.name=:name"
            , nativeQuery = true)
    LabelEntity getLabel(@Param("name") String name);
}
