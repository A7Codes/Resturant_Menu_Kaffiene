package com.a7codes.menu.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.Classes.DBHelper;
import com.a7codes.menu.Classes.ImgHelper;
import com.a7codes.menu.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Details extends AppCompatActivity {

    //Views
    ImageView img;
    TextView title;
    TextView price;
    TextView ingredients;

    //Variables
    int receivedID = 0;
    DBHelper db;
    ArrayList<ClassC> itemsC = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Objects.requireNonNull(getSupportActionBar()).hide();
        db = new DBHelper(this);
        Assigner();
        ReceiveData();

    }

    private void Assigner(){
        img   = findViewById(R.id.deImg);
        title = findViewById(R.id.deTitle);
        price = findViewById(R.id.dePrice);
        ingredients = findViewById(R.id.deIng);
    }

    private void ReceiveData(){
        Intent intent = getIntent();

        receivedID = intent.getIntExtra("rID", 0);
        if (receivedID != 0){
            ReadC();
            for(int i = 0; i < itemsC.size(); i++){
                if(itemsC.get(i).get_id() == receivedID){
                    fillData(itemsC.get(i));
                }
            }
        }

    }

    void fillData(ClassC item){
        img.setImageBitmap(readBitmapImageFromDisk(item.getImg()));
        title.setText(item.getTitle());
        ingredients.setText(item.getDESC());

        DecimalFormat df = new DecimalFormat("#,###,###");
        String formattedPrice = df.format(Integer.parseInt(item.getPrice()));
        price.setText(formattedPrice + "  " + "د.ع.");

    }

    void ReadC(){
        Cursor cursor = db.SelectAll(3);
        if(cursor.getCount() == 0){
            Toast.makeText(Details.this, "No Data.", Toast.LENGTH_SHORT).show();
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

    //Reading Image File
    Bitmap readBitmapImageFromDisk(String bitm){
        File file = commonMenuDirPath("ITEMS");
        File imgFileJpg = new File(file + "/" + bitm + ".jpg");

        if(imgFileJpg.exists()){
            Bitmap bitmap = ImgHelper.decodeSampledBitmapFromResource(imgFileJpg.getPath(), 512, 512);
            return bitmap;
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logorounded);
            return bitmap;
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