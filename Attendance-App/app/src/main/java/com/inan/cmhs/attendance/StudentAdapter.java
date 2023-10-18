package com.inan.cmhs.attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    ArrayList<StudentItems> studentItems=null;
    Context context;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnClick(int position);
    }



    public StudentAdapter(Context context, ArrayList<StudentItems> studentItems) {
        this.context=context;
        this.studentItems = studentItems;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {



        TextView roll,name,status;
        CardView cardView;

        public StudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            roll=itemView.findViewById(R.id.roll);
            name=itemView.findViewById(R.id.name);
            status=itemView.findViewById(R.id.status);
            itemView.setOnClickListener(v->onItemClickListener.OnClick(getAdapterPosition()));
            cardView=itemView.findViewById(R.id.cardView2);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),0,0,"Edit");
            menu.add(getAdapterPosition(),1,0,"Delete");
        }
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);
        return new StudentAdapter.StudentViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.roll.setText(studentItems.get(position).getRoll());
        holder.name.setText(studentItems.get(position).getName());
        holder.status.setText(studentItems.get(position).getStatus());
        holder.cardView.setCardBackgroundColor(getColor(position));
    }

    private int getColor(int position) {
        String status=studentItems.get(position).getStatus();
        if(status.equals("P")){
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.Present)));
        }else if(status.equals("A")){
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.Absent)));
        }
        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.normal)));
    }


    @Override
    public int getItemCount() {
        if(studentItems!=null) {
            return studentItems.size();
        }else {
            return -1;
        }
    }
}
