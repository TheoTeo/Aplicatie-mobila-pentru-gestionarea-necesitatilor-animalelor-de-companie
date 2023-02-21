package com.ase.dam.licenta01;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle(R.string.register_bar_title);
        initComponents();
    }

    private void initComponents() {
      //  addPhotoLauncher = getUpdateAuctionLauncher();
        radioGroup=findViewById(R.id.rg_add_gender_type);
        tietFullName = findViewById(R.id.tiet_register_firstName);
        tietEmail = findViewById(R.id.tiet_register_mail);
        tietPhone = findViewById(R.id.tiet_register_phone);
        tietPassword = findViewById(R.id.tiet_register_password);
        dpBirthdate = findViewById(R.id.dp_register);
        btnRegister = findViewById(R.id.btn_register_register);
        btnRegister.setOnClickListener(addUserToDatabaseClickListener());
        imageViewAvatar=findViewById(R.id.iv_register_avatar);
        imageViewAvatar.setOnClickListener(addUserPicClickListener());
        btnaddProfilePicture=findViewById(R.id.btn_add_profile_picture);
        btnaddProfilePicture.setOnClickListener(addUserPicClickListener());
    }

    private View.OnClickListener addUserPicClickListener() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                addPhotoLauncher.launch(intent);
            }
        };
    }
//
//
//    private ActivityResultLauncher<Intent> getUpdateAuctionLauncher() {
//        ActivityResultCallback<ActivityResult> callback = getPhotoActivityResultCallback();
//        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
//    }
//
//    private ActivityResultCallback<ActivityResult> getPhotoActivityResultCallback() {
//        return new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result != null && result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    uri=result.getData().getData();
//                    imageViewAvatar.setImageURI(uri);
//                }
//            }
//        };
//    }


    private View.OnClickListener addUserToDatabaseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    if (uniq()) {
                        User user = buildUser();
                        db= FirebaseFirestore.getInstance();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        String email = tietEmail.getText().toString();
                        String password = tietPassword.getText().toString();
                        intent.putExtra(getString(R.string.email_register), email);
                        intent.putExtra(getString(R.string.password_register), password);
                        intent.putExtra("User",user);


                        db.collection("utilizatori").document(email).set(user);

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                R.string.registerActivityMessage,
                                Toast.LENGTH_LONG)
                                .show();
                    }

                }
            }
        };
    }


    private boolean isValid() {
        if (tietFullName.getText().toString().isEmpty() || tietPassword.getText().toString().isEmpty()
                || tietPhone.getText().toString().isEmpty() || tietEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.empy_filds_error, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!tietFullName.getText().toString().matches(getString(R.string.regex_only_text_matches)) || tietFullName.getText().length() < 3
                || tietFullName.getText().length() > 20) {
            Toast.makeText(this, R.string.incorrect_name_error, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!tietPhone.getText().toString().matches(getString(R.string.reges_phone_matches)) || tietPhone.getText().length() != 10) {
            Toast.makeText(this, R.string.incorrect_phone_error, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!tietEmail.getText().toString().matches(getString(R.string.regex_email_matches))) {
            Toast.makeText(this, R.string.incorrect_email_error, Toast.LENGTH_SHORT).show();
            return false;
        } else if (dpBirthdate.getYear() > 2021) {
            Toast.makeText(this, R.string.invalid_date_error, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    };


    private boolean uniq() {
        for (User u : users) {
            if (u.getEmail().equals(tietEmail.getText().toString())) {
                return false;
            } else if (u.getPhone().equals(tietPhone.getText().toString())) {
                return false;
            }
        }
        return true;
    }


    private User buildUser() {
        String fullName = tietFullName.getText().toString();
        String email = tietEmail.getText().toString();
        String phone = tietPhone.getText().toString();
        String password = tietPassword.getText().toString();
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

        return new User(fullName, email, phone, gender, password,profilePic);
    }


}