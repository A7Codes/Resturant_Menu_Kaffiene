package com.a7codes.menu.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a7codes.menu.Classes.ClassA;
import com.a7codes.menu.Classes.ClassB;
import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.Classes.DBHelper;
import com.a7codes.menu.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SyncData extends AppCompatActivity {

    //Views
    Button uploadBtn;
    Button downloadBtn;
    EditText verET;
    ConstraintLayout root;

    //Variables
    DBHelper db;
    ArrayList<ClassA> itemsA = new ArrayList<>();
    ArrayList<ClassB> itemsB = new ArrayList<>();
    ArrayList<ClassC> itemsC = new ArrayList<>();
    int dbVer = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);

        // First Setting
        db = new DBHelper(this);
        Assigner();

        //Read All Data
        ReadA();
        ReadB();
        ReadC();

        //Upload Tapped
        uploadBtn.setOnClickListener(view -> UploadData());

        //Download Tapped
        downloadBtn.setOnClickListener(view -> DownloadData());

    }

    private void Assigner(){
        uploadBtn = findViewById(R.id.sncUploadBtn);
        downloadBtn = findViewById(R.id.sncDownloadBtn);
        verET = findViewById(R.id.sncVerET);
        root = findViewById(R.id.sdRoot);
    }

    private void UploadData(){

        dbVer = Integer.parseInt(verET.getText().toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String date = df.format(new Date());

        if (dbVer != 0){

            for(ClassA tmpA: itemsA){
                myRef.child("Kaffiene").child("DB").child(String.valueOf(dbVer)).child(date).child("ClassA")
                        .child(String.valueOf(tmpA.get_id())).setValue(tmpA);
            }

            for(ClassB tmpB: itemsB){
                myRef.child("Kaffiene").child("DB").child(String.valueOf(dbVer)).child(date).child("ClassB")
                        .child(String.valueOf(tmpB.get_id())).setValue(tmpB);
            }

            for(ClassC tmpC: itemsC){
                myRef.child("Kaffiene").child("DB").child(String.valueOf(dbVer)).child(date).child("ClassC")
                        .child(String.valueOf(tmpC.get_id())).setValue(tmpC);

                if (itemsC.indexOf(tmpC) == itemsC.size() - 1){
                    mkSnack("DB Uploaded Successfully");
                }
            }

        } else {
            mkSnack("please Enter DB Version");
        }
    }

    void DownloadData(){
        if (!verET.getText().equals("")){
            dbVer = Integer.parseInt(verET.getText().toString());
        } else {
            Toast.makeText(this, "Enter A Version Number", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase dbF = FirebaseDatabase.getInstance();
        DatabaseReference refKaffiene = dbF.getReference()
                .child("Kaffiene")
                .child("DB")
                .child(String.valueOf(dbVer));

        //Clear SQLite DB
        for(ClassA itemA: itemsA){
            db.DeleteRow(1, itemA.get_id());
        }

        for(ClassB itemB: itemsB){
            db.DeleteRow(2, itemB.get_id());
        }

        for(ClassC itemC: itemsC){
            db.DeleteRow(3, itemC.get_id());
        }

        itemsA.clear();
        itemsB.clear();
        itemsC.clear();
        //Clear SQLite DB

        refKaffiene.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap1 : dataSnapshot.getChildren()){
                    for (DataSnapshot snap2 : snap1.getChildren()){
                        Log.d("Snap 2 //", "" + snap2.getKey());
                        for (DataSnapshot snap2A : snap2.getChildren()){
                            Log.d("Snap 2A //", "" + snap2A.getKey());

                                //Class A
                                if(snap2.getKey().equals("ClassA")){
                                    ClassA tmpA = new ClassA(0, "", 0);
                                    for (DataSnapshot snap3 : snap2.getChildren()){
                                        for (DataSnapshot snap4 : snap3.getChildren()){
                                            //Filling A Item
                                            if (snap4.getKey().equals("_id")){tmpA.set_id(Integer.parseInt(snap4.getValue().toString()));}
                                            if (snap4.getKey().equals("title")){tmpA.setTitle(snap4.getValue().toString());}
                                            if (snap4.getKey().equals("parent")){tmpA.setParent(Integer.parseInt(snap4.getValue().toString()));}
                                            //Filling A Item
                                        }
                                        addDataA(tmpA);
                                    }
                                }
                                //ClassB
                                if(snap2.getKey().equals("ClassB")){
                                    ClassB tmpB = new ClassB(0, "", 0, "");
                                    for (DataSnapshot snap3 : snap2.getChildren()){
                                        for (DataSnapshot snap4 : snap3.getChildren()){
                                            //Filling A Item
                                            if (snap4.getKey().equals("_id")){tmpB.set_id(Integer.parseInt(snap4.getValue().toString()));}
                                            if (snap4.getKey().equals("title")){tmpB.setTitle(snap4.getValue().toString());}
                                            if (snap4.getKey().equals("parent")){tmpB.setParent(Integer.parseInt(snap4.getValue().toString()));}
                                            if (snap4.getKey().equals("img")){tmpB.setImg(snap4.getValue().toString());}
                                            //Filling A Item
                                        }
                                        if (tmpB.get_id() != 0){
                                            addDataB(tmpB);
                                        }
                                    }
                                }
                                //Class C
                                if(snap2.getKey().equals("ClassC")){
                                    ClassC tmpC = new ClassC(0, "", 0, "", "");
                                    for (DataSnapshot snap3 : snap2.getChildren()){
                                        for (DataSnapshot snap4 : snap3.getChildren()){
                                            //Filling A Item
                                            if (snap4.getKey().equals("_id")){tmpC.set_id(Integer.parseInt(snap4.getValue().toString()));}
                                            if (snap4.getKey().equals("title")){tmpC.setTitle(snap4.getValue().toString());}
                                            if (snap4.getKey().equals("parent")){tmpC.setParent(Integer.parseInt(snap4.getValue().toString()));}
                                            if (snap4.getKey().equals("img")){tmpC.setImg(snap4.getValue().toString());}
                                            if (snap4.getKey().equals("desc")){tmpC.setDESC(snap4.getValue().toString());}
                                            //Filling A Item
                                        }
                                        addDataC(tmpC);
                                    }
                                }
                            }
                        }
                    }
                mkSnack("DB Downloaded Successfully");
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mkSnack("Error Contact Ahmed Please");
            }
        });



    }

    void addDataA(ClassA itemA){
        db.AddClassA(itemA.get_id(), itemA.getTitle(), itemA.getParent());
    }
    void addDataB(ClassB itemB){
        db.AddClassB(itemB.get_id(), itemB.getTitle(), itemB.getParent(), itemB.getImg());
    }
    void addDataC(ClassC itemC){
        db.AddClassC(itemC.get_id(), itemC.getTitle(), itemC.getParent(), itemC.getImg(), itemC.getDESC());
    }

    void ReadC(){
        Cursor cursor = db.SelectAll(3);
        if(cursor.getCount() == 0){
            Toast.makeText(SyncData.this, "No Data.", Toast.LENGTH_SHORT).show();
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

        ClassB allItems = new ClassB(0, "الكل", 1, "ID1");
        itemsB.add(allItems);

        Cursor cursor = db.SelectAll(2);
        if(cursor.getCount() == 0){
            Toast.makeText(SyncData.this, "No Data.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SyncData.this, "No Data.", Toast.LENGTH_SHORT).show();
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

    void mkSnack(String txt){
        Snackbar.make(root, "" + txt, Snackbar.LENGTH_SHORT).show();
    }



}