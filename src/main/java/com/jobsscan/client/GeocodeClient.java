package com.jobsscan.client;

import com.jobsscan.utils.enums.URIEnum;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Component
@NoArgsConstructor
public class GeocodeClient {

    public CompletableFuture<HttpResponse<String>> geocodeRequest(@NonNull String query, @NonNull String resource,
                                                                  @NonNull String apiKey, @NonNull String language) {

        var httpClient = HttpClient.newHttpClient();

        var encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        var REQUEST_URI = resource + "?" + URIEnum.API_KEY.getName() + "=" +
                apiKey + "&" + URIEnum.QUERY.getName() + "=" + encodedQuery + "&" + URIEnum.LANGUAGE.getName() + "=" + language;

        var geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(REQUEST_URI))
                .timeout(Duration.ofMillis(2000)).build();

        return httpClient.sendAsync(geocodingRequest, HttpResponse.BodyHandlers.ofString());
    }
}
