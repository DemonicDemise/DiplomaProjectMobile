package com.example.diploma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signUp;
    private EditText name, email, password;
    private TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.reg_btn);
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        signIn = findViewById(R.id.reg_login);

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
                        Toast.makeText(RegistrationActivity.this, "Registration Successfully",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Error:" + task.getException(),Toast.LENGTH_LONG);
                    }
                });
    }
}