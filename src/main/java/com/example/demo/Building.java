package com.example.demo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "building", cascade = CascadeType.ALL)
    private List<Flat> flat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Flat> getFlat() {
        return flat;
    }

    public void setFlat(List<Flat> flat) {
        this.flat = flat;
    }
}
