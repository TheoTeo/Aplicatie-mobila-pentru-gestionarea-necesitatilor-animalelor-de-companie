package com.ase.dam.licenta01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToateStatisticileActivity extends AppCompatActivity {
    private Button btnStatisticiEuropene;
    private Button btnStatisticiUtilizator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toate_statisticile);
        btnStatisticiEuropene=findViewById(R.id.btn_StatisticaEuropeana);
        btnStatisticiUtilizator=findViewById(R.id.btn_StatisticaUtilizator);
        btnStatisticiEuropene.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(getApplicationContext(), StatisticaActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );
        btnStatisticiUtilizator.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         Intent intent = new Intent(getApplicationContext(), BarChartActivity.class);
                                                         startActivity(intent);
                                                     }
                                                 }
        );

    }
}