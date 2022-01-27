package com.example.urlshortener.web;

import com.example.urlshortener.Commons.Constants;
import com.example.urlshortener.DTO.UrlStatisticsDTO;
import com.example.urlshortener.DTO.UrlDTO;
import com.example.urlshortener.entity.Url;
import com.example.urlshortener.service.CreationUrlService;
import com.example.urlshortener.service.ChangeUrlService;
import com.example.urlshortener.service.ReceivingUrlService;
import com.example.urlshortener.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class UrlController {

    private final ChangeUrlService urlService;
    private final CreationUrlService creationUrlService;
    private final ReceivingUrlService receivingUrlService;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public UrlController(ChangeUrlService urlService, CreationUrlService creationUrlService, ReceivingUrlService receivingUrlService, ResponseErrorValidation responseErrorValidation) {
        this.urlService = urlService;
        this.creationUrlService = creationUrlService;
        this.receivingUrlService = receivingUrlService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUrl(@Valid @RequestBody UrlDTO urlDTO,
                                            BindingResult bindingResult){

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors))
            return errors;

        Url url = creationUrlService.createShortUrl(urlDTO);
        return new ResponseEntity<>(url.getShortUrl(), HttpStatus.OK);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirect (@PathVariable("shortUrl") String shortUrl,
                                  HttpServletRequest request) {

        Url url = urlService.incrementClicksNumber(receivingUrlService.getUrlByShortUrl(Constants.serviceUrl + shortUrl),
                request);
        return new RedirectView(url.getOriginalUrl());
    }

    @DeleteMapping ("/delete/{shortUrl}")
    public ResponseEntity<Object> deleteUrl(@PathVariable("shortUrl") String shortUrl) {

        urlService.deleteUrl(Constants.serviceUrl + shortUrl);
        return new ResponseEntity<>("Url was deleted", HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Iterable<UrlStatisticsDTO>> getStatistics() {

        return new ResponseEntity<>(receivingUrlService.getUrlStatistics(), HttpStatus.OK);
    }
}
