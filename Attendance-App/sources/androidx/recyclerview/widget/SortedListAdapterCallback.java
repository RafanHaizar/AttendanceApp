package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

public abstract class SortedListAdapterCallback<T2> extends SortedList.Callback<T2> {
    final RecyclerView.Adapter mAdapter;

    public SortedListAdapterCallback(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    public void onInserted(int position, int count) {
        this.mAdapter.notifyItemRangeInserted(position, count);
    }

    public void onRemoved(int position, int count) {
        this.mAdapter.notifyItemRangeRemoved(position, count);
    }

    public void onMoved(int fromPosition, int toPosition) {
        this.mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    public void onChanged(int position, int count) {
        this.mAdapter.notifyItemRangeChanged(position, count);
    }

    public void onChanged(int position, int count, Object payload) {
        this.mAdapter.notifyItemRangeChanged(position, count, payload);
    }
}
