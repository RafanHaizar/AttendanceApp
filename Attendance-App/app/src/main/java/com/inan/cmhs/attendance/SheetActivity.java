package com.inan.cmhs.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SheetActivity extends AppCompatActivity {
    private ListView sheetList;
    private ArrayAdapter adapter;
    private long cid;
    TextView title,section;
    Intent intent;
    ImageView back,save;
    private ArrayList listItems=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);
        title=findViewById(R.id.title_tool);
        section=findViewById(R.id.section_tool);
        back=findViewById(R.id.back);
        save=findViewById(R.id.save);
        intent = getIntent();
        title.setText("List of Attendance Months");
        section.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        back.setOnClickListener(v->onBackPressed());
        cid=intent.getLongExtra("cid", -1);
        System.out.println(cid);
        sheetList=findViewById(R.id.sheetList);
        adapter=new ArrayAdapter(this,R.layout.sheet_list,R.id.data_list_item,listItems);
        sheetList.setAdapter(adapter);
        loadListItems();
        sheetList.setOnItemClickListener((parent, view, position, id)->openSheetActivity(position));
    }

    private void openSheetActivity(int position) {
        intent = getIntent();
        long[] idArray=intent.getLongArrayExtra("idArray");
        int[] rollArray=intent.getIntArrayExtra("rollArray");
        String[] nameArray=intent.getStringArrayExtra("nameArray");
        Intent i = new Intent(this,SheetHalkhata.class);
        i.putExtra("idArray",idArray);
        i.putExtra("nameArray",nameArray);
        i.putExtra("rollArray",rollArray);
        i.putExtra("month",String.valueOf(listItems.get(position)));
//        i.putExtra("subtext",)
        startActivity(i);
    }

    private void loadListItems() {
        Cursor cursor=new DBHelper(this).getMonths(cid);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex(DBHelper.DateKey));
            String mayhem="";
            if(date.substring(3,5).contains("01"))      mayhem="January";
            else if(date.substring(3,5).contains("02")) mayhem="February";
            else if(date.substring(3,5).contains("03")) mayhem="March";
            else if(date.substring(3,5).contains("04")) mayhem="April";
            else if(date.substring(3,5).contains("05")) mayhem="May";
            else if(date.substring(3,5).contains("06")) mayhem="June";
            else if(date.substring(3,5).contains("07")) mayhem="July";
            else if(date.substring(3,5).contains("08")) mayhem="August";
            else if(date.substring(3,5).contains("09")) mayhem="September";
            else if(date.substring(3,5).contains("10")) mayhem="October";
            else if(date.substring(3,5).contains("11")) mayhem="November";
            else if(date.substring(3,5).contains("12")) mayhem="December";

            listItems.add(mayhem+" "+date.substring(6));
        }
    }
}