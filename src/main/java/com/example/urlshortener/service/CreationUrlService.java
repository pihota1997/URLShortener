package com.example.urlshortener.service;

import com.example.urlshortener.Commons.Constants;
import com.example.urlshortener.DTO.UrlDTO;
import com.example.urlshortener.entity.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CreationUrlService {

    private final UrlRepository urlRepository;
    private final ShortUrlGenerator shortUrlGenerator;

    @Autowired
    public CreationUrlService(UrlRepository urlRepository, ShortUrlGenerator shortUrlGenerator) {
        this.urlRepository = urlRepository;
        this.shortUrlGenerator = shortUrlGenerator;
    }

    public Url createShortUrl(UrlDTO urlDTO) {
        Optional<Url> url = urlRepository.findByOriginalUrl(urlDTO.getOriginalUrl());
        if (url.isEmpty()) {
            String shortUrl = generateShortUrl();
            urlDTO.setShortUrl(shortUrl);
            if (urlDTO.getLifeDays() == 0)
                urlDTO.setTimeLife(LocalDateTime.now().plusDays(Constants.constantDayTimeLife));
            else
                urlDTO.setTimeLife(LocalDateTime.now().plusDays(urlDTO.getLifeDays()));
            return createUrlInDb(urlDTO);
        }

        return url.get();
    }

    private String generateShortUrl() {

        String shortUrl = Constants.serviceUrl + shortUrlGenerator.generateShortUrl();
        Optional<Url> shortUrlInDB = urlRepository.findByShortUrl(shortUrl);

        if (shortUrlInDB.isEmpty())
            return shortUrl;

        return generateShortUrl();
    }

    private Url createUrlInDb(UrlDTO urlDTO) {

        Url url = new Url();
        url.setOriginalUrl(urlDTO.getOriginalUrl());
        url.setShortUrl(urlDTO.getShortUrl());
        url.setTimeLife(urlDTO.getTimeLife());

        return urlRepository.save(url);
    }
}
