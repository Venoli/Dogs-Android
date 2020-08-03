package com.example.dogs.model;

//model class for dogs
public class Dog {
    private String breedName;
    private Integer dogImageName;

    public Dog(String breedName, Integer dogImageName) {
        this.breedName = breedName;
        this.dogImageName = dogImageName;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public Integer getDogImageName() {
        return dogImageName;
    }

    public void setDogImageName(Integer dogImageName) {
        this.dogImageName = dogImageName;
    }
}
