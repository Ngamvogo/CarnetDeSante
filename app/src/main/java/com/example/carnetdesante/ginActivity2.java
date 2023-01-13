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
import com.google.firebase.database.FirebaseDatabase;

public class ginActivity2 extends AppCompatActivity {
 private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gin2);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            finish();
            return;
        }
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }

        });

        TextView textViewSwitchToLogin = findViewById(R.id.loogin);
        textViewSwitchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loogin();
            }

        });
    }
    private void registerUser(){
        EditText firstname = findViewById(R.id.firstname);
        EditText lastname = findViewById(R.id.lastname);
        EditText Eemail = findViewById(R.id.email);
        EditText Epassword = findViewById(R.id.password);

        String sfristname = firstname.getText().toString();
        String slastname = lastname.getText().toString();
        String semail = Eemail.getText().toString();
        String spassword = Epassword.getText().toString();

        if (sfristname.isEmpty() || slastname.isEmpty() || semail.isEmpty() || spassword.isEmpty()){
            Toast.makeText(this, "please fill all field", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(semail,spassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User( sfristname, slastname, semail);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            showMainActivity();
                                        }
                                    });
                        }else {
                            Toast.makeText(ginActivity2.this,"Authentication failed.",
                                    Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }
    private void showMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void loogin(){
        Intent intent = new Intent(this,resisteActivity2.class);
        startActivity(intent);
        finish();
    }
}