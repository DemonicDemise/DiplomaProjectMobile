package com.example.diploma.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diploma.R;
import com.example.diploma.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private Button signUp;
    private EditText name, email, password;
    private TextView signIn;

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.reg_btn);
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        signIn = findViewById(R.id.reg_login);

        pb = findViewById(R.id.progressbar);
        pb.setVisibility(View.GONE);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
                pb.setVisibility(View.VISIBLE);
               // pCreateUser();
            }
        });
    }
    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Name is Empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email is Empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Password is Empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(userPassword.length() < 6){
            Toast.makeText(this,"Password length must be greater than 6 characters", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        UserModel userModel = new UserModel(userName, userEmail, userPassword);
                        String id = task.getResult().getUser().getUid();
                        db.getReference().child("Users").child(id).setValue(userModel);
                        pb.setVisibility(View.GONE);
                        Toast.makeText(RegistrationActivity.this, "Registration Successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }
                    else{
                        pb.setVisibility(View.GONE);
                        Toast.makeText(RegistrationActivity.this, "Error:" + task.getException(),Toast.LENGTH_LONG);
                    }
                });
    }

//    private void pCreateUser() throws SQLException {
//        Connection conn = DriverManager.getConnection("jdbc:postgresql://<host>:<port>/<database>", "<username>", "<password>");
//
//    }
}