package com.jobsscan.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class VacancyDto {

    String jobTitle;

    String companyName;

    String companyUrl;

    LocationDto location;

    Set<String> laborFunction;

    String logoLink;

    long postedDate;

    String description;

    public VacancyDto(Builder builder) {
        this.jobTitle = builder.jobTitle;
        this.companyName = builder.companyName;
        this.companyUrl = builder.companyUrl;
        this.location = builder.location;
        this.laborFunction = builder.laborFunction;
        this.logoLink = builder.logoLink;
        this.postedDate = builder.postedDate;
        this.description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        String jobTitle;

        String companyName;

        String companyUrl;

        LocationDto location;

        Set<String> laborFunction;

        String logoLink;

        long postedDate;

        String description;

        private Builder() {
        }


        public Builder jobTitle(String val) {
            jobTitle = val;
            return this;
        }

        public Builder companyName(String val) {
            companyName = val;
            return this;
        }

        public Builder companyUrl(String val) {
            companyUrl = val;
            return this;
        }

        public Builder locationDto(LocationDto val) {
            location = val;
            return this;
        }

        public Builder laborFunction(Set<String> val) {
            laborFunction = val;
            return this;
        }

        public Builder logoLink(String val) {
            logoLink = val;
            return this;
        }

        public Builder postedDate(long val) {
            postedDate = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public VacancyDto build() {
            return new VacancyDto(this);
        }
    }
}
