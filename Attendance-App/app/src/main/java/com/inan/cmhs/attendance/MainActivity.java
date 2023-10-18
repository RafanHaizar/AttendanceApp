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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Static Vars
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    ArrayList<ClassItems> classItems = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    Spinner name, section;
    TextView title, Section;
    ImageView back, save;
    DBHelper dbHelper;
    AutoCompleteTextView acTextView;
    Button cancel, add;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declaration of sql
        dbHelper = new DBHelper(this);
        loadDATA();
        //findViewByID
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerview);
        title = findViewById(R.id.title_tool);
        Section = findViewById(R.id.section_tool);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        //toolbar set
        title.setText("Cumilla Modern High School");
        Section.setText("Attendance Management");
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        //RecyclerView
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        classAdapter = new ClassAdapter(this, classItems);

        recyclerView.setAdapter(classAdapter);
        floatingActionButton.setOnClickListener(view -> fab());
        classAdapter.setOnItemClickListener(position -> gotoItem(position));
    }

    @SuppressLint("Range")
    private void loadDATA() {
        Cursor cursor = dbHelper.cursor();
        classItems.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(dbHelper.C_ID));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper.ClassNameKey));
            String section = cursor.getString(cursor.getColumnIndex(dbHelper.SectionNameKey));
            classItems.add(new ClassItems(id, name, section));
        }

    }

    private void gotoItem(int position) {
        Intent i = new Intent(this, StudentActivity.class);
        i.putExtra("className", classItems.get(position).getName());
        i.putExtra("sectionName", classItems.get(position).getSection());
        i.putExtra("position", position);
        i.putExtra("cid", classItems.get(position).getId());
        startActivity(i);
    }

    @SuppressLint("MissingInflatedId")
    private void fab() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.classdialog, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        name = view.findViewById(R.id.classes);
        List<String> namesList = Arrays.asList("Class 6", "Class 7", "Class 8", "Class 9", "Class 10");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name.setAdapter(adapter);
        name.setOnItemSelectedListener(this);

        section = view.findViewById(R.id.Section_ClassDialog);
        List<String> sectionList = Arrays.asList("A", "B", "C","D","E","F","G","H","I","J");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sectionList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section.setAdapter(adapter2);
        section.setOnItemSelectedListener(this);

        cancel = view.findViewById(R.id.cancel_class);
        add = view.findViewById(R.id.create_class);
        cancel.setOnClickListener(v -> alertDialog.dismiss());
        add.setOnClickListener(v -> {
            add(name.getSelectedItem().toString(), section.getSelectedItem().toString());
            alertDialog.dismiss();
        });
    }

    private void add(String name, String section) {
        long cid = dbHelper.addClass(name, section);
        ClassItems classItems1 = new ClassItems((int) cid, name, section);
        classItems.add(classItems1);
        classAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            updateClass(item.getGroupId());
        } else {
            deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteClass(int pos) {
        dbHelper.removeClass(classItems.get(pos).getId());
        classItems.remove(pos);
        classAdapter.notifyItemRemoved((pos));
    }

    private void updateClass(int itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edt_class, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        name = view.findViewById(R.id.Name_classDialogedt);
        List<String> namesList = Arrays.asList("Class 6", "Class 7", "Class 8", "Class 9", "Class 10");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name.setAdapter(adapter);
        name.setOnItemSelectedListener(this);

        section = view.findViewById(R.id.Section_ClassDialogedt);
        List<String> sectionList = Arrays.asList("A", "B", "C","D","E","F","G","H","I","J");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sectionList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section.setAdapter(adapter2);
        section.setOnItemSelectedListener(this);

        Button cancel = view.findViewById(R.id.cancel_classedt);
        Button add = view.findViewById(R.id.edt_class);

        cancel.setOnClickListener(v -> alertDialog.dismiss());
        add.setOnClickListener(v -> {
            editClass(itemId, name.getSelectedItem().toString(), section.getSelectedItem().toString());
            alertDialog.dismiss();
        });
    }


    private void editClass(int position, String name, String section) {
        try {
            classItems.get(position).setName(name);
            classItems.get(position).setSection(section);
            classAdapter.notifyItemChanged(position);
            dbHelper.editclass(classItems.get(position).getId(), name, section);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}