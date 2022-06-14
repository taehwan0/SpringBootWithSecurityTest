package com.cos.security.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class User {

    @Id @GeneratedValue
    private long id;

    private String username;
    private String password;
    private String email;
    private String role; // ROLE_USER, ROLE_ADMIN
    @CreationTimestamp
    private Timestamp createdAt;
}
