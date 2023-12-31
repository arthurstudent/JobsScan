package com.jobsscan.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private String publicId;

    private String country;

    private String city;

    private String remote;
}
