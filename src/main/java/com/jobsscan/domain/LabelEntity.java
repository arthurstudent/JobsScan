package com.jobsscan.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "labels")
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class LabelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    String publicId;

    @Column(name = "name", unique = true)
    private String laborFunction;

    @ManyToMany(mappedBy = "labels")
    private Set<VacancyEntity> jobs = new HashSet<>();

    public void addVacancy(VacancyEntity job) {
        job.getLabels().add(this);
        jobs.add(job);
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public void setLaborFunction(String laborFunction) {
        this.laborFunction = laborFunction;
    }

    public void setJobs(Set<VacancyEntity> jobs) {
        this.jobs = jobs;
    }
}
