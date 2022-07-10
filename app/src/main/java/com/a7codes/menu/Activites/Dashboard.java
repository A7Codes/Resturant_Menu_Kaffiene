package com.a7codes.menu.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.a7codes.menu.Adapters.AdapterClassA;
import com.a7codes.menu.Classes.ClassA;
import com.a7codes.menu.Classes.ClassB;
import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.Classes.DBHelper;
import com.a7codes.menu.Classes.ImgHelper;
import com.a7codes.menu.Classes.LoadingDialog;
import com.a7codes.menu.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Dashboard extends AppCompatActivity implements AdapterClassA.ItemClickListener {

    //Views
    RecyclerView recA;
    ImageView settingImg;
    //Variables
    AdapterClassA adapter;
    ArrayList<ClassA> itemsA = new ArrayList<>();
    ArrayList<ClassB> itemsB = new ArrayList<>();
    ArrayList<ClassC> itemsC = new ArrayList<>();
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Objects.requireNonNull(getSupportActionBar()).hide();
        db = new DBHelper(this);
        ReadA();

        //Compressing All Images
        ReadB();
        ReadC();

        //
        commonMenuDirPath("ITEMS");

        LoadingDialog loadingDialog = new LoadingDialog(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ClassC itemC : itemsC){
                    CompressImages(itemC.getImg());
                }

//                for (int i = 0; i< itemsC.size(); i++){
//                    if(i == 0){
////                        loadingDialog.StartLoading();
//                    }
//                    CompressImages(itemsC.get(i).getImg());
//                    if (i == itemsC.size() - 1){
////                        loadingDialog.StopLoading();
//                    }
//                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ClassB itemB : itemsB){
                    CompressImages(itemB.getImg());
                }
            }
        }).start();
        //Compressing All Images

        //Setting Up Recycler View
        recA = findViewById(R.id.recA);
        recA.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterClassA(Dashboard.this, itemsA);
        adapter.setClickListener(this);
        recA.setAdapter(adapter);

        //Setting Up Logo Img
        settingImg = findViewById(R.id.dashSetting);
        settingImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(Dashboard.this, AdminSignIn.class);
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(Dashboard.this, Menu.class);
        intent.putExtra("cB", itemsA.get(position).get_id());
        startActivity(intent);
    }

    void ReadA(){
        Cursor cursor = db.SelectAll(1);
        if(cursor.getCount() == 0){
            Toast.makeText(Dashboard.this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                ClassA tmpItem = new ClassA(0, "", 0);
                tmpItem.set_id(cursor.getInt(0));
                tmpItem.setTitle(cursor.getString(1));
                tmpItem.setParent(cursor.getInt(2));

                itemsA.add(tmpItem);
            }
        }
    }

    void ReadB(){

        ClassB allItems = new ClassB(0, "الكل", 1, "ID1");
        itemsB.add(allItems);

        Cursor cursor = db.SelectAll(2);
        if(cursor.getCount() == 0){
            Toast.makeText(Dashboard.this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                ClassB tmpItem = new ClassB(0, "", 0,"");
                tmpItem.set_id(cursor.getInt(0));
                tmpItem.setTitle(cursor.getString(1));
                tmpItem.setParent(cursor.getInt(2));
                tmpItem.setImg(cursor.getString(3));

                itemsB.add(tmpItem);
            }
        }
    }

    void ReadC(){
        Cursor cursor = db.SelectAll(3);
        if(cursor.getCount() == 0){
            Toast.makeText(Dashboard.this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                ClassC tmpItem = new ClassC(0, "", 0,"", "");
                tmpItem.set_id(cursor.getInt(0));
                tmpItem.setTitle(cursor.getString(1));
                tmpItem.setParent(cursor.getInt(2));
                tmpItem.setImg(cursor.getString(3));
                tmpItem.setDESC(cursor.getString(4));
                itemsC.add(tmpItem);
            }
        }
    }

    private void CompressImages(String img){
        File file = commonMenuDirPath("ITEMS");
        File imgFilePng = new File(file + "/" + img + ".png");
        File imgFileJpg = new File(file + "/" + img + ".jpg");

        if(!imgFileJpg.exists() && imgFilePng.exists()){
            Bitmap bitmap = ImgHelper.decodeSampledBitmapFromResource(imgFilePng.getPath(), 512, 512);
            try {
                FileOutputStream out = new FileOutputStream(imgFileJpg);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static File commonMenuDirPath(String FolderName) {
        File dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + "/" + "A7MENU" + "/" + FolderName);
        } else {
            dir = new File(Environment.getExternalStorageDirectory() + "/DCIM"  + "/A7MENU/" + FolderName);
        }
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                dir = null;
            }
        }
        return dir;
    }

}