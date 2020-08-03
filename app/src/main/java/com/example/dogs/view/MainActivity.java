package com.example.dogs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.dogs.R;

public class MainActivity extends AppCompatActivity {
    //switch button styling : https://www.zoftino.com/android-switch-button-and-custom-switch-examples
    Switch switchTimer;
    public static boolean isTimerOn = false; //timer on or off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchTimer = (Switch) findViewById(R.id.switch_timer);
        final ImageView timerImage = (ImageView) findViewById(R.id.timer_image);
        //switch button check change listener
        switchTimer.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            //switch on
                            timerImage.setImageDrawable(getResources().getDrawable(R.drawable.timer_on)); //set timer on icon
                            isTimerOn = true; //timer on

                        } else {
                            //switch off
                            timerImage.setImageDrawable(getResources().getDrawable(R.drawable.timer_off)); //set timer off icon
                            isTimerOn = false;//timer off


                        }
                    }
                });

    }

    public void identifyTheBreed(View view) {
        Intent intent = new Intent(this, IdentifyTheBreed.class);
        startActivity(intent);
    }

    public void identifyTheDog(View view) {
        Intent intent = new Intent(this, IdentifyTheDog.class);
        startActivity(intent);
    }

    public void searchDogBreeds(View view) {
        Intent intent = new Intent(this, SearchDogBreeds.class);
        startActivity(intent);
    }
}
