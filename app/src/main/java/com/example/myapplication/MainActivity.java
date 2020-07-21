package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button home, office, market, stt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = findViewById(R.id.home);
        office = findViewById(R.id.office);
        market = findViewById(R.id.market);
        stt = findViewById(R.id.stt);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Moving on to the Home Interface!", Toast.LENGTH_SHORT).show();
            }
        });

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OfficeActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Moving on to the Office Interface!", Toast.LENGTH_SHORT).show();
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MarketActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Moving on to the Market Interface!", Toast.LENGTH_SHORT).show();
            }
        });
        stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpeechtoText.class);
                startActivity(intent);
            }
        });
    }
}
