package com.ase.dam.licenta01;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {
    public static final String LOGIN_CREDENTIALS = "LoginCredentials";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    private String emailFromPrefferences;
    private String passwordFromPrefferences;
    private int count2022=0;
    private int count2023=0;
    private int count2024=0;

    private void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences(LOGIN_CREDENTIALS, MODE_PRIVATE);
        if (preferences.contains(EMAIL)) {
            emailFromPrefferences = preferences.getString(EMAIL, getString(R.string.empty_string));
            // tietEmail.setText(emailFromPrefferences);

        }
        if (preferences.contains(PASSWORD)) {
            passwordFromPrefferences = preferences.getString(PASSWORD, getString(R.string.empty_string));
            // tietPassword.setText(passwordFromPrefferences);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        loadPreferences();

        Query query = FirebaseFirestore.getInstance()
                .collection("EvenimenteCalendar")
                //.orderby
                .limit(50);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("emailUser").equals(emailFromPrefferences)) {
                                    document.getString("numeEveniment");
                                    document.getDate("dataEveniment");
                                    CalendarVeterinar calendarVeterinar2 = new CalendarVeterinar(document.getDate("dataEveniment"), document.getString("numeEveniment"), emailFromPrefferences);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                                    String dateOnly = dateFormat.format(document.getDate("dataEveniment"));
                                    if (dateOnly.indexOf("2022")>0) {
                                        count2022++;
                                    }
                                    if (dateOnly.contains("2023")) {
                                        count2023++;
                                    }
                                    if (dateOnly.contains("2024")) {
                                        count2024++;
                                    }
                                }
                            }
                        }
                        BarChart barChart=findViewById(R.id.barchart);
                        ArrayList<BarEntry> lista=new ArrayList<>();
                        lista.add(new BarEntry(2022,count2022));
                        lista.add(new BarEntry(2023,count2023));
                        lista.add(new BarEntry(2024,count2024));


                        BarDataSet barDataSet=new BarDataSet(lista,"Evenimentele tale pe urmatorii 3 ani");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(16f);

                        BarData barData=new BarData(barDataSet);
                        //barChart.setVisibleXRange(2022,2024);

                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.getDescription().setText("Evenimentele tale pe urmatorii 3 ani");
                        barChart.animateY(2000);
                    }
                });



    }
}