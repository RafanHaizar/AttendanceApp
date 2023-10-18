package com.inan.cmhs.attendance;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.svg.SvgConstants;
import java.util.ArrayList;
import java.util.Iterator;

public class StudentActivity extends AppCompatActivity {
    EditText Name;
    Button add;
    ImageView back;
    Button cancel;
    public long cid;
    DBHelper dbHelper = new DBHelper(this);
    TextView dialoge_title;
    FloatingActionButton fab_date;
    FloatingActionButton fab_sheet;
    FloatingActionButton floatingActionButton;
    String intent_section;
    String intent_title;
    RecyclerView.LayoutManager layoutManager;
    MyCalendar myCalendar = new MyCalendar();
    int position;
    RecyclerView recyclerView;
    EditText roll;
    ImageView save;
    TextView section;
    StudentAdapter studentAdapter;
    ArrayList<StudentItems> studentItems = new ArrayList<>();
    TextView title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1397R.C1401layout.activity_student);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(C1397R.C1400id.studentrecycle);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.layoutManager = linearLayoutManager;
        this.recyclerView.setLayoutManager(linearLayoutManager);
        StudentAdapter studentAdapter2 = new StudentAdapter(this, this.studentItems);
        this.studentAdapter = studentAdapter2;
        this.recyclerView.setAdapter(studentAdapter2);
        this.title = (TextView) findViewById(C1397R.C1400id.title_tool);
        this.section = (TextView) findViewById(C1397R.C1400id.section_tool);
        this.back = (ImageView) findViewById(C1397R.C1400id.back);
        this.save = (ImageView) findViewById(C1397R.C1400id.save);
        new Intent();
        Intent intent = getIntent();
        this.dialoge_title = (TextView) findViewById(C1397R.C1400id.title_of_classdialogedt);
        this.intent_title = intent.getStringExtra("className");
        this.intent_section = intent.getStringExtra("sectionName");
        this.cid = (long) intent.getIntExtra("cid", -1);
        this.position = intent.getIntExtra(CommonCssConstants.POSITION, -1);
        String str = this.intent_title;
        if (!(str == null || this.intent_section == null)) {
            this.title.setText(str);
            this.section.setText("Section: " + this.intent_section);
        }
        Load();
        loadStatus();
        this.back.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda6(this));
        this.save.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda7(this));
        this.floatingActionButton = (FloatingActionButton) findViewById(C1397R.C1400id.fab_student);
        this.fab_date = (FloatingActionButton) findViewById(C1397R.C1400id.fab_date);
        FloatingActionButton floatingActionButton2 = (FloatingActionButton) findViewById(C1397R.C1400id.fab_sheet);
        this.fab_sheet = floatingActionButton2;
        floatingActionButton2.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda8(this));
        this.floatingActionButton.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda9(this));
        this.fab_date.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda10(this));
        this.studentAdapter.setOnItemClickListener(new StudentActivity$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$0$com-inan-cmhs-attendance-StudentActivity  reason: not valid java name */
    public /* synthetic */ void m1334lambda$onCreate$0$cominancmhsattendanceStudentActivity(View v) {
        onBackPressed();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$1$com-inan-cmhs-attendance-StudentActivity  reason: not valid java name */
    public /* synthetic */ void m1335lambda$onCreate$1$cominancmhsattendanceStudentActivity(View v) {
        saveStatus();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$2$com-inan-cmhs-attendance-StudentActivity  reason: not valid java name */
    public /* synthetic */ void m1336lambda$onCreate$2$cominancmhsattendanceStudentActivity(View v) {
        sheet();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$3$com-inan-cmhs-attendance-StudentActivity  reason: not valid java name */
    public /* synthetic */ void m1337lambda$onCreate$3$cominancmhsattendanceStudentActivity(View v) {
        showAddStudentDialog();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$4$com-inan-cmhs-attendance-StudentActivity  reason: not valid java name */
    public /* synthetic */ void m1338lambda$onCreate$4$cominancmhsattendanceStudentActivity(View v) {
        showDateDialog();
    }

    private void sheet() {
        long[] idArray = new long[this.studentItems.size()];
        String[] nameArray = new String[this.studentItems.size()];
        int[] rollArray = new int[this.studentItems.size()];
        for (int i = 0; i < idArray.length; i++) {
            idArray[i] = this.studentItems.get(i).getSid();
        }
        for (int i2 = 0; i2 < nameArray.length; i2++) {
            nameArray[i2] = this.studentItems.get(i2).getName();
        }
        for (int i3 = 0; i3 < rollArray.length; i3++) {
            rollArray[i3] = Integer.parseInt(this.studentItems.get(i3).getRoll());
        }
        Intent i4 = new Intent(this, SheetActivity.class);
        i4.putExtra("cid", this.cid);
        System.out.println("Intent Here " + this.cid);
        i4.putExtra("idArray", idArray);
        i4.putExtra("nameArray", nameArray);
        i4.putExtra("rollArray", rollArray);
        startActivity(i4);
    }

    private void saveStatus() {
        Iterator<StudentItems> it = this.studentItems.iterator();
        while (it.hasNext()) {
            StudentItems studentItem = it.next();
            String status = studentItem.getStatus();
            long val = this.dbHelper.addStatus(studentItem.getSid(), this.cid, this.myCalendar.getData(), status);
            if (status != StandardRoles.f1511P) {
                status = SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A;
            }
            System.out.println(val + "-------------------------" + status);
            if (val == -1) {
                this.dbHelper.deleteStatus(studentItem.getSid(), this.cid, this.myCalendar.getData());
            }
            this.dbHelper.updateStatus(studentItem.getSid(), this.myCalendar.getData(), status);
        }
    }

    private void loadStatus() {
        Iterator<StudentItems> it = this.studentItems.iterator();
        while (it.hasNext()) {
            StudentItems studentItems1 = it.next();
            String status = this.dbHelper.getStatus(studentItems1.getSid(), this.myCalendar.getData());
            System.out.println("Checkpoint No. 0002 loadStatus()");
            System.out.println(status);
            if (status != null) {
                studentItems1.setStatus(status);
            }
        }
        this.studentAdapter.notifyDataSetChanged();
    }

    private void showDateDialog() {
        this.myCalendar.show(getSupportFragmentManager(), "");
        if (((String) this.section.getText()).contains("|")) {
            Toast.makeText(this, "You already have a date specified", 1);
        } else {
            this.myCalendar.setOnCalendarClickListener(new StudentActivity$$ExternalSyntheticLambda3(this));
        }
    }

    /* access modifiers changed from: private */
    public void OnOKClicked(int year, int month, int day) {
        String bt = this.myCalendar.getData();
        this.myCalendar.SetData(year, month, day);
        this.myCalendar.SetData(year, month, day);
        this.section.setText(this.section.getText() + " | " + bt);
    }

    private void OnOKClickedtwo(int year, int month, int day) {
        String bt = this.myCalendar.getData();
        this.myCalendar.SetData(year, month, day);
        this.myCalendar.SetData(year, month, day);
        String mota = (String) this.section.getText();
        while (mota.contains("|")) {
            mota.substring(0, mota.length() - 1);
        }
        this.section.setText(mota + " | " + bt);
    }

    public void Load() {
        Cursor cursor = this.dbHelper.cursor_student(this.cid);
        this.studentItems.clear();
        while (cursor.moveToNext()) {
            long sid = cursor.getLong(cursor.getColumnIndex("_SID"));
            int roll2 = cursor.getInt(cursor.getColumnIndex(DBHelper.RollKey));
            this.studentItems.add(new StudentItems(sid, String.valueOf(roll2), cursor.getString(cursor.getColumnIndex(DBHelper.StudentNameKey))));
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            updateStudent(item.getGroupId());
        } else {
            deleteStudent(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteStudent(int groupId) {
        this.dbHelper.removeStudent(this.studentItems.get(groupId).getSid());
        this.studentItems.remove(groupId);
        this.studentAdapter.notifyItemRemoved(groupId);
    }

    public void updateStudent(int itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(C1397R.C1401layout.edt_student, (ViewGroup) null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        this.roll = (EditText) view.findViewById(C1397R.C1400id.Roll_studentdialogedt);
        this.Name = (EditText) view.findViewById(C1397R.C1400id.Name_studentdialogedt);
        this.cancel = (Button) view.findViewById(C1397R.C1400id.cancel_studentedt);
        this.add = (Button) view.findViewById(C1397R.C1400id.edt_student);
        this.roll.setText(this.studentItems.get(itemId).getRoll());
        this.Name.setText(this.studentItems.get(itemId).getName());
        try {
            bool(String.valueOf(this.studentItems.get(this.position - 1).getRoll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.cancel.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda4(alertDialog));
        this.add.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda5(this, itemId, alertDialog));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$updateStudent$7$com-inan-cmhs-attendance-StudentActivity  reason: not valid java name */
    public /* synthetic */ void m1340lambda$updateStudent$7$cominancmhsattendanceStudentActivity(int itemId, AlertDialog alertDialog, View v) {
        editClass(itemId, this.roll.getText().toString(), this.Name.getText().toString());
        alertDialog.dismiss();
    }

    private void editClass(int position2, String roll2, String Name2) {
        try {
            this.studentItems.get(position2).setRoll(roll2);
            this.studentItems.get(position2).setName(Name2);
            this.studentAdapter.notifyItemChanged(position2);
            this.dbHelper.editstudent(this.studentItems.get(position2).getSid(), (long) Integer.parseInt(roll2), Name2);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), 0).show();
        }
    }

    /* renamed from: makechange */
    public void m1339lambda$onCreate$5$cominancmhsattendanceStudentActivity(int position2) {
        String status;
        if (this.studentItems.get(position2).getStatus().equals(StandardRoles.f1511P)) {
            status = SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A;
        } else {
            status = StandardRoles.f1511P;
        }
        this.studentItems.get(position2).setStatus(status);
        this.studentAdapter.notifyDataSetChanged();
    }

    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(C1397R.C1401layout.studentdialog, (ViewGroup) null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        EditText editText = (EditText) view.findViewById(C1397R.C1400id.Roll_studentdialog);
        this.roll = editText;
        String i = editText.getText().toString();
        this.Name = (EditText) view.findViewById(C1397R.C1400id.Name_studentdialoge);
        this.cancel = (Button) view.findViewById(C1397R.C1400id.cancel_student);
        this.add = (Button) view.findViewById(C1397R.C1400id.create_student);
        this.cancel.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda0(alertDialog));
        this.add.setOnClickListener(new StudentActivity$$ExternalSyntheticLambda2(this, alertDialog, i));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$showAddStudentDialog$9$com-inan-cmhs-attendance-StudentActivity */
    public /* synthetic */ void mo25088xc96b7e2(AlertDialog alertDialog, String i, View v) {
        add();
        alertDialog.dismiss();
        bool(i);
    }

    private void bool(String i) {
        try {
            this.roll.setText(Integer.parseInt(i) + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void add() {
        String vroll = this.roll.getText().toString();
        String vname = this.Name.getText().toString();
        this.studentItems.add(new StudentItems(this.dbHelper.addStudent(this.cid, Integer.parseInt(vroll), vname), vroll, vname));
    }
}
