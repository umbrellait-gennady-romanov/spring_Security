package com.umbrellait.spring_Security.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role() {}
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
