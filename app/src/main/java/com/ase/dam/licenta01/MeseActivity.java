package com.ase.dam.licenta01;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

        import java.util.Calendar;

public class MeseActivity extends AppCompatActivity {
    TimePicker timePicker;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mese);
        timePicker=(TimePicker)findViewById(R.id.timePickerMese);
        findViewById(R.id.btnSetAlarmMese).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarMese=Calendar.getInstance();
                if(Build.VERSION.SDK_INT>=23) {
                    calendarMese.set(calendarMese.get(Calendar.YEAR), calendarMese.get(Calendar.MONTH), calendarMese.get(Calendar.DAY_OF_MONTH), timePicker.getHour(), timePicker.getMinute(), 0);
                }
                else{
                    calendarMese.set(calendarMese.get(Calendar.YEAR), calendarMese.get(Calendar.MONTH), calendarMese.get(Calendar.DAY_OF_MONTH), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);

                }
                setAlarm(calendarMese.getTimeInMillis());

                db= FirebaseFirestore.getInstance();
                Intent intent = getIntent();
                User user;
                user=intent.getParcelableExtra("User");
                PlimbareMasaAlarma masa=new PlimbareMasaAlarma(user.getEmail(),timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                db.collection("AlarmeMese").add(masa).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MeseActivity.this, "Alarma pentru masa inregistrata", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MeseActivity.this, "Eroare la legatura cu baza de date", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, Alarma.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this, "Alarma este setata", Toast.LENGTH_SHORT).show();


    }


}