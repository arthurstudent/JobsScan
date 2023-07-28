package com.jobsscan.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NamedEntityGraph(
        name = "labels_locations-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("labels"),
                @NamedAttributeNode("locations")
        }
)
@Entity
@Table(name = "vacancies")
@Getter
@NoArgsConstructor
public class VacancyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String publicId;

    String jobTitle;

    String companyName;

    String companyUrl;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "vacancy_location",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    Set<LocationEntity> locations = new HashSet<>();

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "vacancy_label",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    Set<LabelEntity> labels = new HashSet<>();

    String logoLink;

    long postedDate;

    @Column(columnDefinition = "TEXT", length = 2048)
    String description;

    public void addLocation(LocationEntity locationEntity) {
        locationEntity.getJobs().add(this);
        locations.add(locationEntity);
    }

    public void addLabel(LabelEntity labelEntity) {
        labelEntity.getJobs().add(this);
        labels.add(labelEntity);
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public void setLabels(Set<LabelEntity> labels) {
        this.labels = labels;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public void setPostedDate(long postedDate) {
        this.postedDate = postedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocations(Set<LocationEntity> locations) {
        this.locations = locations;
    }
}
