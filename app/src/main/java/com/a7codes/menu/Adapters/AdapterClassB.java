package com.a7codes.menu.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.a7codes.menu.Classes.ClassB;
import com.a7codes.menu.Classes.ImgHelper;
import com.a7codes.menu.R;

import java.io.File;
import java.util.ArrayList;

public class AdapterClassB extends RecyclerView.Adapter<AdapterClassB.ViewHolder>{

    Context context;
    ArrayList<ClassB> items = new ArrayList<>();
    private ItemClickListener mClickListener;

    public AdapterClassB(Context context, ArrayList<ClassB> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.classbrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassB tmpB = items.get(position);
        holder.tv.setText(tmpB.getTitle().toUpperCase());
        holder.img.setImageBitmap(readBitmapImageFromDisk(items.get(position).getImg()));
    }

    private Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(),
                source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(0, 0, source.getWidth()
                - 0, source.getHeight() - 0), source.getWidth() / 2, source.getHeight() / 2, paint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    Bitmap readBitmapImageFromDisk(String bitm){
        File file = commonMenuDirPath("ITEMS");
        File imgFileJpg = new File(file + "/" + bitm + ".jpg");
        if(imgFileJpg.exists()){
            //Testing
            Bitmap bitmap = transform( ImgHelper.decodeSampledBitmapFromResource(imgFileJpg.getPath(), 256, 256) );
            //Testing
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

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Views
        ImageView img;
        TextView tv;
        //Variables

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.c2rImg);
            tv = itemView.findViewById(R.id.c2rTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
