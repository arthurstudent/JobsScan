package com.jobsscan.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobsscan.config.GeocoderProperties;
import com.jobsscan.exception.GeocodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.jobsscan.utils.GeocodeEnum.ADDRESS;
import static com.jobsscan.utils.GeocodeEnum.COUNTRY_CODE;
import static com.jobsscan.utils.GeocodeEnum.CITY;
import static com.jobsscan.utils.GeocodeEnum.ITEMS;
import static com.jobsscan.utils.GeocodeEnum.NA;

@Slf4j
@Component
public class GeocodeMapper {

    private final GeocoderProperties geocoderProperties;

    public GeocodeMapper(GeocoderProperties geocoderProperties) {
        this.geocoderProperties = geocoderProperties;
    }

    public Map<String, String> getGeocodingResource(String location) {

        if (StringUtils.isBlank(location)) {
            return new HashMap<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> geoCodeMap = new HashMap<>();
        CompletableFuture<HttpResponse<String>> futureResponse = geocodeAsync(location);

        String response = futureResponse.join().body();
        JsonNode responseJsonNode;

        try {
            responseJsonNode = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            log.warn("GeocodeMapper: Unable to get data from JsonNode");
            throw new GeocodeException(e.getMessage());
        }

        JsonNode item = responseJsonNode.get(ITEMS.getName());

        Optional<JsonNode> address = Optional.ofNullable(item.get(0).get(ADDRESS.getName()));
        if (address.isPresent()) {
            String countryCode = fromNodeToString(address.get(), COUNTRY_CODE.getName());
            String city = fromNodeToString(address.get(), CITY.getName());

            geoCodeMap.put(COUNTRY_CODE.getName(), countryCode);
            geoCodeMap.put(CITY.getName(), city);
        }
        return geoCodeMap;
    }

    private CompletableFuture<HttpResponse<String>> geocodeAsync(String query) {

        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String REQUEST_URI = geocoderProperties.getResource() + "?apiKey=" +
                geocoderProperties.getApiKey() + "&q=" + encodedQuery + "&lang=en-US";

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(REQUEST_URI))
                .timeout(Duration.ofMillis(2000)).build();

        return httpClient.sendAsync(geocodingRequest, HttpResponse.BodyHandlers.ofString());
    }

    private String fromNodeToString(JsonNode jsonNode, String name) {
        StringBuilder stringBuilder = new StringBuilder();

        Optional<JsonNode> node = Optional.ofNullable(jsonNode.get(name));
        node.ifPresent(el -> stringBuilder.append(el.asText()));

        return StringUtils.defaultIfBlank(stringBuilder.toString(), NA.getName());
    }

}
