package com.example.zoomanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    FirebaseAuth auth;

    private EditText email;
    private EditText password;
    private Button logIn;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.log_in);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.tvEmail);
        password = findViewById(R.id.tvPassword);
        logIn = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.tvGoSignUp);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email = email.getText().toString();
                String _password = password.getText().toString();

                UserLogIn(_email, _password);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, SignUp.class));
            }
        });

    }

    private void UserLogIn(String email, String password) {
         auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){
                     Toast.makeText(LogIn.this, "Success", Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(LogIn.this, "Failed", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }
}