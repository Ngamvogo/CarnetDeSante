package com.example.carnetdesante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class resisteActivity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resiste2);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            finish();
            return;
        }
        Button btnlogin = findViewById(R.id.login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });

        TextView toRegister = findViewById(R.id.Register2);
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wtoRegister();
            }
        });
    }
    private void authenticateUser(){
        EditText lemail = findViewById(R.id.email2);
        EditText lpassword = findViewById(R.id.password2);

        String email = lemail.getText().toString();
        String password = lpassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            showMainActivity();
                        }else {
                            Toast.makeText(resisteActivity2.this,"Authenticatin failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void wtoRegister(){
        Intent intent = new Intent(this, ginActivity2.class);
        startActivity(intent);
        finish();
    }
}