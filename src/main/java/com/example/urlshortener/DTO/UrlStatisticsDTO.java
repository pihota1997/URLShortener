package com.example.urlshortener.DTO;

import lombok.Data;

@Data
public class UrlStatisticsDTO {

    private String shortUrl;
    private long clicksNumber;
    private long uniqueClicksNumber;

}
