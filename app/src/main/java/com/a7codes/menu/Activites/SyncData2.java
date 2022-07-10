package com.a7codes.menu.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.a7codes.menu.Classes.ClassA;
import com.a7codes.menu.Classes.ClassB;
import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.Classes.DBHelper;
import com.a7codes.menu.ImportClasses.Rec1Class;
import com.a7codes.menu.ImportClasses.Rec2Class;
import com.a7codes.menu.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SyncData2 extends AppCompatActivity {

    //Views
    EditText dataEt;
    Button   impBtn;
    Button   iolBtn;
    Button   expBtn;
    RadioButton radItems;
    RadioButton radCats;
    RadioButton radCA;
    RadioButton radCB;
    RadioButton radCC;

    //Variables
    ArrayList<ClassA> itemsA = new ArrayList<>();
    ArrayList<ClassB> itemsB = new ArrayList<>();
    ArrayList<ClassC> itemsC = new ArrayList<>();
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data2);

        Assigner();
        //Set DB
        db = new DBHelper(this);
        Clicker();
    }

    private void Assigner(){
        dataEt   = findViewById(R.id.sd2JsonET);
        impBtn   = findViewById(R.id.sd2ImportBtn);
        iolBtn   = findViewById(R.id.sd2ImportOldBtn);
        expBtn   = findViewById(R.id.sd2ExportBtn);
        radItems = findViewById(R.id.sd2RadItems);
        radCats  = findViewById(R.id.sd2RadCats);
        radCA  = findViewById(R.id.sd2RadcA);
        radCB  = findViewById(R.id.sd2RadcB);
        radCC  = findViewById(R.id.sd2RadcC);
    }

    private void Clicker(){
        expBtn.setOnClickListener(view -> ExportData());

        impBtn.setOnClickListener(view -> {
            if (radCA.isChecked()){ImportDB(1);}
            else if (radCB.isChecked()){ImportDB(2);}
            else if (radCC.isChecked()){ImportDB(3);}
        });

        iolBtn.setOnClickListener(view -> {
            if(radItems.isChecked()){ImportOldDB1();}
            else if (radCats.isChecked()){ImportOldDB2();}
        });
    }

    private void ImportDB(int mode){
        try {
            // JSON array
            String json = dataEt.getText().toString();

            // convert JSON array to Java List
            ArrayList<ClassA> itemsA = new ArrayList<>();
            ArrayList<ClassB> itemsB = new ArrayList<>();
            ArrayList<ClassC> itemsC = new ArrayList<>();

            if (mode == 1){
                ReadA();
                for (ClassA itemA: itemsA){ db.DeleteRow(1, itemA.get_id()); }
                itemsA = new Gson().fromJson(json, new TypeToken<ArrayList<ClassA>>() {}.getType());
               for (ClassA itemA : itemsA){
                   db.AddClassA(itemA.get_id(), itemA.getTitle(), itemA.getParent());
               } } else if (mode == 2){
                ReadB();
                for (ClassB itemB: itemsB){ db.DeleteRow(2, itemB.get_id()); }
                itemsB = new Gson().fromJson(json, new TypeToken<ArrayList<ClassB>>() {}.getType());
                for (ClassB itemB : itemsB){
                    db.AddClassB(itemB.get_id(), itemB.getTitle(), itemB.getParent(), itemB.getImg());
                } } else if (mode == 3) {
                ReadC();
                for (ClassC itemC: itemsC){ db.DeleteRow(3, itemC.get_id()); }
                itemsC = new Gson().fromJson(json, new TypeToken<ArrayList<ClassC>>() {}.getType());
                for (ClassC itemC : itemsC){
                    db.AddClassC(itemC.get_id(), itemC.getTitle(), itemC.getParent(), itemC.getImg(),
                    itemC.getDESC());
                } }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            Toast.makeText(this, ""+ ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void ImportOldDB2(){
        // 1- Clear Database and Variables
        ReadB();

        for (ClassB itemB: itemsB){
            db.DeleteRow(2, itemB.get_id());
        }

        itemsB.clear();

        // 2- Read JSON
        try {
            // JSON array
            String json = dataEt.getText().toString();

            // convert JSON array to Java List
            ArrayList<Rec1Class> items1 = new Gson().fromJson(json, new TypeToken<ArrayList<Rec1Class>>() {}.getType());

            // Add to DB
            for (int i = 0; i < items1.size(); i ++){
                Rec1Class tmpItem = items1.get(i);
                ClassB tmpItemB = new ClassB(tmpItem.get_id(), tmpItem.getTitle(), 1, tmpItem.getImg());
                itemsB.add(tmpItemB);
            }

            // Add to DB
            for(ClassB itemB : itemsB){
                db.AddClassB(itemB.get_id(), itemB.getTitle(),
                        itemB.getParent(), itemB.getImg());
            }

        }

        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            Toast.makeText(this, ""+ ex, Toast.LENGTH_SHORT).show();
        }

    }

    private void ImportOldDB1(){
        // 1- Clear Database and Variables
        ReadC();

        for (ClassC itemC: itemsC){
            db.DeleteRow(2, itemC.get_id());
        }

        itemsC.clear();

        // 2- Read JSON
        try {
            // JSON array
            String json = dataEt.getText().toString();

            // convert JSON array to Java List
            ArrayList<Rec2Class> items2 = new Gson().fromJson(json, new TypeToken<ArrayList<Rec2Class>>() {}.getType());

            // Add to Variable
            for (int i = 0; i < items2.size(); i ++){
                Rec2Class tmpItem = items2.get(i);
                ClassC tmpItemC = new ClassC(tmpItem.get_id(), tmpItem.getArTitle(), tmpItem.getParent(), tmpItem.getImg(), tmpItem.getPrice());
                itemsC.add(tmpItemC);
            }

            // Add to DB
            for(ClassC itemC : itemsC){
                db.AddClassC(itemC.get_id(), itemC.getTitle(),
                            itemC.getParent(), itemC.getImg(),
                            itemC.getDESC());
            }
        }

        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            Toast.makeText(this, ""+ ex, Toast.LENGTH_SHORT).show();
        }

    }

    private void ExportData(){
        //Making Current Date
        SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd:HH:mm:ss");
        String curDate = sdf.format(new Date());

        //Get The Data Ready
        ReadA();
        ReadB();
        ReadC();

        //Making Txt Files
        File BackupFile = commonMenuDirPath("BACKUPS/" + curDate);

        //Getting DB As JSON String
        Gson gson = new Gson();
        String jsonA = gson.toJson(itemsA);
        String jsonB = gson.toJson(itemsB);
        String jsonC = gson.toJson(itemsC);

        //Write Data To Files
        try {
            File buClassA   = new File(BackupFile + "/ClassA" + ".txt" );
            File buClassB   = new File(BackupFile + "/ClassB" + ".txt" );
            File buClassC   = new File(BackupFile + "/ClassC" + ".txt" );
            FileWriter writer1 = new FileWriter(buClassA);
            FileWriter writer2 = new FileWriter(buClassB);
            FileWriter writer3 = new FileWriter(buClassC);
            writer1.append(jsonA);
            writer2.append(jsonB);
            writer3.append(jsonC);
            writer1.flush();
            writer2.flush();
            writer3.flush();
            writer1.close();
            writer2.close();
            writer3.close();
        } catch (Exception e) {
            Log.d("Error //", "ExportData: " + e);
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

    void ReadC(){
        Cursor cursor = db.SelectAll(3);
        if(cursor.getCount() == 0){
            Toast.makeText(SyncData2.this, "No Data.", Toast.LENGTH_SHORT).show();
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

    void ReadB(){

        ClassB allItems = new ClassB(0, "الكل", 1, "");
        itemsB.add(allItems);

        Cursor cursor = db.SelectAll(2);
        if(cursor.getCount() == 0){
            Toast.makeText(SyncData2.this, "No Data.", Toast.LENGTH_SHORT).show();
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

    void ReadA(){
        itemsA.clear();
        Cursor cursor = db.SelectAll(1);
        if(cursor.getCount() == 0){
            Toast.makeText(SyncData2.this, "No Data.", Toast.LENGTH_SHORT).show();
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

}