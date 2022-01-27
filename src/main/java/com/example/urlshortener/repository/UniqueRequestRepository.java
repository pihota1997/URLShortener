package com.example.urlshortener.repository;

import com.example.urlshortener.entity.UniqueRequest;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UniqueRequestRepository extends CrudRepository<UniqueRequest, Long> {

    @Query("select * from unique_request where user_ip = :userIp AND url_id = :urlId")
    Optional<UniqueRequest> findByUrlIdAndUserIp (@Param("userIp") String userIp,
                                                  @Param("urlId") long urlId);

}
