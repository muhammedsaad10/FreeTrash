package com.example.freetrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference reference;
    TextInputLayout username, password;
    FloatingActionButton login;
    String tagl;
    StringBuffer buffer = new StringBuffer();
    boolean isUserName = false;
    boolean isPassword = false;
     ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        username = (TextInputLayout) findViewById(R.id.username);
        password = (TextInputLayout) findViewById(R.id.password);
        login = (FloatingActionButton) findViewById(R.id.loginBtn);
        tagl = getIntent().getStringExtra("tag");
        login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        String usernameField = username.getEditText().getText().toString().trim();
        String passwordField = password.getEditText().getText().toString().trim();

        username.setError("");
        password.setError("");


        if (usernameField.equals("") || TextUtils.isEmpty(usernameField)) {
            username.setError("Empty username");
        }
        if (passwordField.equals("") || TextUtils.isEmpty(passwordField)) {
            password.setError("Empty password");
        }

        if (!usernameField.equals("") || !TextUtils.isEmpty(usernameField) && !passwordField.equals("") || !TextUtils.isEmpty(passwordField)) {
            username.setError("");
            password.setError("");

            if (tagl.equals("user")) {

                reference = FirebaseDatabase.getInstance().getReference("User");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnap : snapshot.getChildren()) {
                            if (userSnap.child("username").getValue(String.class).toLowerCase().equals(usernameField.toLowerCase()) &&
                                    userSnap.child("password").getValue(String.class).toLowerCase().equals(passwordField.toLowerCase())) {
                                Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getApplicationContext(), BinDisplay.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                               // Toast.makeText(LoginScreen.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            if (tagl.equals("admin")) {


                reference = FirebaseDatabase.getInstance().getReference().child("Admin");

                reference.child("admin1").child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()) {

                            if(snapshot.getValue().toString().equals(usernameField.trim())) {
                                isUserName = true;
                            }

                        }else {
                            isUserName =false;
                            Toast.makeText(LoginScreen.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                reference.child("admin1").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()) {

                            if(snapshot.getValue().toString().equals(passwordField.trim())) {
                                isPassword = true;
                            }

                        }else {
                            isPassword =false;
                            Toast.makeText(LoginScreen.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                if(isUserName && isPassword) {

                    username.getEditText().setText("");
                    password.getEditText().setText("");
                    username.setError("");
                    password.setError("");

                    Intent intent=new Intent(getApplicationContext(), AdminActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(this, "Login successfull", Toast.LENGTH_SHORT).show();
                }
                if(!isUserName) {
                    username.setError("Invalid username");
                }
                if(!isPassword) {
                    password.setError("Invalid password");
                }

            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),HomeScreen.class));
    }
}
