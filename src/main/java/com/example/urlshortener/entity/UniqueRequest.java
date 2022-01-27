package com.example.urlshortener.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;

@Data
@Table("unique_request")
public class UniqueRequest {

    @Id
    private long id;
    @Column("user_ip")
    @NotEmpty
    private String userIp;
    @Column("url_id")
    private long urlId;

}
