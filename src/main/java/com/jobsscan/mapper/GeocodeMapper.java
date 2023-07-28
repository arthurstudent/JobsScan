package com.jobsscan.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobsscan.client.GeocodeClient;
import com.jobsscan.config.GeocoderProperties;
import com.jobsscan.exception.GeocodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.jobsscan.utils.enums.GeocodeEnum.ADDRESS;
import static com.jobsscan.utils.enums.GeocodeEnum.COUNTRY_CODE;
import static com.jobsscan.utils.enums.GeocodeEnum.CITY;
import static com.jobsscan.utils.enums.GeocodeEnum.ITEMS;
import static com.jobsscan.utils.enums.GeocodeEnum.NA;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeocodeMapper {

    private final GeocoderProperties geocoderProperties;

    private final GeocodeClient geocodeClient;

    public Map<String, String> getGeocodingResource(String location) {

        if (StringUtils.isBlank(location)) {
            return new HashMap<>();
        }

        var mapper = new ObjectMapper();
        Map<String, String> geoCodeMap = new HashMap<>();
        var futureResponse = geocodeClient.geocodeRequest(location, geocoderProperties.getResource(),
                geocoderProperties.getApiKey(), "en-US");

        var response = futureResponse.join().body();
        JsonNode responseJsonNode;

        try {
            responseJsonNode = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            log.warn("GeocodeMapper: Unable to get data from JsonNode");
            throw new GeocodeException(e.getMessage());
        }

        var item = responseJsonNode.get(ITEMS.getName());

        var address = Optional.ofNullable(item.get(0).get(ADDRESS.getName()));
        if (address.isPresent()) {
            String countryCode = fromNodeToString(address.get(), COUNTRY_CODE.getName());
            String city = fromNodeToString(address.get(), CITY.getName());

            geoCodeMap.put(COUNTRY_CODE.getName(), countryCode);
            geoCodeMap.put(CITY.getName(), city);
        }
        return geoCodeMap;
    }

    private String fromNodeToString(JsonNode jsonNode, String name) {
        var stringBuilder = new StringBuilder();

        var node = Optional.ofNullable(jsonNode.get(name));
        node.ifPresent(el -> stringBuilder.append(el.asText()));

        return StringUtils.defaultIfBlank(stringBuilder.toString(), NA.getName());
    }
}
