package com.ozaytunctan.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public abstract class BaseEntity<ID extends Number> implements Serializable {


    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "idGenerator",strategy = GenerationType.SEQUENCE)
    private ID id;
    private LocalDate createdDate = LocalDate.now();
    private boolean active;
    public BaseEntity() {
        setId(null);
    }

    public BaseEntity(ID id) {
        setId(id);
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
