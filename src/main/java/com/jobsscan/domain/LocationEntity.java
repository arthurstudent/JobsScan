package com.jobsscan.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String country;

    private String city;

    private String remote;

    @ManyToMany(mappedBy = "locations", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<VacancyEntity> jobs = new HashSet<>();

    public void addVacancy(VacancyEntity job) {
        job.getLocations().add(this);
        jobs.add(job);
    }
}
