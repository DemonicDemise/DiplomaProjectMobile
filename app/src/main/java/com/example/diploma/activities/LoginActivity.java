package com.example.diploma.activities;

import androidx.annotation.NonNull;
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

import com.example.diploma.MainActivity;
import com.example.diploma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button signIn;
    private EditText email, password;
    private TextView signUp;

    private FirebaseAuth mAuth;

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        signIn = findViewById(R.id.login_btn);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        signUp = findViewById(R.id.login_register);

        pb = findViewById(R.id.progressbar);
        pb.setVisibility(View.GONE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
                pb.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

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

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            pb.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Login Successfull", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Error" + task.isSuccessful(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}