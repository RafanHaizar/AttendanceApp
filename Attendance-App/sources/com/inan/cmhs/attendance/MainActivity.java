package com.inan.cmhs.attendance;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.svg.SvgConstants;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView Section;
    AutoCompleteTextView acTextView;
    Button add;
    ImageView back;
    Button cancel;
    ClassAdapter classAdapter;
    ArrayList<ClassItems> classItems = new ArrayList<>();
    DBHelper dbHelper;
    FloatingActionButton floatingActionButton;
    RecyclerView.LayoutManager layoutManager;
    Spinner name;
    RecyclerView recyclerView;
    ImageView save;
    Spinner section;
    TextView title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1397R.C1401layout.activity_main);
        this.dbHelper = new DBHelper(this);
        loadDATA();
        this.floatingActionButton = (FloatingActionButton) findViewById(C1397R.C1400id.floatingActionButton);
        this.recyclerView = (RecyclerView) findViewById(C1397R.C1400id.recyclerview);
        this.title = (TextView) findViewById(C1397R.C1400id.title_tool);
        this.Section = (TextView) findViewById(C1397R.C1400id.section_tool);
        this.back = (ImageView) findViewById(C1397R.C1400id.back);
        this.save = (ImageView) findViewById(C1397R.C1400id.save);
        this.title.setText("Cumilla Modern High School");
        this.Section.setText("Attendance Management");
        this.back.setVisibility(4);
        this.save.setVisibility(4);
        this.recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.layoutManager = linearLayoutManager;
        this.recyclerView.setLayoutManager(linearLayoutManager);
        ClassAdapter classAdapter2 = new ClassAdapter(this, this.classItems);
        this.classAdapter = classAdapter2;
        this.recyclerView.setAdapter(classAdapter2);
        this.floatingActionButton.setOnClickListener(new MainActivity$$ExternalSyntheticLambda0(this));
        this.classAdapter.setOnItemClickListener(new MainActivity$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$0$com-inan-cmhs-attendance-MainActivity  reason: not valid java name */
    public /* synthetic */ void m1325lambda$onCreate$0$cominancmhsattendanceMainActivity(View view) {
        fab();
    }

    private void loadDATA() {
        Cursor cursor = this.dbHelper.cursor();
        this.classItems.clear();
        while (cursor.moveToNext()) {
            this.classItems.add(new ClassItems(cursor.getInt(cursor.getColumnIndex("_CID")), cursor.getString(cursor.getColumnIndex(DBHelper.ClassNameKey)), cursor.getString(cursor.getColumnIndex(DBHelper.SectionNameKey))));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: gotoItem */
    public void m1326lambda$onCreate$1$cominancmhsattendanceMainActivity(int position) {
        Intent i = new Intent(this, StudentActivity.class);
        i.putExtra("className", this.classItems.get(position).getName());
        i.putExtra("sectionName", this.classItems.get(position).getSection());
        i.putExtra(CommonCssConstants.POSITION, position);
        i.putExtra("cid", this.classItems.get(position).getId());
        startActivity(i);
    }

    private void fab() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(C1397R.C1401layout.classdialog, (ViewGroup) null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        this.name = (Spinner) view.findViewById(C1397R.C1400id.classes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 17367049, new String[]{"Six(Day)", "Six(Morning)", "Seven(Day)", "Seven(Morning)", "Eight(Day)", "Eight(Morning)", "Nine(Day)", "Nine(Morning)", "Ten(Day)", "Ten(Morning)"});
        adapter.setDropDownViewResource(17367049);
        this.name.setAdapter(adapter);
        this.name.setOnItemSelectedListener(this);
        this.section = (Spinner) view.findViewById(C1397R.C1400id.Section_ClassDialog);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, 17367049, new String[]{SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A, SvgConstants.Attributes.PATH_DATA_BEARING, SvgConstants.Attributes.PATH_DATA_CURVE_TO, "D", "E", "F", "G", "H", "I", "J"});
        adapter.setDropDownViewResource(17367049);
        this.section.setAdapter(adapter2);
        this.section.setOnItemSelectedListener(this);
        this.cancel = (Button) view.findViewById(C1397R.C1400id.cancel_class);
        this.add = (Button) view.findViewById(C1397R.C1400id.create_class);
        this.cancel.setOnClickListener(new MainActivity$$ExternalSyntheticLambda4(alertDialog));
        this.add.setOnClickListener(new MainActivity$$ExternalSyntheticLambda5(this, alertDialog));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$fab$3$com-inan-cmhs-attendance-MainActivity  reason: not valid java name */
    public /* synthetic */ void m1324lambda$fab$3$cominancmhsattendanceMainActivity(AlertDialog alertDialog, View v) {
        add(this.name.getSelectedItem().toString(), this.section.getSelectedItem().toString());
        alertDialog.dismiss();
    }

    private void add(String name2, String section2) {
        this.classItems.add(new ClassItems((int) this.dbHelper.addClass(name2, section2), name2, section2));
        this.classAdapter.notifyDataSetChanged();
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            updateClass(item.getGroupId());
        } else {
            deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteClass(int pos) {
        this.dbHelper.removeClass(this.classItems.get(pos).getId());
        this.classItems.remove(pos);
        this.classAdapter.notifyItemRemoved(pos);
    }

    private void updateClass(int itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(C1397R.C1401layout.edt_class, (ViewGroup) null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Spinner name2 = (Spinner) view.findViewById(C1397R.C1400id.Name_classDialogedt);
        Spinner section2 = (Spinner) view.findViewById(C1397R.C1400id.Section_ClassDialogedt);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367049, new String[]{"Six(Day)", "Six(Morning)", "Seven(Day)", "Seven(Morning)", "Eight(Day)", "Eight(Morning)", "Nine(Day)", "Nine(Morning)", "Ten(Day)", "Ten(Morning)"});
        arrayAdapter.setDropDownViewResource(17367049);
        name2.setAdapter(arrayAdapter);
        name2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        String[] sections = {SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A, SvgConstants.Attributes.PATH_DATA_BEARING, SvgConstants.Attributes.PATH_DATA_CURVE_TO, "D", "E", "F", "G", "H", "I", "J"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, 17367049, sections);
        arrayAdapter2.setDropDownViewResource(17367049);
        section2.setAdapter(arrayAdapter2);
        section2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ((Button) view.findViewById(C1397R.C1400id.cancel_classedt)).setOnClickListener(new MainActivity$$ExternalSyntheticLambda2(alertDialog));
        MainActivity$$ExternalSyntheticLambda3 mainActivity$$ExternalSyntheticLambda3 = r0;
        ArrayAdapter arrayAdapter3 = arrayAdapter2;
        String[] strArr = sections;
        MainActivity$$ExternalSyntheticLambda3 mainActivity$$ExternalSyntheticLambda32 = new MainActivity$$ExternalSyntheticLambda3(this, itemId, name2, section2, alertDialog);
        ((Button) view.findViewById(C1397R.C1400id.edt_class)).setOnClickListener(mainActivity$$ExternalSyntheticLambda3);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$updateClass$5$com-inan-cmhs-attendance-MainActivity  reason: not valid java name */
    public /* synthetic */ void m1327lambda$updateClass$5$cominancmhsattendanceMainActivity(int itemId, Spinner name2, Spinner section2, AlertDialog alertDialog, View v) {
        editClass(itemId, name2.getSelectedItem().toString(), section2.getSelectedItem().toString());
        alertDialog.dismiss();
    }

    private void editClass(int position, String name2, String section2) {
        try {
            this.classItems.get(position).setName(name2);
            this.classItems.get(position).setSection(section2);
            this.classAdapter.notifyItemChanged(position);
            this.dbHelper.editclass(this.classItems.get(position).getId(), name2, section2);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), 0).show();
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
