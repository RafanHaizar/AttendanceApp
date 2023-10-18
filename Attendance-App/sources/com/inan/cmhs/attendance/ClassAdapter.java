package com.inan.cmhs.attendance;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassViewHolder> {
    ArrayList<ClassItems> classItems;
    Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public ClassAdapter(Context context2, ArrayList<ClassItems> classItems2) {
        this.context = context2;
        this.classItems = classItems2;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name;
        TextView section;

        public ClassViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(C1397R.C1400id.class_edt);
            this.section = (TextView) itemView.findViewById(C1397R.C1400id.section_edt);
            itemView.setOnClickListener(new ClassAdapter$ClassViewHolder$$ExternalSyntheticLambda0(this, onItemClickListener));
            itemView.setOnCreateContextMenuListener(this);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$new$0$com-inan-cmhs-attendance-ClassAdapter$ClassViewHolder */
        public /* synthetic */ void mo25025x8fee62a2(OnItemClickListener onItemClickListener, View v) {
            onItemClickListener.OnClick(getAdapterPosition());
        }

        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(), 0, 0, "Edit");
            contextMenu.add(getAdapterPosition(), 1, 0, "Delete");
        }
    }

    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1397R.C1401layout.class_item, parent, false), this.onItemClickListener);
    }

    public void onBindViewHolder(ClassViewHolder holder, int position) {
        holder.name.setText(this.classItems.get(position).getName());
        holder.section.setText("Section: " + this.classItems.get(position).getSection());
    }

    public int getItemCount() {
        return this.classItems.size();
    }
}
