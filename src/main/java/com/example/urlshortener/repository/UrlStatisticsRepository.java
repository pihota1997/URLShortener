package com.example.urlshortener.repository;

import com.example.urlshortener.DTO.UrlStatisticsDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;


public interface UrlStatisticsRepository extends CrudRepository<UrlStatisticsDTO, Long> {

    @Query("select short_url, clicks_number, COUNT(*) AS unique_clicks_number " +
            "FROM url INNER JOIN unique_request ur on url.id = ur.url_id " +
            "GROUP BY url_id, short_url, clicks_number")
    Iterable<UrlStatisticsDTO> readAllByUniqueClicksNumber();
}
