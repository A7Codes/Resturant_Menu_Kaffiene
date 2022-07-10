package com.a7codes.menu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.a7codes.menu.Classes.ClassA;
import com.a7codes.menu.Classes.ClassB;
import com.a7codes.menu.Classes.ClassC;
import com.a7codes.menu.R;
import java.util.ArrayList;

public class AdapterClassD extends RecyclerView.Adapter<AdapterClassD.ViewHolder>{
    Context context;
    ArrayList<ClassA> itemsA = new ArrayList<>();
    ArrayList<ClassB> itemsB = new ArrayList<>();
    ArrayList<ClassC> itemsC = new ArrayList<>();
    private ItemClickListener mClickListener;
    int mode = 0;

    public AdapterClassD(Context context, ArrayList<ClassA> itemsA, ArrayList<ClassB> itemsB, ArrayList<ClassC> itemsC, int mode) {
        this.context = context;
        this.itemsA = itemsA;
        this.itemsB = itemsB;
        this.itemsC = itemsC;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.classdrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (mode) {
            case 1:
                ClassA tmpA = itemsA.get(position);
                holder.tvID.setText(String.valueOf(tmpA.get_id()));
                holder.tvTi.setText(tmpA.getTitle());
                holder.tvPa.setText(String.valueOf(tmpA.getParent()));
                break;

            case 2:
                ClassB tmpB = itemsB.get(position);
                holder.tvID.setText(String.valueOf(tmpB.get_id()));
                holder.tvTi.setText(tmpB.getTitle());
                holder.tvPa.setText(String.valueOf(tmpB.getParent()));
                break;

            case 3:
                ClassC tmpC = itemsC.get(position);
                holder.tvID.setText(String.valueOf(tmpC.get_id()));
                holder.tvTi.setText(tmpC.getTitle());
                holder.tvPa.setText(String.valueOf(tmpC.getParent()));
                break;

            default:
                holder.tvID.setText("0");
                holder.tvTi.setText("No Data");
                break;
        }


    }

    @Override
    public int getItemCount() {
        switch (mode){
            case 1:
                return itemsA.size();

            case 2:
                return itemsB.size();

            case 3:
                return itemsC.size();
            default:
                return 1;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvID;
        TextView tvTi;
        TextView tvPa;
        Button   btnD;
        Button   btnU;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.c4gTvID);
            tvTi = itemView.findViewById(R.id.c4gTvTitle);
            tvPa = itemView.findViewById(R.id.c4gTvParent);
            btnD = itemView.findViewById(R.id.c4gBtnDel);
            btnU = itemView.findViewById(R.id.c4gBtnUpd);

            btnD.setOnClickListener(this);
            btnU.setOnClickListener(this);

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
