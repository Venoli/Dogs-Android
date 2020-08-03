package com.example.dogs.controller;

import com.example.dogs.R;
import com.example.dogs.model.Breed;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BreedController {
    public static List<Breed> allBreeds = new ArrayList<>();

    public static void initAllBreeds() {

        //initialize all breeds
        allBreeds.clear();
        allBreeds.add(new Breed("Papillon", "n02086910"));
        allBreeds.add(new Breed("Basset", "n02088238"));
        allBreeds.add(new Breed("Beagle", "n02088364"));
        allBreeds.add(new Breed("Borzoi", "n02090622"));
        allBreeds.add(new Breed("Whippet", "n02091134"));
        allBreeds.add(new Breed("Cairn", "n02096177"));
        allBreeds.add(new Breed("Lhasa", "n02098413"));
        allBreeds.add(new Breed("Vizsla", "n02100583"));
        allBreeds.add(new Breed("Clumber", "n02101556"));
        allBreeds.add(new Breed("Kuvasz", "n02104029"));
        allBreeds.add(new Breed("Briard", "n02105251"));
        allBreeds.add(new Breed("Kelpie", "n02105412"));
        allBreeds.add(new Breed("collie", "n02106030"));
        allBreeds.add(new Breed("Doberman", "n02107142"));
        allBreeds.add(new Breed("Dingo", "n02115641"));

        //get all images relevant to each breed
        final Field[] fields = R.drawable.class.getDeclaredFields();
        final R.drawable drawableResources = new R.drawable();
        //code for read drawables using prefix: https://android.developreference.com/article/17537328/Get+all+drawables+base+on+file+prefix+and+store+it+on+Array+of+Int
        for (Breed b : allBreeds) {

            List<Integer> imageNameList = new ArrayList<>();
            int resId;
            for (int i = 0; i < fields.length; i++) {
                try {
                    if (fields[i].getName().contains(b.getPrefix())) {
                        resId = fields[i].getInt(drawableResources);
                        imageNameList.add(resId);
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                    continue;
                }
            }
            b.setDogImageNames(imageNameList);
        }

    }

    public static List<String> getAllBreedNames() {
        //to get all breed names
        List<String> breedNameList = new ArrayList<>();
        for (Breed breed : allBreeds) {
            breedNameList.add(breed.getBreedName());
        }

        return breedNameList;
    }

    public static Breed getBreedByName(String name) {
        //to get breed data by it's name
        Breed breed = null;
        for (Breed b : allBreeds) {
            if (b.getBreedName().toLowerCase().equals(name.toLowerCase())) {
                breed = b;
                break;
            }

        }
        return breed;
    }
}
