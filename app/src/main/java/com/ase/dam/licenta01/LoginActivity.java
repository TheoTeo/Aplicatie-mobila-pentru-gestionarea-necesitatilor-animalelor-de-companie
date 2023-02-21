package com.ase.dam.licenta01;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGIN_CREDENTIALS = "LoginCredentials";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String CHECKED = "CHECKED";
    public static final String PARCELABLE_USER = "ParcelableUser";
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;
    private Button btnLogin;
    private Button btnRegister;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private ActivityResultLauncher<Intent> registerUserLauncher;
    private User user;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle(R.string.welcome_login);
        initComponents();
        loadPreferences();
    }


    private void initComponents() {
        tietEmail = findViewById(R.id.tiet_login_mail);
        tietPassword = findViewById(R.id.tiet_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        btnLogin.setOnClickListener(goToMainOnClickListener());
        btnRegister = findViewById(R.id.btn_login_register);
        btnRegister.setOnClickListener(getUserOnClickListener());
        checkBox = findViewById(R.id.cb_login_save);
        sharedPreferences = getSharedPreferences(LOGIN_CREDENTIALS, MODE_PRIVATE);
        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.login_email))){
            tietEmail.setText(getIntent().getStringExtra(getString(R.string.login_email)));
        }
        if(intent.hasExtra(getString(R.string.login_password))){
            tietPassword.setText(getIntent().getStringExtra(getString(R.string.login_password)));
        }
        if(intent.hasExtra("User")){
            user=intent.getParcelableExtra("User");
        }
    }

    private void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences(LOGIN_CREDENTIALS, MODE_PRIVATE);
        if (preferences.contains(EMAIL)) {
            String emailFromPrefferences = preferences.getString(EMAIL, getString(R.string.empty_string));
            tietEmail.setText(emailFromPrefferences);
        }
        if (preferences.contains(PASSWORD)) {
            String passwordFromPrefferences = preferences.getString(PASSWORD, getString(R.string.empty_string));
            tietPassword.setText(passwordFromPrefferences);
        }
        if (preferences.contains(CHECKED)) {
            Boolean checked = preferences.getBoolean(CHECKED, false);
            checkBox.setChecked(checked);
        }
    }

    private View.OnClickListener goToMainOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tietEmail.getText().toString();
                String password = tietPassword.getText().toString();
                if (checkBox.isChecked()) {
                    Boolean isChecked = checkBox.isChecked();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(EMAIL, email);
                    editor.putString(PASSWORD, password);
                    editor.putBoolean(CHECKED, isChecked);
                    editor.apply();
                } else {
                    sharedPreferences.edit().clear().apply();
                }
                db= FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("utilizatori").document(email);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot task) {
                        if (task.exists()) {
                            String email=task.getString("email");
                            String fullname=task.getString("fullname");
                            String gender=task.getString("gender");
                            String password=task.getString("password");
                            String phone=task.getString("phone");
                            String profilePic=task.getString("profilePic");
                            user=new User(fullname,email,phone,gender,password,profilePic);

                        } else {
                            Toast.makeText(getApplicationContext(), "Utilizatorul nu exista in baza de date", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("User",user);
                intent.putExtra(getString(R.string.email_register), email);
                startActivity(intent);
            }
        };
    }


    private View.OnClickListener getUserOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        };
    }


}