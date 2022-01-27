package com.example.urlshortener.service;

import com.example.urlshortener.DTO.UrlStatisticsDTO;
import com.example.urlshortener.entity.Url;
import com.example.urlshortener.exception.UrlExistException;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.repository.UrlStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivingUrlService {

    private final UrlRepository urlRepository;
    private final UrlStatisticsRepository urlStatisticsRepository;

    @Autowired
    public ReceivingUrlService(UrlRepository urlRepository, UrlStatisticsRepository urlStatisticsRepository) {
        this.urlRepository = urlRepository;
        this.urlStatisticsRepository = urlStatisticsRepository;
    }

    public Url getUrlByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new UrlExistException("URL " + shortUrl + " not found"));
    }

    public Iterable<UrlStatisticsDTO> getUrlStatistics(){
        return urlStatisticsRepository.readAllByUniqueClicksNumber();
    }


}
