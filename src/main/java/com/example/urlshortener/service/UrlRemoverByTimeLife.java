package com.example.urlshortener.service;

import com.example.urlshortener.Commons.Constants;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UrlRemoverByTimeLife {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlRemoverByTimeLife(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Scheduled(fixedRate = Constants.timeLifeCheckFrequency)
    public void deleteWhenTimeLifeExpires(){
        urlRepository.deleteAll(urlRepository.findAllByTimeLife(LocalDateTime.now()));
    }

}
