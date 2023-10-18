package com.inan.cmhs.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    TextView title,section;
    MyCalendar myCalendar=new MyCalendar();
    String intent_title,intent_section;
    int position;
    EditText roll,Name;
    StudentAdapter studentAdapter;
    Button cancel,add;
    ArrayList<StudentItems> studentItems=new ArrayList<>();
    DBHelper dbHelper=new DBHelper(this);
    TextView dialoge_title;
    FloatingActionButton floatingActionButton,fab_date,fab_sheet;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public  long cid;
    ImageView back,save;
    @SuppressLint({"ResourceType", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        //recyclerview
        recyclerView=findViewById(R.id.studentrecycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        studentAdapter=new StudentAdapter(this,studentItems);
        recyclerView.setAdapter(studentAdapter);
        //toolbar
        title=findViewById(R.id.title_tool);
        section=findViewById(R.id.section_tool);
        back=findViewById(R.id.back);
        save=findViewById(R.id.save);
        //other
        Intent intent=new Intent();
        intent=getIntent();
        dialoge_title=findViewById(R.id.title_of_classdialogedt);
        intent_title = intent.getStringExtra("className");
        intent_section = intent.getStringExtra("sectionName");
        cid=intent.getIntExtra("cid",-1);
        position = intent.getIntExtra("position", -1);
        if(intent_title!=null&&intent_section!=null) {
            title.setText(intent_title);
            section.setText("Section: " + intent_section);
        }
        Load();
        loadStatus();
        back.setOnClickListener(v->onBackPressed());
        save.setOnClickListener(v->saveStatus());
        floatingActionButton=findViewById(R.id.fab_student);
        fab_date=findViewById(R.id.fab_date);
        fab_sheet=findViewById(R.id.fab_sheet);
        fab_sheet.setOnClickListener(v->sheet());
        floatingActionButton.setOnClickListener(v->showAddStudentDialog());
        fab_date.setOnClickListener(v->showDateDialog());
        studentAdapter.setOnItemClickListener(position->makechange(position));
    }

    private void sheet() {
        long[] idArray = new long[studentItems.size()];
        String[] nameArray = new String[studentItems.size()];
        int[] rollArray = new int[studentItems.size()];
        for(int i=0;i<idArray.length;i++){
            idArray[i] = studentItems.get(i).getSid();
        }
        for(int i=0;i<nameArray.length;i++){
            nameArray[i] = studentItems.get(i).getName();
        }
        for(int i=0;i<rollArray.length;i++) {
            rollArray[i] = Integer.parseInt(studentItems.get(i).getRoll());
        }
        Intent i = new Intent(this,SheetActivity.class);
        i.putExtra("cid",cid);
        System.out.println("Intent Here "+cid);
        i.putExtra("idArray",idArray);
        i.putExtra("nameArray",nameArray);
        i.putExtra("rollArray",rollArray);
        startActivity(i);
    }

    private void saveStatus() {
        for(StudentItems studentItem: studentItems){
            String status= studentItem.getStatus();
            long val=dbHelper.addStatus(studentItem.getSid(),cid, myCalendar.getData(),status);
            if(status != "P") status="A";
            Toast.makeText(this,"Attendance Succsesfully Added",Toast.LENGTH_SHORT);
            if(val==-1) dbHelper.deleteStatus(studentItem.getSid(),cid, myCalendar.getData());dbHelper.updateStatus(studentItem.getSid(), myCalendar.getData(),status);
        }
    }

    private void loadStatus() {
        for(StudentItems studentItems1: studentItems){
            String status=dbHelper.getStatus(studentItems1.getSid(), myCalendar.getData());
            System.out.println("Checkpoint No. 0002 loadStatus()");
            System.out.println(status);
            if(status!=null) studentItems1.setStatus(status);
        }
        studentAdapter.notifyDataSetChanged();
    }
    private void showDateDialog() {
        myCalendar.show(getSupportFragmentManager(),"");
        String mota= (String) section.getText();
        if(mota.contains("|")) Toast.makeText(this,"You already have a date specified",Toast.LENGTH_LONG);
        else myCalendar.setOnCalendarClickListener(this::OnOKClicked);
    }
    private void OnOKClicked(int year, int month, int day) {
        String bt=myCalendar.getData();
        myCalendar.SetData(year, month, day);
        myCalendar.SetData(year, month, day);
    }
    private void OnOKClickedtwo(int year, int month, int day) {
        String bt=myCalendar.getData();
        myCalendar.SetData(year, month, day);
        myCalendar.SetData(year, month, day);
        String mota= (String) section.getText();
        for(;mota.contains("|");) {
            mota.substring(0, mota.length() - 1);
        }
        section.setText(mota+" | " + bt);
    }

    @SuppressLint("Range")
    public void Load() {
        Cursor cursor = dbHelper.cursor_student(cid);
        studentItems.clear();
        while (cursor.moveToNext()) {
            long sid = cursor.getLong(cursor.getColumnIndex(dbHelper.S_ID));
            int roll = cursor.getInt(cursor.getColumnIndex(dbHelper.RollKey));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper.StudentNameKey));
            studentItems.add(new StudentItems(sid, String.valueOf(roll),name));
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==0){
            updateStudent(item.getGroupId());
        }else{
            deleteStudent(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteStudent(int groupId) {
        dbHelper.removeStudent(studentItems.get(groupId).getSid());
        studentItems.remove(groupId);
        studentAdapter.notifyItemRemoved(groupId);
    }
    @SuppressLint("MissingInflatedId")
    public void updateStudent(int itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edt_student, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        roll = view.findViewById(R.id.Roll_studentdialogedt);
        Name = view.findViewById(R.id.Name_studentdialogedt);
        cancel = view.findViewById(R.id.cancel_studentedt);
        add = view.findViewById(R.id.edt_student);
        roll.setText(studentItems.get(itemId).getRoll());
        Name.setText(studentItems.get(itemId).getName());
        try {
            bool(String.valueOf(studentItems.get(position - 1).getRoll()));
        }catch (Exception e){
            e.printStackTrace();
        }
        cancel.setOnClickListener(v -> alertDialog.dismiss());
        add.setOnClickListener(v->{editClass(itemId,roll.getText().toString(),Name.getText().toString());alertDialog.dismiss();});
    }

    private void editClass(int position,String roll, String Name) {
        try {
            studentItems.get(position).setRoll(roll);
            studentItems.get(position).setName(Name);
            studentAdapter.notifyItemChanged(position);
            dbHelper.editstudent(studentItems.get(position).getSid(), Integer.parseInt(roll), Name);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void makechange(int position) {
        String status=studentItems.get(position).getStatus();
        if(status.equals("P")) status="A";
        else status="P";
        studentItems.get(position).setStatus(status);
        studentAdapter.notifyDataSetChanged();
    }


    @SuppressLint("MissingInflatedId")
    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.studentdialog,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        roll=view.findViewById(R.id.Roll_studentdialog);
        String i =(roll.getText().toString());
        Name=view.findViewById(R.id.Name_studentdialoge);
        cancel=view.findViewById(R.id.cancel_student);
        add=view.findViewById(R.id.create_student);
        cancel.setOnClickListener(v->alertDialog.dismiss());
        add.setOnClickListener(v->{add();alertDialog.dismiss();bool(i);});

    }

    private void bool(String i) {
        try{
            int pintu = Integer.parseInt(i)+1;
            roll.setText(pintu);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void add() {
        String vroll=roll.getText().toString();
        String vname=Name.getText().toString();
        long sid = dbHelper.addStudent(cid,Integer.parseInt(vroll),vname);
        studentItems.add(new StudentItems(sid,vroll,vname));
    }

}