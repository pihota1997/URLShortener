package com.example.urlshortener.repository;

import com.example.urlshortener.entity.Url;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {

    @Query("select * from url where original_url = :originalUrl")
    Optional<Url> findByOriginalUrl(@Param("originalUrl") String originalUrl);

    @Query("select * from url where short_url = :shortUrl")
    Optional<Url> findByShortUrl(@Param("shortUrl") String shortUrl);

    @Query("select * from url where time_life < :currentDate")
    Iterable<Url> findAllByTimeLife(@Param("currentDate") LocalDateTime localDateTime);

}
