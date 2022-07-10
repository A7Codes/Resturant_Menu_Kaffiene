package com.a7codes.menu.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.Classes.ImgHelper;
import com.a7codes.menu.R;

import java.io.File;
import java.util.ArrayList;

public class AdapterClassC extends BaseAdapter {

    Context context;
    ArrayList<ClassC> items = new ArrayList<>();
    LayoutInflater inflater = null;

    public AdapterClassC(Context context, ArrayList<ClassC> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null){
            view = inflater.inflate(R.layout.classcrow, null);
        }

        TextView nameTv = view.findViewById(R.id.c3gTv);
        ImageView Img = view.findViewById(R.id.c3gImg);

        nameTv.setText(items.get(i).getTitle());
        Img.setImageBitmap(transform(readBitmapImageFromDisk(items.get(i).getImg())));

        return view;
    }

//    #CBDCF8
private Bitmap transform(final Bitmap source) {
    final Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP));

    Bitmap output = Bitmap.createBitmap(source.getWidth(),
            source.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);
    canvas.drawRoundRect(new RectF(0, 0, source.getWidth(),
            source.getHeight()), 10, 10, paint);

    if (source != output) {
        source.recycle();
    }

    return output;
}

    Bitmap readBitmapImageFromDisk(String bitm){
        File file = commonMenuDirPath("ITEMS");
        File imgFileJpg = new File(file + "/" + bitm + ".jpg");
        File imgFileJpg2 = new File(file + "/" + bitm + ".jpeg");

        if(imgFileJpg.exists()){
            Bitmap bitmap = ImgHelper.decodeSampledBitmapFromResource(imgFileJpg.getPath(), 200, 200);
            return bitmap;
        } else if (imgFileJpg2.exists()){
            Bitmap bitmap = ImgHelper.decodeSampledBitmapFromResource(imgFileJpg2.getPath(), 200, 200);
            return bitmap;
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.mipmap.logorounded);
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
