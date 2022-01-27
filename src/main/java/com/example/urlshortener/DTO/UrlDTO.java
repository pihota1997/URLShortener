package com.example.urlshortener.DTO;

import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
public class UrlDTO {

    private long id;
    private String originalUrl;
    private String shortUrl;
    private long clicksNumber;
    private LocalDateTime timeLife;
    @Min(value = 0)
    private int lifeDays;

}
