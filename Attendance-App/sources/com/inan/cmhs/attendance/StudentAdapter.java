package com.inan.cmhs.attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.svg.SvgConstants;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    Context context;
    private OnItemClickListener onItemClickListener;
    ArrayList<StudentItems> studentItems = null;

    public interface OnItemClickListener {
        void OnClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public StudentAdapter(Context context2, ArrayList<StudentItems> studentItems2) {
        this.context = context2;
        this.studentItems = studentItems2;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        CardView cardView;
        TextView name;
        TextView roll;
        TextView status;

        public StudentViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.roll = (TextView) itemView.findViewById(C1397R.C1400id.roll);
            this.name = (TextView) itemView.findViewById(C1397R.C1400id.name);
            this.status = (TextView) itemView.findViewById(C1397R.C1400id.status);
            itemView.setOnClickListener(new StudentAdapter$StudentViewHolder$$ExternalSyntheticLambda0(this, onItemClickListener));
            this.cardView = (CardView) itemView.findViewById(C1397R.C1400id.cardView2);
            itemView.setOnCreateContextMenuListener(this);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$new$0$com-inan-cmhs-attendance-StudentAdapter$StudentViewHolder */
        public /* synthetic */ void mo25105x945e0882(OnItemClickListener onItemClickListener, View v) {
            onItemClickListener.OnClick(getAdapterPosition());
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 0, 0, "Edit");
            menu.add(getAdapterPosition(), 1, 0, "Delete");
        }
    }

    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1397R.C1401layout.student_item, parent, false), this.onItemClickListener);
    }

    public void onBindViewHolder(StudentViewHolder holder, int position) {
        holder.roll.setText(this.studentItems.get(position).getRoll());
        holder.name.setText(this.studentItems.get(position).getName());
        holder.status.setText(this.studentItems.get(position).getStatus());
        holder.cardView.setCardBackgroundColor(getColor(position));
    }

    private int getColor(int position) {
        String status = this.studentItems.get(position).getStatus();
        if (status.equals(StandardRoles.f1511P)) {
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this.context, C1397R.C1398color.Present)));
        }
        if (status.equals(SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A)) {
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this.context, C1397R.C1398color.Absent)));
        }
        return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this.context, C1397R.C1398color.normal)));
    }

    public int getItemCount() {
        ArrayList<StudentItems> arrayList = this.studentItems;
        if (arrayList != null) {
            return arrayList.size();
        }
        return -1;
    }
}
