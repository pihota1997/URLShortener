package com.example.urlshortener.repository;

import com.example.urlshortener.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select * from users where login = :login")
    Optional<User> findByLogin(@Param("login") String login);

}
