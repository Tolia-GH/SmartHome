package com.demo.backend.databaseJPA.supporter;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "supporter", schema = "smart_home")
public class SupporterJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "is_free")
    private Boolean isFree;
}

