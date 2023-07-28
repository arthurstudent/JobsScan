package com.jobsscan.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locations")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    String publicId;

    private String country;

    @Column(unique = true)
    private String city;

    private String remote;

    @ManyToMany(mappedBy = "locations")
    private Set<VacancyEntity> jobs = new HashSet<>();

    public void addVacancy(VacancyEntity job) {
        job.getLocations().add(this);
        jobs.add(job);
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public void setJobs(Set<VacancyEntity> jobs) {
        this.jobs = jobs;
    }
}
