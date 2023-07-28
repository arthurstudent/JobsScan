package com.jobsscan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class VacancyDto implements Serializable {

    @NotBlank
    String publicId;

    @NotNull
    String jobTitle;

    @NotNull
    String companyName;

    @NotNull
    String companyUrl;

    @NotNull
    LocationDto location;

    @NotNull
    Set<LaborFunctionDto> laborFunctions;

    @NotNull
    String logoLink;

    @NotNull
    long postedDate;

    @NotNull
    String description;

    public VacancyDto(Builder builder) {
        this.publicId = builder.publicId;
        this.jobTitle = builder.jobTitle;
        this.companyName = builder.companyName;
        this.companyUrl = builder.companyUrl;
        this.location = builder.location;
        this.laborFunctions = builder.laborFunctions;
        this.logoLink = builder.logoLink;
        this.postedDate = builder.postedDate;
        this.description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        String publicId;

        String jobTitle;

        String companyName;

        String companyUrl;

        LocationDto location;

        Set<LaborFunctionDto> laborFunctions;

        String logoLink;

        long postedDate;

        String description;

        private Builder() {
        }

        public Builder publicId(String val) {
            publicId = val;
            return this;
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

        public Builder laborFunction(Set<LaborFunctionDto> val) {
            laborFunctions = val;
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
