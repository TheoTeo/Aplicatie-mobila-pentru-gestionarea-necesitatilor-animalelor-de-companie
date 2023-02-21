package com.ase.dam.licenta01;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StatisticaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistica);
        getSupportActionBar().setTitle(R.string.statistica);

        PieChart pieChart=findViewById(R.id.pieChart);
        pieChart.setBackgroundColor(Color.rgb	(255,228,225));
        ArrayList<PieEntry> animale=new ArrayList<>();

        Fediaf2021 f1=new Fediaf2021(113588248,"pisici");
        Fediaf2021 f2=new Fediaf2021(92947732,"caini");
        Fediaf2021 f3=new Fediaf2021(48719769,"pasari");
        Fediaf2021 f4=new Fediaf2021(29347757,"rozatoare");
        Fediaf2021 f5=new Fediaf2021(16403937,"pesti");



        animale.add(new PieEntry(f1.getNrAnimaleEuropa(),f1.getTipAnimal()));
        animale.add(new PieEntry(f2.getNrAnimaleEuropa(),f2.getTipAnimal()));
        animale.add(new PieEntry(f3.getNrAnimaleEuropa(),f3.getTipAnimal()));
        animale.add(new PieEntry(f4.getNrAnimaleEuropa(),f4.getTipAnimal()));
        animale.add(new PieEntry(f5.getNrAnimaleEuropa(),f5.getTipAnimal()));
        PieDataSet pieDataSet=new PieDataSet(animale,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Animalele inregistrate din Europa");
        pieChart.animate();
    }
}