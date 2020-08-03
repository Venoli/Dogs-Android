package com.example.dogs.controller;

import com.example.dogs.model.Breed;
import com.example.dogs.model.Dog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DogController {

    public List<Dog> getRandomDogs(List<Breed> allBreeds, int numberOfDogs) {
        //to get number og random dogs
        List<Dog> dogs = new ArrayList<>();
        Collections.shuffle(allBreeds);
        for (int i = 0; i < numberOfDogs; i++) {
            Collections.shuffle(allBreeds.get(i).getDogImageNames());
            dogs.add(new Dog(allBreeds.get(i).getBreedName(), allBreeds.get(i).getDogImageNames().get(1)));
        }

        return dogs;
    }


}
