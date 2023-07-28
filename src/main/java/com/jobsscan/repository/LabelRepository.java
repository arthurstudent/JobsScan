package com.jobsscan.repository;

import com.jobsscan.domain.LabelEntity;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepository extends CrudRepository<LabelEntity, Long> {

    LabelEntity findByLaborFunction(String name);
}
