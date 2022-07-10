package com.a7codes.menu.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.a7codes.menu.Adapters.AdapterClassB;
import com.a7codes.menu.Adapters.AdapterClassC;
import com.a7codes.menu.Classes.ClassA;
import com.a7codes.menu.Classes.ClassB;
import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.Classes.DBHelper;
import com.a7codes.menu.R;

import java.util.ArrayList;
import java.util.Objects;

public class Menu extends AppCompatActivity implements AdapterClassB.ItemClickListener{
    //Views
    RecyclerView recB;
    GridView grid;
    TextView viewTitle;

    //Variables
    ArrayList<ClassA> itemsA = new ArrayList<>();
    ArrayList<ClassB> itemsB = new ArrayList<>();
    ArrayList<ClassB> itemsBFiltered = new ArrayList<>();
    AdapterClassB adapterB;
    ArrayList<ClassC> itemsC = new ArrayList<>();
    ArrayList<ClassC> itemsCFiltered = new ArrayList<>();
    AdapterClassC adapterC;
    DBHelper db;
    int cb = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Objects.requireNonNull(getSupportActionBar()).hide();

        //Initiating & Reading DB
        db = new DBHelper(this);
        ReadA();
        ReadB();
        ReadC();

        //Fill Items
        bItemsFill();
        cItemsFill();

        //Set View's Title
        setViewTitle();
    }

    void setViewTitle(){
        viewTitle = findViewById(R.id.menuTitleTv);
        for (int i = 0; i < itemsA.size(); i++){
            if (itemsA.get(i).get_id() == cb){
                viewTitle.setText(itemsA.get(i).getTitle());
            }
        }
    }

    void ReadC(){
        Cursor cursor = db.SelectAll(3);
        if(cursor.getCount() == 0){
            Toast.makeText(Menu.this, "No Data.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Menu.this, "No Data.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Menu.this, "No Data.", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemClick(View view, int position) {
        if (itemsBFiltered.get(position).get_id() == 0){
            itemsC.clear();
            ReadC();
            itemsCFiltered.clear();
            for(int i = 0; i < itemsBFiltered.size(); i++){
                for(int j = 0; j < itemsC.size(); j++){
                    if (itemsC.get(j).getParent() == itemsBFiltered.get(i).get_id()){
                        itemsCFiltered.add(itemsC.get(j));
                    }
                }
            }
            AdapterClassC newAdapterC = new AdapterClassC(this, itemsCFiltered);
            grid.setAdapter(newAdapterC);

        } else {
            filterCAccordingToB(itemsBFiltered.get(position).get_id());
        }

    }

    void bItemsFill(){
        Intent intent = getIntent();
        cb = intent.getIntExtra("cB", 0);

        for (int i = 0; i < itemsB.size(); i++){
            if (itemsB.get(i).getParent() == cb || itemsB.get(i).get_id() == 0){
                itemsBFiltered.add(itemsB.get(i));
            }
        }

        //Setting Up Recycler View
        recB = findViewById(R.id.recB);
        recB.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterB = new AdapterClassB(Menu.this, itemsBFiltered);
        adapterB.setClickListener(this);
        recB.setAdapter(adapterB);

    }

    void cItemsFill(){
        itemsCFiltered.clear();

        for (int i = 0; i < itemsBFiltered.size(); i++){
            for (int j = 0; j < itemsC.size(); j++){
                if (itemsC.get(j).getParent() == itemsBFiltered.get(i).get_id()){
                    itemsCFiltered.add(itemsC.get(j));
                }
            }
        }

        grid = findViewById(R.id.GridC);
        adapterC = new AdapterClassC(Menu.this, itemsCFiltered);
        grid.setAdapter(adapterC);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Menu.this, Details.class );
                intent.putExtra("rID", itemsCFiltered.get(i).get_id());
                Log.d("Item C Sent ID //", "" + itemsCFiltered.get(i).get_id());
                startActivity(intent);
            }
        });

    }

    private void filterCAccordingToB(int B){
        itemsC.clear();
        itemsCFiltered.clear();
        ReadC();

        ArrayList<ClassC> newItemsC = new ArrayList<>();

        for (int i = 0; i < itemsC.size(); i++){
            if (itemsC.get(i).getParent() == B){
                newItemsC.add(itemsC.get(i));
            }
        }

        AdapterClassC newAdapterC = new AdapterClassC(this, newItemsC);
        itemsCFiltered = newItemsC;
        grid.setAdapter(newAdapterC);

    }



}