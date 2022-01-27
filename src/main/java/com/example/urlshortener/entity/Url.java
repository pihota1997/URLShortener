package com.example.urlshortener.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Table("url")
public class Url {

    @Id
    private long id;
    @Column("original_url")
    @NotEmpty
    @Size(min = 2)
    private String originalUrl;
    @Column("short_url")
    @NotEmpty
    private String shortUrl;
    @Column("clicks_number")
    @Min(value = 0)
    private long clicksNumber;
    @Column("time_life")
    private LocalDateTime timeLife;

}
