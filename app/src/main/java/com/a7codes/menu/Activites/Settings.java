package com.a7codes.menu.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.a7codes.menu.R;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    //Views
    Button addBtn;
    Button edtBtn;
    Button uplBtn;
    Button dowBtn;

    //Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).hide();
        Assigner();
        Clicker();

    }

    private void Assigner(){
        addBtn = findViewById(R.id.settingAddData);
        edtBtn = findViewById(R.id.settingEdtData);
        uplBtn = findViewById(R.id.settingUplData);
        dowBtn = findViewById(R.id.settingDowData);
    }
    private void Clicker(){
        // Go Add.
        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, AddData.class);
            startActivity(intent);
        });

        // Go Edit.
        edtBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, EditData.class);
            startActivity(intent);
        });

        // Go Sync.
        uplBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, SyncData.class);
            startActivity(intent);
        });

        // Go Sync 2.
        dowBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, SyncData2.class);
            startActivity(intent);
        });
    }

}