package com.example.stressbuster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class FirstTimeInitialization extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_initialization);

        setStatusBarGradientOk.setStatusBarGradient(this);

        final TextView onlyTextHere = findViewById(R.id.loginHeadingText);
        final ImageView onlyImageView = findViewById(R.id.lolImage);
        final MaterialButton nextBtn = findViewById(R.id.introActivityBtn);
        MaterialButton skipBtn = findViewById(R.id.skipActivityBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0) {
                    onlyTextHere.setText("No Permissions needed. \nYayy!");
                    onlyImageView.setImageDrawable(null);
                    count++;
                } else if(count == 1) {
                    onlyImageView.setImageDrawable(null);
                    onlyTextHere.setText("Explore the app. \nFind out stuff. \nGet used to it :)");
                    count++;
                } else if(count == 2) {
                    startActivity(new Intent(FirstTimeInitialization.this, UserDashboard.class));
                }
                if(count == 2) {
                    nextBtn.setText("Done");
                }

            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstTimeInitialization.this, UserDashboard.class));
            }
        });



    }
}
