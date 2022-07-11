package com.a7codes.menu.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.a7codes.menu.Adapters.AdapterClassD;
import com.a7codes.menu.Classes.ClassA;
import com.a7codes.menu.Classes.ClassB;
import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.Classes.DBHelper;
import com.a7codes.menu.R;

import java.util.ArrayList;

public class EditData extends AppCompatActivity implements AdapterClassD.ItemClickListener{

    //Views
    RecyclerView recD;
    RadioGroup rgD;
    RadioButton radA;
    RadioButton radB;
    RadioButton radC;

    //Variables
    ArrayList<ClassA> itemsA = new ArrayList<>();
    ArrayList<ClassB> itemsB = new ArrayList<>();
    ArrayList<ClassC> itemsC = new ArrayList<>();
    DBHelper db;
    AdapterClassD adapter;
    int mode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        Assigner();

        //1
        db = new DBHelper(this);
        radA.setChecked(true);

        ReadA();

        recD.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterClassD(EditData.this, itemsA, itemsB, itemsC, 1);
        adapter.setClickListener(this);
        recD.setAdapter(adapter);


        //2
        rgD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                itemsA.clear();
                itemsB.clear();
                itemsC.clear();

                checkWhoChanged();
            }
        });

        //TODO: UPDATE

    }

    private void Assigner(){
        recD = findViewById(R.id.recD);
        rgD = findViewById(R.id.edClassRG);
        radA = findViewById(R.id.editRadBtnA);
        radB = findViewById(R.id.editRadBtnB);
        radC = findViewById(R.id.editRadBtnC);
    }

    void ReadA(){
        Cursor cursor = db.SelectAll(1);
        if(cursor.getCount() == 0){
            Toast.makeText(EditData.this, "No Data.", Toast.LENGTH_SHORT).show();
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
        Cursor cursor = db.SelectAll(2);
        if(cursor.getCount() == 0){
            Toast.makeText(EditData.this, "No Data.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(EditData.this, "No Data.", Toast.LENGTH_SHORT).show();
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

    void checkWhoChanged(){
        if (radA.isChecked()){
            ReadA();
            mode = 1;
            fillRecD(1);
        } else if (radB.isChecked()){
            ReadB();
            mode = 2;
            fillRecD(2);
        } else if (radC.isChecked()){
            ReadC();
            mode = 3;
            fillRecD(3);
        }
    }

    void fillRecD(int mode){
        adapter.notifyDataSetChanged();
        adapter = new AdapterClassD(EditData.this, itemsA, itemsB, itemsC, mode);
        recD.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
        recD.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        if (view.getId() == R.id.c4gBtnUpd){
            Intent intent = new Intent(EditData.this, AddData.class);
            intent.putExtra("mode",mode);
            switch (mode){
                case 1:
                    intent.putExtra("cfw",itemsA.get(position).get_id());
                    break;
                case 2:
                    intent.putExtra("cfw",itemsB.get(position).get_id());
                    break;
                case 3:
                    intent.putExtra("cfw",itemsC.get(position).get_id());
                    break;
            }
            startActivity(intent);
        } else {
        if (radA.isChecked()){
            db.DeleteRow(1, itemsA.get(position).get_id());
            itemsA.remove(position);
            adapter.notifyItemRemoved(position);
        } else if (radB.isChecked()){
            db.DeleteRow(2, itemsB.get(position).get_id());
            itemsB.remove(position);
            adapter.notifyItemRemoved(position);
        } else if (radC.isChecked()){
            db.DeleteRow(3, itemsC.get(position).get_id());
            itemsC.remove(position);
            adapter.notifyItemRemoved(position);
            }
        }

    }
}