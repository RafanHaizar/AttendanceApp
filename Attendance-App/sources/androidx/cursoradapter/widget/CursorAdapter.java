package androidx.cursoradapter.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import androidx.cursoradapter.widget.CursorFilter;

public abstract class CursorAdapter extends BaseAdapter implements Filterable, CursorFilter.CursorFilterClient {
    @Deprecated
    public static final int FLAG_AUTO_REQUERY = 1;
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
    protected boolean mAutoRequery;
    protected ChangeObserver mChangeObserver;
    protected Context mContext;
    protected Cursor mCursor;
    protected CursorFilter mCursorFilter;
    protected DataSetObserver mDataSetObserver;
    protected boolean mDataValid;
    protected FilterQueryProvider mFilterQueryProvider;
    protected int mRowIDColumn;

    public abstract void bindView(View view, Context context, Cursor cursor);

    public abstract View newView(Context context, Cursor cursor, ViewGroup viewGroup);

    @Deprecated
    public CursorAdapter(Context context, Cursor c) {
        init(context, c, 1);
    }

    public CursorAdapter(Context context, Cursor c, boolean autoRequery) {
        init(context, c, autoRequery ? 1 : 2);
    }

    public CursorAdapter(Context context, Cursor c, int flags) {
        init(context, c, flags);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void init(Context context, Cursor c, boolean autoRequery) {
        init(context, c, autoRequery ? 1 : 2);
    }

    /* access modifiers changed from: package-private */
    public void init(Context context, Cursor c, int flags) {
        boolean z = false;
        if ((flags & 1) == 1) {
            flags |= 2;
            this.mAutoRequery = true;
        } else {
            this.mAutoRequery = false;
        }
        if (c != null) {
            z = true;
        }
        boolean cursorPresent = z;
        this.mCursor = c;
        this.mDataValid = cursorPresent;
        this.mContext = context;
        this.mRowIDColumn = cursorPresent ? c.getColumnIndexOrThrow("_id") : -1;
        if ((flags & 2) == 2) {
            this.mChangeObserver = new ChangeObserver();
            this.mDataSetObserver = new MyDataSetObserver();
        } else {
            this.mChangeObserver = null;
            this.mDataSetObserver = null;
        }
        if (cursorPresent) {
            ChangeObserver changeObserver = this.mChangeObserver;
            if (changeObserver != null) {
                c.registerContentObserver(changeObserver);
            }
            DataSetObserver dataSetObserver = this.mDataSetObserver;
            if (dataSetObserver != null) {
                c.registerDataSetObserver(dataSetObserver);
            }
        }
    }

    public Cursor getCursor() {
        return this.mCursor;
    }

    public int getCount() {
        Cursor cursor;
        if (!this.mDataValid || (cursor = this.mCursor) == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public Object getItem(int position) {
        Cursor cursor;
        if (!this.mDataValid || (cursor = this.mCursor) == null) {
            return null;
        }
        cursor.moveToPosition(position);
        return this.mCursor;
    }

    public long getItemId(int position) {
        Cursor cursor;
        if (!this.mDataValid || (cursor = this.mCursor) == null || !cursor.moveToPosition(position)) {
            return 0;
        }
        return this.mCursor.getLong(this.mRowIDColumn);
    }

    public boolean hasStableIds() {
        return true;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (!this.mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        } else if (this.mCursor.moveToPosition(position)) {
            if (convertView == null) {
                v = newView(this.mContext, this.mCursor, parent);
            } else {
                v = convertView;
            }
            bindView(v, this.mContext, this.mCursor);
            return v;
        } else {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v;
        if (!this.mDataValid) {
            return null;
        }
        this.mCursor.moveToPosition(position);
        if (convertView == null) {
            v = newDropDownView(this.mContext, this.mCursor, parent);
        } else {
            v = convertView;
        }
        bindView(v, this.mContext, this.mCursor);
        return v;
    }

    public View newDropDownView(Context context, Cursor cursor, ViewGroup parent) {
        return newView(context, cursor, parent);
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == this.mCursor) {
            return null;
        }
        Cursor oldCursor = this.mCursor;
        if (oldCursor != null) {
            ChangeObserver changeObserver = this.mChangeObserver;
            if (changeObserver != null) {
                oldCursor.unregisterContentObserver(changeObserver);
            }
            DataSetObserver dataSetObserver = this.mDataSetObserver;
            if (dataSetObserver != null) {
                oldCursor.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.mCursor = newCursor;
        if (newCursor != null) {
            ChangeObserver changeObserver2 = this.mChangeObserver;
            if (changeObserver2 != null) {
                newCursor.registerContentObserver(changeObserver2);
            }
            DataSetObserver dataSetObserver2 = this.mDataSetObserver;
            if (dataSetObserver2 != null) {
                newCursor.registerDataSetObserver(dataSetObserver2);
            }
            this.mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            this.mDataValid = true;
            notifyDataSetChanged();
        } else {
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            notifyDataSetInvalidated();
        }
        return oldCursor;
    }

    public CharSequence convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }

    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        FilterQueryProvider filterQueryProvider = this.mFilterQueryProvider;
        if (filterQueryProvider != null) {
            return filterQueryProvider.runQuery(constraint);
        }
        return this.mCursor;
    }

    public Filter getFilter() {
        if (this.mCursorFilter == null) {
            this.mCursorFilter = new CursorFilter(this);
        }
        return this.mCursorFilter;
    }

    public FilterQueryProvider getFilterQueryProvider() {
        return this.mFilterQueryProvider;
    }

    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.mFilterQueryProvider = filterQueryProvider;
    }

    /* access modifiers changed from: protected */
    public void onContentChanged() {
        Cursor cursor;
        if (this.mAutoRequery && (cursor = this.mCursor) != null && !cursor.isClosed()) {
            this.mDataValid = this.mCursor.requery();
        }
    }

    private class ChangeObserver extends ContentObserver {
        ChangeObserver() {
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean selfChange) {
            CursorAdapter.this.onContentChanged();
        }
    }

    private class MyDataSetObserver extends DataSetObserver {
        MyDataSetObserver() {
        }

        public void onChanged() {
            CursorAdapter.this.mDataValid = true;
            CursorAdapter.this.notifyDataSetChanged();
        }

        public void onInvalidated() {
            CursorAdapter.this.mDataValid = false;
            CursorAdapter.this.notifyDataSetInvalidated();
        }
    }
}
