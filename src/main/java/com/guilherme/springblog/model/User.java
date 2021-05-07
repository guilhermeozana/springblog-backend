package com.guilherme.springblog.model;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
}
