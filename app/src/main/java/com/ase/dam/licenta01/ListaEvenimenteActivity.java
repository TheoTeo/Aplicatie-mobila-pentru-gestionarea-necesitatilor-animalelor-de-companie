package com.ase.dam.licenta01;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaEvenimenteActivity extends AppCompatActivity {

    private Button btnInregistrareEveniment;
    private Button btnIntoarcere;
    private List<CalendarVeterinar> listaCalendar=new ArrayList<>();
    public static final String LOGIN_CREDENTIALS = "LoginCredentials";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    private String emailFromPrefferences;
    private String passwordFromPrefferences;

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
        setContentView(R.layout.activity_lista_evenimente);
        getSupportActionBar().setTitle(R.string.eveniment);
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
                                if(document.getString("emailUser").equals(emailFromPrefferences)){
                                document.getString("numeEveniment");
                                document.getDate("dataEveniment");
                                CalendarVeterinar calendarVeterinar2=new CalendarVeterinar( document.getDate("dataEveniment"),document.getString("numeEveniment"),emailFromPrefferences);
                                listaCalendar.add(calendarVeterinar2);
                                Log.d(TAG, listaCalendar.toString());}
                                ArrayAdapter<CalendarVeterinar> adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listaCalendar);
                                ListView lv=findViewById(R.id.lsevent);
                                lv.setAdapter(adapter);
                                lv.setBackgroundColor(Color.rgb(253,245,230));
                                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                        final int whichItem=position-1;
//                                        Toast.makeText(getApplicationContext(), "LongClick", Toast.LENGTH_SHORT).show();
                                        new AlertDialog.Builder(ListaEvenimenteActivity.this)
                                                .setTitle("Sunteti sigur?").setMessage("Doriti sa stergeti acest eveniment?")
                                                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Query query = FirebaseFirestore.getInstance()
                                                                .collection("EvenimenteCalendar")
                                                                .limit(50);
                                                        query.get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                if(document.getString("numeEveniment").equals(listaCalendar.get(whichItem).getNumeEveniment())){
                                                                                  // Toast.makeText(getApplicationContext(), listaCalendar.get(whichItem).getNumeEveniment()+document.getString("numeEveniment"), Toast.LENGTH_SHORT).show();
                                                                                    document.getDate("dataEveniment");
                                                                                    String id=document.getId();
                                                                                    FirebaseFirestore.getInstance().collection("EvenimenteCalendar").document(id)
                                                                                            .delete()
                                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void aVoid) {
                                                                                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                                                                }
                                                                                            })
                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                    Log.w(TAG, "Error deleting document", e);
                                                                                                }
                                                                                            });
                                                                                }}
                                                                        } else {
                                                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                                                        }
                                                                    }
                                                                });
                                                        listaCalendar.remove(whichItem);
                                                        ArrayAdapter<CalendarVeterinar>adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listaCalendar);
                                                        adapter.notifyDataSetChanged();
                                                        lv.setAdapter(adapter);
                                                        lv.setBackgroundColor(Color.rgb(253,245,230));
                                                    }
                                                }).setNegativeButton("Nu",null)
                                                .show();
                                        return true;
                                    }
                                });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        btnInregistrareEveniment=findViewById(R.id.btn_adEveniment);
        btnInregistrareEveniment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarVeterinarActivity.class);
                startActivity(intent);
            }
        }
        );
        btnIntoarcere=findViewById(R.id.btn_back);
        btnIntoarcere.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
        );

    }

}