package com.a7codes.menu.Activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.a7codes.menu.Classes.AlertHelper;
import com.a7codes.menu.Classes.ClassA;
import com.a7codes.menu.Classes.ClassB;
import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.Classes.DBHelper;
import com.a7codes.menu.R;

import java.util.ArrayList;

public class AddData extends AppCompatActivity {

    //Views
    RadioGroup     classRG;
    RadioButton aRadioBtn;
    RadioButton bRadioBtn;
    RadioButton cRadioBtn;
    EditText         idTv;
    EditText      titleTv;
    EditText     parentTv;
    EditText        imgTv;
    EditText       descTv;
    EditText      priceTv;
    Button         addBtn;
    Button         updBtn;

    //Variables
    ArrayList<ClassA> itemsA = new ArrayList<>();
    ArrayList<ClassB> itemsB = new ArrayList<>();
    ArrayList<ClassC> itemsC = new ArrayList<>();
    DBHelper db;
    int cfw = 0;
    int mode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        Assigner();
        db = new DBHelper(this);
        addBtn.setOnClickListener(view -> AddDataToDb());
        updBtn.setOnClickListener(view -> updateRow());
        intentToEdit();
    }

    private void Assigner(){
        classRG = findViewById(R.id.adClassRG);
        aRadioBtn = findViewById(R.id.adRbA);
        bRadioBtn = findViewById(R.id.adRbB);
        cRadioBtn = findViewById(R.id.adRbC);

        idTv = findViewById(R.id.adIDTv);
        titleTv = findViewById(R.id.adTitleTv);
        parentTv = findViewById(R.id.adParentTv);
        imgTv = findViewById(R.id.adImgTv);
        descTv = findViewById(R.id.adDescTv);
        priceTv = findViewById(R.id.adPriceTv);

        addBtn = findViewById(R.id.adAddBtn);
        updBtn = findViewById(R.id.adUpdBtn);
    }

    private void AddDataToDb(){
        int id       = Integer.parseInt(idTv.getText().toString().trim());
        String title = titleTv.getText().toString().trim();
        int parent   = Integer.parseInt(parentTv.getText().toString().trim());
        String img   = imgTv.getText().toString().trim();
        String desc  = descTv.getText().toString().trim();
        String price  = priceTv.getText().toString().trim();
        AlertHelper al = new AlertHelper(this);
        db = new DBHelper(this);

        int radChosen = 0;

        if (aRadioBtn.isChecked()){
            radChosen = 1;
        } else if (bRadioBtn.isChecked()){
            radChosen = 2;
        } else if (cRadioBtn.isChecked()){
            radChosen = 3;
        }

        switch (radChosen){
            case 1:
                if (checkFilled(radChosen) && !idExists()){
                    db.AddClassA(id, title, parent); } else if (idExists()){
                    al.MakeAlert("خطأ", "هذا ال ID موجود بالفعل" , "حسناً");
                }
                break;
            case 2:
                if (checkFilled(radChosen) && !idExists()){
                    db.AddClassB(id, title, parent, img); } else if (idExists()){
                    al.MakeAlert("خطأ", "هذا ال ID موجود بالفعل", "حسناً");
                }
                break;
            case 3:
                if (checkFilled(radChosen) && !idExists()){
                    db.AddClassC(id, title, parent, img, desc, price); } else if (idExists()){
                    al.MakeAlert("خطأ", "هذا ال ID موجود بالفعل", "حسناً");
                }
                break;
        }

    }

    private Boolean checkFilled(int radChosen){
        String id       = idTv.getText().toString().trim();
        String title    = titleTv.getText().toString().trim();
        String parent   = parentTv.getText().toString().trim();
        String img      = imgTv.toString().trim();
        String desc     = descTv.getText().toString().trim();
        String price    = priceTv.getText().toString().trim();

        if (id.equals("") ||
            title.equals("") ||
            parent.equals("")){
            Toast.makeText(this, "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (img.equals("") && radChosen >= 2){
            Toast.makeText(this, "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (desc.equals("") && radChosen == 3){
            Toast.makeText(this, "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            return true;
        }

    }

    private boolean idExists(){
        int radChosen = 0;
        boolean retVal = false;
        int ID = Integer.parseInt(idTv.getText().toString().trim());

        if (aRadioBtn.isChecked()){
            radChosen = 1;
        } else if (bRadioBtn.isChecked()){
            radChosen = 2;
        } else if (cRadioBtn.isChecked()){
            radChosen = 3;
        }

        switch (radChosen){
            case 1:
                ReadA();
                for (int i = 0; i < itemsA.size(); i++){
                    if (ID == itemsA.get(i).get_id()){
                        retVal = true;
                    }
                }
                break;

            case 2:
                ReadB();
                for (int i = 0; i < itemsB.size(); i++){
                    if (ID == itemsB.get(i).get_id()){
                        retVal = true;
                    }
                }
                break;

            case 3:
                ReadC();
                for (int i = 0; i < itemsC.size(); i++){
                    if (ID == itemsC.get(i).get_id()){
                        retVal = true;
                    }
                }
                break;

        }

        return retVal;
    }

    void intentToEdit(){
        Intent intent = getIntent();
        cfw = intent.getIntExtra("cfw", 0);
        mode = intent.getIntExtra("mode", 0);
        Toast.makeText(this, "CFW : " + cfw, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "MODE : " + mode, Toast.LENGTH_SHORT).show();

            switch (mode) {
                case 1:
                    ReadA();
                    aRadioBtn.setChecked(true);
                    for (int i = 0; i < itemsA.size(); i++) {
                        if (itemsA.get(i).get_id() == cfw) {
                            idTv.setText(String.valueOf(itemsA.get(i).get_id()));
                            titleTv.setText(itemsA.get(i).getTitle());
                            parentTv.setText(String.valueOf(itemsA.get(i).getParent()));
                        }
                    }
                    break;

                case 2:
                    ReadB();
                    bRadioBtn.setChecked(true);
                    for (int i = 0; i < itemsB.size(); i++) {
                        if (itemsB.get(i).get_id() == cfw) {
                            idTv.setText(String.valueOf(itemsB.get(i).get_id()));
                            titleTv.setText(itemsB.get(i).getTitle());
                            parentTv.setText(String.valueOf(itemsB.get(i).getParent()));
                            imgTv.setText(itemsB.get(i).getImg());
                        }
                    }
                    break;

                case 3:
                    ReadC();
                    cRadioBtn.setChecked(true);
                    for (int i = 0; i < itemsC.size(); i++) {
                        if (itemsC.get(i).get_id() == cfw) {
                            idTv.setText(String.valueOf(itemsC.get(i).get_id()));
                            titleTv.setText(itemsC.get(i).getTitle());
                            parentTv.setText(String.valueOf(itemsC.get(i).getParent()));
                            imgTv.setText(itemsC.get(i).getImg());
                            descTv.setText(itemsC.get(i).getDESC());
                            priceTv.setText(itemsC.get(i).getPrice());
                        }
                    }
                    break;

            }
    }

    void ReadA(){
        itemsA.clear();
        Cursor cursor = db.SelectAll(1);
        if(cursor.getCount() == 0){
            Toast.makeText(AddData.this, "No Data.", Toast.LENGTH_SHORT).show();
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
        itemsB.clear();
        Cursor cursor = db.SelectAll(2);
        if(cursor.getCount() == 0){
            Toast.makeText(AddData.this, "No Data.", Toast.LENGTH_SHORT).show();
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
        itemsC.clear();
        Cursor cursor = db.SelectAll(3);
        if(cursor.getCount() == 0){
            Toast.makeText(AddData.this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                ClassC tmpItem = new ClassC(0, "", 0,"", "", "");
                tmpItem.set_id(cursor.getInt(0));
                tmpItem.setTitle(cursor.getString(1));
                tmpItem.setParent(cursor.getInt(2));
                tmpItem.setImg(cursor.getString(3));
                tmpItem.setDESC(cursor.getString(4));
                tmpItem.setPrice(cursor.getString(5));
                itemsC.add(tmpItem);
            }
        }
    }

    void updateRow(){
        int id       = Integer.parseInt(idTv.getText().toString().trim());
        String title = titleTv.getText().toString().trim();
        int parent   = Integer.parseInt(parentTv.getText().toString().trim());
        String img   = imgTv.getText().toString().trim();
        String desc = descTv.getText().toString().trim();
        String price = priceTv.getText().toString().trim();

        int radChosen = 0;

        if (aRadioBtn.isChecked()){
            radChosen = 1;
        } else if (bRadioBtn.isChecked()){
            radChosen = 2;
        } else if (cRadioBtn.isChecked()){
            radChosen = 3;
        }

        db.UpdateRow(radChosen, id, title, parent, img, desc, price);

    }


}