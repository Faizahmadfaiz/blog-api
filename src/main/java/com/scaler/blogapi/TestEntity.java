package com.scaler.blogapi;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long Id;
    String name;
    Boolean completed;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
