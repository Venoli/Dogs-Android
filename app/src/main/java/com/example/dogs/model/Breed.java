package com.example.dogs.model;

import java.util.ArrayList;
import java.util.List;

//model class for breeds
public class Breed {
    private String breedName;
    private String prefix;
    private List<Integer> dogImageNames = new ArrayList<>();

    public Breed(String breedName, String prefix) {
        this.breedName = breedName;
        this.prefix = prefix;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<Integer> getDogImageNames() {
        return dogImageNames;
    }

    public void setDogImageNames(List<Integer> dogImageNames) {
        this.dogImageNames = dogImageNames;
    }
}
