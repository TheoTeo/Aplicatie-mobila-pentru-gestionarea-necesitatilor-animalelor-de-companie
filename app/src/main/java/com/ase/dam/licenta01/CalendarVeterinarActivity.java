package com.ase.dam.licenta01;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.FieldIndex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarVeterinarActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private TextInputEditText tietNumeEveniment;
    private DatePicker dpEveniment;
    private Button btnInregistrareEveniment;
    private ListView listaEvenimente;
    private List<CalendarVeterinar> listaCalendar;
    public static final String LOGIN_CREDENTIALS = "LoginCredentials";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    private String emailFromPrefferences;
    private String passwordFromPrefferences;

    private void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences(LOGIN_CREDENTIALS, MODE_PRIVATE);
        if (preferences.contains(EMAIL)) {
            emailFromPrefferences = preferences.getString(EMAIL, getString(R.string.empty_string));

        }
        if (preferences.contains(PASSWORD)) {
            passwordFromPrefferences = preferences.getString(PASSWORD, getString(R.string.empty_string));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_veterinar);
        loadPreferences();
        tietNumeEveniment=findViewById(R.id.tiet_register_eventNume);
        dpEveniment=findViewById(R.id.dp_register);
        btnInregistrareEveniment=findViewById(R.id.btn_registerEvent);
        listaEvenimente=findViewById(R.id.lsEvenimente);
        listaCalendar=new ArrayList<>();
        btnInregistrareEveniment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numeEveniment=tietNumeEveniment.getText().toString();
                int day = dpEveniment.getDayOfMonth();
                int month = dpEveniment.getMonth() + 1;
                int year = dpEveniment.getYear();
                String date = getString(R.string.string_date_add,month,day,year);
                Date dataEveniment = DateConverter.fromString(date);

//                db= FirebaseFirestore.getInstance();
//                Fediaf2021 f1=new Fediaf2021(113588248,"pisici");
//                Fediaf2021 f2=new Fediaf2021(92947732,"caini");
//                Fediaf2021 f3=new Fediaf2021(48719769,"pasari");
//                Fediaf2021 f4=new Fediaf2021(29347757,"rozatoare");
//                Fediaf2021 f5=new Fediaf2021(16403937,"pesti");
//
//                db.collection("AnimaleNivelEuropean").document(f1.getTipAnimal()).set(f1);
//                db.collection("AnimaleNivelEuropean").document(f2.getTipAnimal()).set(f2);
//                db.collection("AnimaleNivelEuropean").document(f3.getTipAnimal()).set(f3);
//                db.collection("AnimaleNivelEuropean").document(f4.getTipAnimal()).set(f4);
//                db.collection("AnimaleNivelEuropean").document(f5.getTipAnimal()).set(f5);
//                db=FirebaseFirestore.getInstance();
//                Animal a1=new Animal("Yuki","masculin","caine","Chow-Chow");
//                Animal a2=new Animal("Bella","feminin","pisica","Kaliko");
//                db.collection("Animale").document(a1.getNumeAnimal()).set(a1);
//                db.collection("Animale").document(a2.getNumeAnimal()).set(a2);

                db= FirebaseFirestore.getInstance();
                Intent intent = getIntent();
                User user;
                user=intent.getParcelableExtra("User");
                String email=null;
                if(intent.hasExtra(getString(R.string.login_email))){
                    email=getIntent().getStringExtra(getString(R.string.login_email));
                }
                CalendarVeterinar calendarVeterinar=new CalendarVeterinar(dataEveniment,numeEveniment, emailFromPrefferences);
                db.collection("EvenimenteCalendar").add(calendarVeterinar).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CalendarVeterinarActivity.this, "Vizita la veterinar inregistrata", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CalendarVeterinarActivity.this, "Eroare la legatura cu baza de date", Toast.LENGTH_SHORT).show();
                    }
                });
                db= FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("EvenimenteCalendar").document();

                Query query = FirebaseFirestore.getInstance()
                        .collection("EvenimenteCalendar")
                        .limit(50);
                query.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        document.getString("numeEveniment");
                                        document.getDate("dataEveniment");
                                        CalendarVeterinar calendarVeterinar2=new CalendarVeterinar( document.getDate("dataEveniment"),document.getString("numeEveniment"),emailFromPrefferences);;
                                        listaCalendar.add(calendarVeterinar2);
                                        Log.d(TAG, calendarVeterinar2.toString());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                listaCalendar.add(calendarVeterinar);
               // ArrayAdapter<CalendarVeterinar>adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listaCalendar);
                ListView lv=findViewById(R.id.lsEvenimente);
                ArrayAdapter<CalendarVeterinar>adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listaCalendar);
                lv.setAdapter(adapter);
                Intent it = new Intent(getApplicationContext(), ListaEvenimenteActivity.class);
                it.putExtra("calendar",calendarVeterinar);
                startActivity(it);
            }

        });

    }
}