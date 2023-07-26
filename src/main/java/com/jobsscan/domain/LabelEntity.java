package com.jobsscan.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "labels")
@Getter
@Setter
@NoArgsConstructor
public class LabelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String laborFunction;

    @ManyToMany(mappedBy = "labels", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<VacancyEntity> jobs = new HashSet<>();

    public void addVacancy(VacancyEntity job) {
        job.getLabels().add(this);
        jobs.add(job);
    }
}
