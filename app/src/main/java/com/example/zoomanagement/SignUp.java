package com.example.zoomanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    FirebaseAuth auth;

    private EditText userName;
    private EditText email;
    private EditText password;
    private EditText rPassword;
    private Button signUp;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.sign_up);

        auth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.tvUsername);
        email = findViewById(R.id.tvEmail);
        password = findViewById(R.id.tvPassword);
        rPassword = findViewById(R.id.tvRepeatPass);
        back = findViewById(R.id.backPress);
        signUp = findViewById(R.id.btnSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _userName = userName.getText().toString();
                String _email = email.getText().toString();
                String _password = password.getText().toString();
                String _rPassword = rPassword.getText().toString();

                if (TextUtils.isEmpty(_userName) || TextUtils.isEmpty(_email) || TextUtils.isEmpty(_password)){
                    Toast.makeText(SignUp.this, "Empty information!", Toast.LENGTH_SHORT).show();
                } else if (_password.length() < 6) {
                    Toast.makeText(SignUp.this, "Password must be more than 5 characters", Toast.LENGTH_SHORT).show();
                } else if (!_password.equals(_rPassword)) {
                    Toast.makeText(SignUp.this, "Wrong password", Toast.LENGTH_SHORT).show();
                } else {
                    UserSignUp(_email, _password);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, LogIn.class));
            }
        });
    }

    private void UserSignUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp.this, LogIn.class));
                } else {
                    Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}