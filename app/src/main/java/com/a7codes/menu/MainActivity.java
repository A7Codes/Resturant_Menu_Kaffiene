package com.a7codes.menu;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.a7codes.menu.Activites.Dashboard;
import com.a7codes.menu.Classes.DBHelper;

import java.io.File;
import java.security.Permission;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Views

    //Variables
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        startApp();

    }

    private void startApp(){
//            //Request Permissions
            requestPerms(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            requestPerms(Manifest.permission.READ_EXTERNAL_STORAGE);

            //If Permissions Are Granted
            // 1- Make Folders
            makeFolders("CATS");
            makeFolders("ITEMS");

            // 2- Make DB.
            DBHelper helper = new DBHelper(MainActivity.this);
            helper.createTableClassA();
            helper.createTableClassB();
            helper.createTableClassC();

            // 3- Go To Dashboard
            goToDash();
    }

    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher, as an instance variable.
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.

                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    private void requestPerms(String permission){
        if (ContextCompat.checkSelfPermission(
                this, permission) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.

        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    permission);
        }
    }

    public static File makeFolders(String FolderName) {
        File dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + "/" + "A7MENU" + "/" + FolderName);
        }
        else {
            dir = new File(Environment.getExternalStorageDirectory() + "/DCIM"  + "/A7MENU/" + FolderName);
        }

        // Make sure the path directory exists.
        if (!dir.exists()) {
            // Make it, if it doesn't exit
            boolean success = dir.mkdirs();
            if (!success) {
                dir = null;
            }
        }
        return dir;
    }

    private void goToDash(){
        Intent intent = new Intent(MainActivity.this, Dashboard.class);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 500);

    }
}