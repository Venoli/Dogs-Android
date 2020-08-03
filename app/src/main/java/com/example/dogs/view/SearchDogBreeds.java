package com.example.dogs.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dogs.R;
import com.example.dogs.controller.BreedController;
import com.example.dogs.model.Breed;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.dogs.controller.BreedController.allBreeds;

public class SearchDogBreeds extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ViewAnimator viewAnimator;
    CardView submit;
    boolean showSlider = false;
    int numberOfImagesDisplayed = 1;
    Breed breed;

    GridView gridView;
    SearchListViewAdapter adapter;
    SearchView editSearch;

    //for timer
    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler(); //handler to be able to run in  TimerTask


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dog_breeds);
        //setup tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            mTitle.setText(R.string.search_dog_breeds);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button
            getSupportActionBar().setDisplayShowTitleEnabled(false);//remove title
        }

        BreedController.initAllBreeds();
        submit = (CardView) findViewById(R.id.submit);
        gridView = (GridView) findViewById(R.id.listview);
        adapter = new SearchListViewAdapter(this, allBreeds);
        gridView.setAdapter(adapter);

        //get clicked breed to search view text
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editSearch.setQuery(((TextView) view.findViewById(R.id.name)).getText().toString(), true);
            }
        });
        editSearch = (SearchView) findViewById(R.id.search);
        //searchView code from: https://abhiandroid.com/ui/searchview
        editSearch.setOnQueryTextListener(this);
        viewAnimator = (ViewAnimator) findViewById(R.id.simpleViewAnimator);

        // in and out animations
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        //  view animator code from:      https://abhiandroid.com/ui/viewanimator
        viewAnimator.setInAnimation(in);
        viewAnimator.setOutAnimation(out);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    public void submit(View view) {
        //get relevant breed data (user selected)
        breed = BreedController.getBreedByName(editSearch.getQuery().toString());
        if (breed != null) {
            numberOfImagesDisplayed = 1;
            showSlider = true;
            startTimer();
            editSearch.clearFocus();
            viewAnimator.removeAllViews();
            editSearch.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.INVISIBLE);
            gridView.setVisibility(View.INVISIBLE);
//            viewAnimator.setVisibility(View.VISIBLE);
            viewAnimator.setAnimateFirstView(true); // set true value for setAnimateFirstView
            addImagesToAnimator(breed);

        }
    }

    public void addImagesToAnimator(Breed breed) {
        //add relevant images to the slider

        List<Integer> images = breed.getDogImageNames();
        Collections.shuffle(images);

        for (int i = 0; i < images.size(); i++) {

            CardView cardView = new CardView(getApplicationContext());
            cardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            cardView.setRadius(70);
            cardView.setContentPadding(15, 15, 15, 15);


            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(images.get(i));


            cardView.addView(imageView);
            viewAnimator.addView(cardView);


        }
    }

    public void startTimer() {
//     timer:   https://examples.javacodegeeks.com/android/core/activity/android-timertask-example/
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 5000, 5000); //
    }

    public void stoptimertask(View v) {
        //stop the timer
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        if (numberOfImagesDisplayed > 19) {
                            addImagesToAnimator(breed);
                            numberOfImagesDisplayed = 0;
                        }
                        viewAnimator.showNext();
                        numberOfImagesDisplayed++;

                    }
                });
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(showSlider)
//        startTimer();
    }

    public void stop(View view) {
        stoptimertask(view);
        if (showSlider) {
//            viewAnimator.setVisibility(View.INVISIBLE);
            editSearch.findFocus();
            editSearch.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.VISIBLE);
            showSlider = false;

        }

    }

    //back button press
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(SearchDogBreeds.this,MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}