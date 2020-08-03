package com.example.dogs.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dogs.R;
import com.example.dogs.controller.BreedController;
import com.example.dogs.controller.DogController;
import com.example.dogs.model.Dog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.dogs.view.MainActivity.isTimerOn;
import static com.example.dogs.controller.BreedController.allBreeds;

public class IdentifyTheBreed extends AppCompatActivity {
    private ImageView dogImage;
    private CardView correctOrWrong;
    private ImageView correctOrWrongImage;
    private TextView correctOrWrongText;
    private CardView correctAnswer;
    private TextView correctAnswerText;
    private Button submitNext;
    Dog dog;
    String selectedBreed;
    int submitOrNext = 0;// submit=0

    //for timer
    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler(); //handler to be able to run in  TimerTask
    TextView timerText;
    int timerValue = 10;
    CardView timerCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_breed);
        BreedController.initAllBreeds();
        //setup tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            mTitle.setText(R.string.identify_the_breed);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button
            getSupportActionBar().setDisplayShowTitleEnabled(false);//remove title
        }

        dogImage = (ImageView) findViewById(R.id.dogImage);
        correctOrWrong = (CardView) findViewById(R.id.correctOrWrong);
        correctOrWrongImage = (ImageView) findViewById(R.id.correctOrWrongImage);
        correctOrWrongText = (TextView) findViewById(R.id.correctOrWrongText);
        correctAnswer = (CardView) findViewById(R.id.correctAnswer);
        correctAnswerText = (TextView) findViewById(R.id.correctAnswerText);
        submitNext = (Button) findViewById(R.id.submitNext);
        timerText = (TextView) findViewById(R.id.timer_text);
        timerCardView = (CardView) findViewById(R.id.timer_card_view);

        List<String> breedNameList = BreedController.getAllBreedNames();//get names for dropdown list
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_selected, breedNameList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        DogController dogController = new DogController();
        dog = dogController.getRandomDogs(allBreeds, 1).get(0); //get random dog
        dogImage.setImageDrawable(getResources().getDrawable(dog.getDogImageName()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(IdentifyTheBreed.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                selectedBreed = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (isTimerOn) {
            startTimer();//timer mode
        } else {
            timerCardView.setVisibility(View.INVISIBLE); // without timer mode
        }


    }


    public void clickSubmit(View view) {
        submit();
    }

    public void submit() {
        stoptimertask();
        if (submitOrNext == 0) {
//            click submit
            if (selectedBreed.equals(dog.getBreedName())) {
                correctOrWrong.setCardBackgroundColor(Color.parseColor("#388e3c"));
                correctOrWrongImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                correctOrWrongText.setText(R.string.correct);
                correctAnswer.setVisibility(View.GONE);
            } else {
                correctOrWrong.setCardBackgroundColor(Color.parseColor("#ff3333"));
                correctOrWrongImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_black_24dp));
                correctOrWrongText.setText(R.string.wrong);
                correctAnswerText.setText(dog.getBreedName());
                correctAnswer.setVisibility(View.VISIBLE);
            }
            correctOrWrong.setVisibility(View.VISIBLE);
            submitNext.setText(R.string.next);
            submitOrNext = 1;
        } else {
            //click next
            Intent intent = new Intent(this, IdentifyTheBreed.class);
            startActivity(intent);
            finish();
        }
    }

    public void startTimer() {
//     timer:   https://examples.javacodegeeks.com/android/core/activity/android-timertask-example/
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void stoptimertask() {
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
                        timerText.setText(Integer.toString(--timerValue));
                        if (timerValue < 1) {
                            submit();
                        }
                    }
                });
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        //onResume we start our timer so it can start when the app comes from the background
//        if(showSlider)
//        startTimer();
    }

    //back button press
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(IdentifyTheBreed.this,MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}
