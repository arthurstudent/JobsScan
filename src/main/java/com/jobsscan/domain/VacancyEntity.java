package com.jobsscan.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
@Setter
@NoArgsConstructor
public class VacancyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String vacancyPublicId;

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
}
