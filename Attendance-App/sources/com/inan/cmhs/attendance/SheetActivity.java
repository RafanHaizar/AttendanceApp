package com.inan.cmhs.attendance;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SheetActivity extends AppCompatActivity {
    private ArrayAdapter adapter;
    ImageView back;
    private long cid;
    Intent intent;
    private ArrayList listItems = new ArrayList();
    ImageView save;
    TextView section;
    private ListView sheetList;
    TextView title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1397R.C1401layout.activity_sheet);
        this.title = (TextView) findViewById(C1397R.C1400id.title_tool);
        this.section = (TextView) findViewById(C1397R.C1400id.section_tool);
        this.back = (ImageView) findViewById(C1397R.C1400id.back);
        this.save = (ImageView) findViewById(C1397R.C1400id.save);
        this.intent = getIntent();
        this.title.setText("List of Attendance Months");
        this.section.setVisibility(4);
        this.save.setVisibility(4);
        this.back.setOnClickListener(new SheetActivity$$ExternalSyntheticLambda0(this));
        this.cid = this.intent.getLongExtra("cid", -1);
        System.out.println(this.cid);
        this.sheetList = (ListView) findViewById(C1397R.C1400id.sheetList);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, C1397R.C1401layout.sheet_list, C1397R.C1400id.data_list_item, this.listItems);
        this.adapter = arrayAdapter;
        this.sheetList.setAdapter(arrayAdapter);
        loadListItems();
        this.sheetList.setOnItemClickListener(new SheetActivity$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$0$com-inan-cmhs-attendance-SheetActivity  reason: not valid java name */
    public /* synthetic */ void m1329lambda$onCreate$0$cominancmhsattendanceSheetActivity(View v) {
        onBackPressed();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$1$com-inan-cmhs-attendance-SheetActivity  reason: not valid java name */
    public /* synthetic */ void m1330lambda$onCreate$1$cominancmhsattendanceSheetActivity(AdapterView parent, View view, int position, long id) {
        openSheetActivity(position);
    }

    private void openSheetActivity(int position) {
        Intent intent2 = getIntent();
        this.intent = intent2;
        long[] idArray = intent2.getLongArrayExtra("idArray");
        int[] rollArray = this.intent.getIntArrayExtra("rollArray");
        String[] nameArray = this.intent.getStringArrayExtra("nameArray");
        Intent i = new Intent(this, SheetHalkhata.class);
        i.putExtra("idArray", idArray);
        i.putExtra("nameArray", nameArray);
        i.putExtra("rollArray", rollArray);
        i.putExtra("month", String.valueOf(this.listItems.get(position)));
        startActivity(i);
    }

    private void loadListItems() {
        Cursor cursor = new DBHelper(this).getMonths(this.cid);
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.DateKey));
            String mayhem = "";
            if (date.substring(3, 5).contains("01")) {
                mayhem = "January";
            } else if (date.substring(3, 5).contains("02")) {
                mayhem = "February";
            } else if (date.substring(3, 5).contains("03")) {
                mayhem = "March";
            } else if (date.substring(3, 5).contains("04")) {
                mayhem = "April";
            } else if (date.substring(3, 5).contains("05")) {
                mayhem = "May";
            } else if (date.substring(3, 5).contains("06")) {
                mayhem = "June";
            } else if (date.substring(3, 5).contains("07")) {
                mayhem = "July";
            } else if (date.substring(3, 5).contains("08")) {
                mayhem = "August";
            } else if (date.substring(3, 5).contains("09")) {
                mayhem = "September";
            } else if (date.substring(3, 5).contains("10")) {
                mayhem = "October";
            } else if (date.substring(3, 5).contains("11")) {
                mayhem = "November";
            } else if (date.substring(3, 5).contains("12")) {
                mayhem = "December";
            }
            this.listItems.add(mayhem + " " + date.substring(6));
        }
    }
}
