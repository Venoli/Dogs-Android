package com.example.dogs.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class IdentifyTheDog extends AppCompatActivity {
    //three image buttons
    ImageButton dogButton1;
    ImageButton dogButton2;
    ImageButton dogButton3;
    //cards of that images
    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    int answerDogPosition;//position of the correct answer card(image)

    // card and other elements for display correct or wrong
    private CardView correctOrWrong;
    private ImageView correctOrWrongImage;
    private TextView correctOrWrongText;

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
        setContentView(R.layout.activity_identify_the_dog);
        //setup tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            mTitle.setText(R.string.identify_the_dog);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button
            getSupportActionBar().setDisplayShowTitleEnabled(false);//remove title
        }

        BreedController.initAllBreeds();

        DogController dogController = new DogController();
        List<Dog> threeDogs = dogController.getRandomDogs(allBreeds, 3); //get random three dogs
        dogButton1 = (ImageButton) findViewById(R.id.dog_button_1);
        dogButton2 = (ImageButton) findViewById(R.id.dog_button_2);
        dogButton3 = (ImageButton) findViewById(R.id.dog_button_3);
        cardView1 = (CardView) findViewById(R.id.card_view1);
        cardView2 = (CardView) findViewById(R.id.card_view2);
        cardView3 = (CardView) findViewById(R.id.card_view3);
        timerCardView = (CardView) findViewById(R.id.timer_card_view);
        timerText = (TextView) findViewById(R.id.timer_text);
        TextView breedName = (TextView) findViewById(R.id.breed_name);
        correctOrWrong = (CardView) findViewById(R.id.correctOrWrong);
        correctOrWrongImage = (ImageView) findViewById(R.id.correctOrWrongImage);
        correctOrWrongText = (TextView) findViewById(R.id.correctOrWrongText);

        //set random images to image  button
        dogButton1.setImageDrawable(getResources().getDrawable(threeDogs.get(0).getDogImageName()));
        dogButton2.setImageDrawable(getResources().getDrawable(threeDogs.get(1).getDogImageName()));
        dogButton3.setImageDrawable(getResources().getDrawable(threeDogs.get(2).getDogImageName()));

        answerDogPosition = getRandomNumberToSetAnswer(); //randomly select an answer breed
        breedName.setText("Which one is " + threeDogs.get(answerDogPosition).getBreedName() + "?"); //ask the question (set text)

        //click listeners for buttons
        dogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(0, cardView1);
            }
        });
        dogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1, cardView2);
            }
        });
        dogButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2, cardView3);
            }
        });

        if (isTimerOn) {
            startTimer();
        } else {
            timerCardView.setVisibility(View.INVISIBLE);
        }
    }

    void checkAnswer(int buttonNumber, CardView cardView) {
        //after user clicks the button check the answer and show outputs accordingly
        stoptimertask();
        if (buttonNumber == answerDogPosition) {
            correctOrWrong.setCardBackgroundColor(Color.parseColor("#388e3c"));
            correctOrWrongImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
            correctOrWrongText.setText(R.string.correct);
            cardView.setCardBackgroundColor(getResources().getColor(R.color.answerCorrectGreen));
        } else {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.answerWrongRed));
            if (answerDogPosition == 0) {
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.answerCorrectGreen));
            } else if (answerDogPosition == 1) {
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.answerCorrectGreen));

            } else {
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.answerCorrectGreen));

            }


        }
        correctOrWrong.setVisibility(View.VISIBLE);
        dogButton1.setClickable(false);
        dogButton2.setClickable(false);
        dogButton3.setClickable(false);

    }

    void timerOut() {
        //when time's up show correct answer
        if (answerDogPosition == 0) {
            cardView1.setCardBackgroundColor(getResources().getColor(R.color.answerCorrectGreen));
        } else if (answerDogPosition == 1) {
            cardView2.setCardBackgroundColor(getResources().getColor(R.color.answerCorrectGreen));

        } else {
            cardView3.setCardBackgroundColor(getResources().getColor(R.color.answerCorrectGreen));

        }

        //when time's up show time's up indicator
        correctOrWrongImage.setImageDrawable(getResources().getDrawable(R.drawable.timer_active));
        correctOrWrongText.setText(R.string.times_up);
        correctOrWrong.setVisibility(View.VISIBLE);

        //when time's up disable three buttons
        dogButton1.setClickable(false);
        dogButton2.setClickable(false);
        dogButton3.setClickable(false);
    }

    private static int getRandomNumberToSetAnswer() {
        //get random number among 0 & 2
        double x = (Math.random() * ((2) + 1));
        return (int) x;
    }

    public void next(View view) {
        Intent intent = new Intent(this, IdentifyTheDog.class);
        startActivity(intent);
        finish();
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
                        timerText.setText(Integer.toString(--timerValue)); // set timer value to the text
                        if (timerValue < 1) {
                            stoptimertask();
                            timerOut();
                        }
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
    //back button press
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(IdentifyTheDog.this,MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}
