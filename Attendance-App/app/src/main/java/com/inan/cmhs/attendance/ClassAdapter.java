package com.inan.cmhs.attendance;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    ArrayList<ClassItems> classItems;
    Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnClick(int position);
    }



    public ClassAdapter(Context context, ArrayList<ClassItems> classItems) {
        this.context=context;
        this.classItems = classItems;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name,section;
        public ClassViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            name=itemView.findViewById(R.id.class_edt);
            section=itemView.findViewById(R.id.section_edt);
            itemView.setOnClickListener(v->onItemClickListener.OnClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(),0,0,"Edit");
            contextMenu.add(getAdapterPosition(),1,0,"Delete");
        }

    }
    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,parent,false);
        return new ClassViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.name.setText(classItems.get(position).getName());
        holder.section.setText("Section: "+classItems.get(position).getSection());
    }

    @Override
    public int getItemCount() {
        return classItems.size();
    }



}
