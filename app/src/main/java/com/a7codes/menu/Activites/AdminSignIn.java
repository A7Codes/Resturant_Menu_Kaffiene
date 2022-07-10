package com.a7codes.menu.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.a7codes.menu.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSignIn extends AppCompatActivity {

    //Views
    EditText txtPass;
    Button   btnSign;
    ConstraintLayout root;
    ImageButton btnEye;

    //Vars
    String cloPass = "";
    String locPass = "";
    int inputType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

        //Assign Views
        Assigner();

        //SignIn Tapped
        btnSign.setOnClickListener(view -> {SignIn();});

        //Showing - Hiding Password
        btnEye.setOnClickListener(view -> {
           if (inputType == 0){
               txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
               inputType = 1;
           } else if (inputType == 1){
               txtPass.setInputType(InputType.TYPE_CLASS_TEXT);
               inputType = 0;
           }
        });

    }

    void Assigner(){
        txtPass = findViewById(R.id.asEt);
        btnSign = findViewById(R.id.asBtn);
        root = findViewById(R.id.asRoot);
        btnEye = findViewById(R.id.asEye);
    }

    void SignIn(){
        GetPasswords();
    }

    void GetPasswords(){

        //1- Cloud Password
        FirebaseDatabase dbF = FirebaseDatabase.getInstance();
        DatabaseReference refPass = dbF.getReference()
                .child("Kaffiene")
                .child("Usr")
                .child("AdminPass");

        refPass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tmpPass = dataSnapshot.getValue().toString();
                ReturnPassword(2, tmpPass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERR///", "FirebasePass: Error retrieving password ");
            }
        });

        //2- Local Password
        ReturnPassword(1, txtPass.getText().toString().trim());
    }

    void ReturnPassword(int m, String pass){
        if (m == 1){
            locPass = pass;
        } else if (m == 2){
            cloPass = pass;
        } else {
            Log.d("ERR///", "returnPassword: mode is not 1 || 2");
        }

        if (!locPass.equals("") && !cloPass.equals("")){
            Auth();
        }
    }

    void Auth(){
        if (locPass.equals(cloPass)){
            Intent intent = new Intent(AdminSignIn.this, Settings.class);
            startActivity(intent);
        } else {
            mkSnack("Password Is Incorrect Please Try Again");
        }
    }

    void mkSnack(String txt){
        Snackbar.make(root, "" + txt, Snackbar.LENGTH_SHORT).show();
    }

}