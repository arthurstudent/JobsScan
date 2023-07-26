package com.jobsscan.repository;

import com.jobsscan.domain.VacancyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<VacancyEntity, Long> {

    VacancyEntity findJobByCompanyUrl(String companyUrl);
}
