package com.example.demo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int number;
    private double surface;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "flat", cascade = CascadeType.ALL)
    private List<Inhabitant> inhabitant;
    @ManyToOne
    private Building building;

    public Flat() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public List<Inhabitant> getInhabitant() {
        return inhabitant;
    }

    public void setInhabitant(List<Inhabitant> inhabitant) {
        this.inhabitant = inhabitant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
