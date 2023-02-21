package com.ase.dam.licenta01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    public static final String CATEGORY_NAME = "CategoryName";
    public static final String LOGIN_CREDENTIALS = "LoginCredentials";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private User user;
    private Intent intent;
    private String email;
    private String emailFromPrefferences;
    private String passwordFromPrefferences;
    FirebaseFirestore db;

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
        setContentView(R.layout.activity_main);
        intent = getIntent();

        if (intent.hasExtra(getString(R.string.login_email))) {
            email = getIntent().getStringExtra(getString(R.string.login_email));
        }
        loadPreferences();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("utilizatori").document(emailFromPrefferences);
        Toast.makeText(this, emailFromPrefferences, Toast.LENGTH_SHORT).show();
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot task) {
                if (task.exists()) {
                    String email2 = task.getString("email");
                    String fullname = task.getString("fullname");
                    String gender = task.getString("gender");
                    String password = task.getString("password");
                    String phone = task.getString("phone");
                    String profilePic = task.getString("profilePic");
                    user = new User(fullname, email2, phone, gender, password, profilePic);

                } else {
                    Toast.makeText(getApplicationContext(), "Utilizatorul nu exista in baza de date", Toast.LENGTH_SHORT).show();

                }
            }
        });
        initComponents();
    }

    private void initComponents() {
        configNavigation();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getNavigationItemSelectedListener());
    }


    private NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_plimbari) {
                    Intent intent = new Intent(getApplicationContext(), PlimbariActivity.class);
                    intent.putExtra(CATEGORY_NAME, getString(R.string.plimbari_main));
                    intent.putExtra("User", user);
                    //Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else if (item.getItemId() == R.id.nav_mancare) {
                    Intent intent = new Intent(getApplicationContext(), MeseActivity.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.nav_veterinar) {
                    Intent intent = new Intent(getApplicationContext(), ListaEvenimenteActivity.class);
                    intent.putExtra("User", user);
                    intent.putExtra(getString(R.string.email_register), email);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.nav_statistica) {
                    Intent intent = new Intent(getApplicationContext(), ToateStatisticileActivity.class);
                    startActivity(intent);
//                } else if (item.getItemId() == R.id.nav_barchart) {
//                    Intent intent = new Intent(getApplicationContext(), BarChartActivity.class);
//                    startActivity(intent);
//                } else if (item.getItemId() == R.id.nav_eveniment) {
//                    Intent intent = new Intent(getApplicationContext(), ListaEvenimenteActivity.class);
//                    startActivity(intent);
                }  else if (item.getItemId() == R.id.nav_profil_animal) {
                    Intent intent = new Intent(getApplicationContext(), ProfilAnimal.class);
                    startActivity(intent);
                    finish();
                }
                else if (item.getItemId() == R.id.nav_log_out) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void configNavigation() {
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;
}