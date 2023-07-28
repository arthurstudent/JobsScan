package com.jobsscan.client;

import com.jobsscan.exception.ConnectionException;
import com.jobsscan.exception.ParseDataException;
import com.jobsscan.utils.CustomUtils;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class JsoupClient {

    public List<Document> scrapDocsByUrls(List<String> list, @NonNull String uri) {

        return CustomUtils.collectionNullCheck(list) ? new ArrayList<>() :
                list.stream().filter(link -> link.startsWith("/")).limit(10)
                        .map(link -> uri + link)
                        .map(link -> CompletableFuture.supplyAsync(() -> {
                            try {
                                return Jsoup.connect(link).get();
                            } catch (IOException e) {
                                throw new ConnectionException(e.getMessage());
                            }
                        }))
                        .collect(Collectors.collectingAndThen(
                                Collectors.toList(), completableFutureList ->
                                        completableFutureList.stream()
                                                .map(CompletableFuture::join)
                        ))
                        .toList();
    }

    public List<String> extractUrlsFromDoc(String url) {

        try {
            var document = Jsoup.connect(url).get();
            var element = Optional.ofNullable(document
                    .getElementsByClass("infinite-scroll-component sc-beqWaB biNQIL").first());

            return element.map(value -> value
                    .getElementsByClass("sc-beqWaB sc-gueYoa lpllVF MYFxR job-info")
                    .select("h4 > a")
                    .stream()
                    .map(el -> el.attr("href"))
                    .toList()).orElseGet(ArrayList::new);
        } catch (IOException ex) {
            throw new ParseDataException(ex.getMessage());
        }
    }
}
