package com.ase.dam.licenta01;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProfilAnimal extends AppCompatActivity {
    private TextInputEditText tietFullName;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPhone;
    private TextInputEditText tietPassword;
    private DatePicker dpBirthdate;
    private Button btnRegister;
    private RadioGroup radioGroup;
    private ImageView imageViewAvatar;
    private List<User> users = new ArrayList<>();
    private ActivityResultLauncher<Intent> addPhotoLauncher;
    // private Uri uri;
    private Button btnaddProfilePicture;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_animal);
        getSupportActionBar().setTitle(R.string.register_bar_title2);
        initComponents();
    }

    private void initComponents() {
        //  addPhotoLauncher = getUpdateAuctionLauncher();
        radioGroup = findViewById(R.id.rg_add_gender_type_animal);
        tietFullName = findViewById(R.id.tiet_register_animalName);
        tietEmail = findViewById(R.id.tiet_register_tip);
        tietPhone = findViewById(R.id.tiet_register_rasa);
        dpBirthdate = findViewById(R.id.dp_register_animal);
        btnRegister = findViewById(R.id.btn_register_register_animal);
        btnRegister.setOnClickListener(addAnimalToDatabaseClickListener());
//        imageViewAvatar=findViewById(R.id.iv_register_animal);
//        imageViewAvatar.setOnClickListener(addAnimalPicClickListener());
//        btnaddProfilePicture=findViewById(R.id.btn_add_profile_picture_animal);
//        btnaddProfilePicture.setOnClickListener(addUserPicClickListener());
    }


    private View.OnClickListener addAnimalToDatabaseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animal user = buildAnimal();
                db = FirebaseFirestore.getInstance();
                db.collection("Animale").document(user.getNumeAnimal()).set(user);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        };
    }
    private Animal buildAnimal(){
        String fullName = tietFullName.getText().toString();
        String tip = tietEmail.getText().toString();
        String rasa = tietPhone.getText().toString();
        int day = dpBirthdate.getDayOfMonth();
        int month = dpBirthdate.getMonth() + 1;
        int year = dpBirthdate.getYear();
        String gender = getString(R.string.male_register);
        if (radioGroup.getCheckedRadioButtonId() == R.id.rb_female) {
            gender = getString(R.string.female_register);;
        }
        String profilePic=getString(R.string.no_profile_pic);
//        if(uri!=null){
//            profilePic= uri.toString();
//        }

        return new Animal(fullName,gender,tip,rasa);
    }

}