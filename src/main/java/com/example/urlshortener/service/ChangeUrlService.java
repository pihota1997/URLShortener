package com.example.urlshortener.service;

import com.example.urlshortener.entity.UniqueRequest;
import com.example.urlshortener.entity.Url;
import com.example.urlshortener.repository.UniqueRequestRepository;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service
public class ChangeUrlService {

    private final UrlRepository urlRepository;
    private final ReceivingUrlService receivingUrlService;
    private final UniqueRequestRepository uniqueRequestRepository;

    @Autowired
    public ChangeUrlService(UrlRepository urlRepository, ReceivingUrlService receivingUrlService,
                            UniqueRequestRepository uniqueRequestRepository) {
        this.urlRepository = urlRepository;
        this.receivingUrlService = receivingUrlService;
        this.uniqueRequestRepository = uniqueRequestRepository;
    }


    public void deleteUrl(String shortUrl) {
        Url url = receivingUrlService.getUrlByShortUrl(shortUrl);

        urlRepository.delete(url);
    }

    public Url incrementClicksNumber(Url url, HttpServletRequest request) {
        url.setClicksNumber(url.getClicksNumber() + 1);
        addUniqueIP(url, request);
        return urlRepository.save(url);
    }

    private void addUniqueIP(Url url, HttpServletRequest request) {
        Optional<UniqueRequest> optionalUniqueRequest = uniqueRequestRepository.findByUrlIdAndUserIp(request.getRemoteAddr(), url.getId());
        if (optionalUniqueRequest.isEmpty()) {
            UniqueRequest uniqueRequest = new UniqueRequest();
            uniqueRequest.setUrlId(url.getId());
            uniqueRequest.setUserIp(request.getRemoteAddr());
            uniqueRequestRepository.save(uniqueRequest);
        }
    }
}
