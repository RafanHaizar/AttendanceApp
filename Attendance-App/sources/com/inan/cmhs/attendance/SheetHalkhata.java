package com.inan.cmhs.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import java.lang.reflect.Array;
import java.util.Calendar;

public class SheetHalkhata extends AppCompatActivity {
    ImageView back;
    DBHelper dbHelper;
    Intent intent;
    ImageView save;
    TextView section;
    TextView title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1397R.C1401layout.activity_sheet_halkhata);
        this.dbHelper = new DBHelper(this);
        this.title = (TextView) findViewById(C1397R.C1400id.title_tool);
        this.section = (TextView) findViewById(C1397R.C1400id.section_tool);
        this.back = (ImageView) findViewById(C1397R.C1400id.back);
        this.save = (ImageView) findViewById(C1397R.C1400id.save);
        Intent intent2 = getIntent();
        this.intent = intent2;
        String month = intent2.getStringExtra("month");
        this.title.setText("List of Attendance");
        System.out.println("SECTION -0--------" + month);
        this.section.setText(month);
        this.save.setOnClickListener(new SheetHalkhata$$ExternalSyntheticLambda0(this));
        this.back.setOnClickListener(new SheetHalkhata$$ExternalSyntheticLambda1(this));
        showTable();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$0$com-inan-cmhs-attendance-SheetHalkhata  reason: not valid java name */
    public /* synthetic */ void m1331lambda$onCreate$0$cominancmhsattendanceSheetHalkhata(View v) {
        pdf();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$1$com-inan-cmhs-attendance-SheetHalkhata  reason: not valid java name */
    public /* synthetic */ void m1332lambda$onCreate$1$cominancmhsattendanceSheetHalkhata(View v) {
        onBackPressed();
    }

    private void pdf() {
        TableLayout tableLayout = (TableLayout) findViewById(C1397R.C1400id.halkhata);
        String pdfPath = Environment.getExternalStorageDirectory() + "/table_layout.pdf";
        try {
            Document document = new Document(new PdfDocument(new PdfWriter(pdfPath)));
            int rowCount = tableLayout.getChildCount();
            for (int i = 0; i < rowCount; i++) {
                TableRow row = (TableRow) tableLayout.getChildAt(i);
                int cellCount = row.getChildCount();
                for (int j = 0; j < cellCount; j++) {
                    document.add((IBlockElement) new Paragraph(((TextView) row.getChildAt(j)).getText().toString()));
                }
            }
            document.close();
            Toast.makeText(this, "PDF created successfully at " + pdfPath, 0).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while creating pdf", 0).show();
        }
    }

    private void showTable() {
        long[] idArray;
        this.intent = getIntent();
        TableLayout tableLayout = (TableLayout) findViewById(C1397R.C1400id.halkhata);
        long[] idArray2 = this.intent.getLongArrayExtra("idArray");
        int[] rollArray = this.intent.getIntArrayExtra("rollArray");
        String[] nameArray = this.intent.getStringArrayExtra("nameArray");
        String month = this.intent.getStringExtra("month");
        int mayhem = 0;
        String dima = month;
        System.out.println("Month ---------------" + month);
        if (month.contains("January")) {
            mayhem = 1;
            month = month.replaceAll("January", "");
        }
        if (month.contains("February")) {
            mayhem = 2;
            month = month.replaceAll("February", "");
        }
        if (month.contains("March")) {
            mayhem = 3;
            month = month.replaceAll("March", "");
        }
        if (month.contains("April")) {
            mayhem = 4;
            month = month.replaceAll("April", "");
        }
        if (month.contains("May")) {
            mayhem = 5;
            month = month.replaceAll("May", "");
        }
        if (month.contains("June")) {
            mayhem = 6;
            month = month.replaceAll("June", "");
        }
        if (month.contains("July")) {
            mayhem = 7;
            month = month.replaceAll("July", "");
        }
        if (month.contains("August")) {
            mayhem = 8;
            month = month.replaceAll("August", "");
        }
        if (month.contains("September")) {
            mayhem = 9;
            month = month.replaceAll("September", "");
        }
        if (month.contains("October")) {
            mayhem = 10;
            month = month.replaceAll("October", "");
        }
        if (month.contains("November")) {
            mayhem = 11;
            month = month.replaceAll("November", "");
        }
        if (month.contains("December")) {
            mayhem = 12;
            month = month.replaceAll("December", "");
        }
        String month2 = month.replaceAll(" ", "");
        System.out.println("Month ---------------" + month2);
        System.out.println("Month ---------------" + mayhem);
        int dayinmonth = getDayInMonth(month2, mayhem);
        int rowSize = idArray2.length + 1;
        TableRow[] rows = new TableRow[rowSize];
        TextView[] roll_tvs = new TextView[rowSize];
        TextView[] name_tvs = new TextView[rowSize];
        String str = dima;
        int[] iArr = new int[2];
        iArr[1] = dayinmonth + 1;
        iArr[0] = rowSize;
        TextView[][] status_tvs = (TextView[][]) Array.newInstance(TextView.class, iArr);
        int i = 0;
        while (i < rowSize) {
            roll_tvs[i] = new TextView(this);
            name_tvs[i] = new TextView(this);
            roll_tvs[i].setTextColor(ViewCompat.MEASURED_STATE_MASK);
            name_tvs[i].setTextColor(ViewCompat.MEASURED_STATE_MASK);
            TableLayout tableLayout2 = tableLayout;
            name_tvs[i].setTextSize(2, 16.0f);
            roll_tvs[i].setTextSize(2, 16.0f);
            int i2 = 16;
            name_tvs[i].setPadding(16, 16, 16, 16);
            roll_tvs[i].setPadding(16, 16, 16, 16);
            int j = 1;
            while (j <= dayinmonth) {
                status_tvs[i][j] = new TextView(this);
                status_tvs[i][j].setPadding(i2, i2, i2, i2);
                status_tvs[0][j].setTextColor(ViewCompat.MEASURED_STATE_MASK);
                status_tvs[i][j].setTextSize(2, 16.0f);
                j++;
                rows = rows;
                i2 = 16;
            }
            i++;
            tableLayout = tableLayout2;
        }
        TableLayout tableLayout3 = tableLayout;
        TableRow[] rows2 = rows;
        roll_tvs[0].setText("Roll   ");
        roll_tvs[0].setTypeface(roll_tvs[0].getTypeface(), 1);
        name_tvs[0].setText("  Name  ");
        name_tvs[0].setTypeface(name_tvs[0].getTypeface(), 1);
        status_tvs[0][1].setTypeface(status_tvs[0][1].getTypeface(), 2);
        for (int i3 = 1; i3 < rowSize; i3++) {
            roll_tvs[i3].setText(String.valueOf(rollArray[i3 - 1]));
            name_tvs[i3].setText(nameArray[i3 - 1]);
            int j2 = 1;
            while (j2 <= dayinmonth) {
                String day = String.valueOf(j2);
                if (day.length() == 1) {
                    day = "0" + day;
                }
                String date = day + "." + mayhem + "." + month2;
                status_tvs[0][j2].setText(day);
                System.out.println(date);
                int[] rollArray2 = rollArray;
                String[] nameArray2 = nameArray;
                String status = this.dbHelper.getStatus(idArray2[i3 - 1], date);
                System.out.println(status);
                if (status != null) {
                    idArray = idArray2;
                    status_tvs[i3][j2].setText("  " + status + "  ");
                } else {
                    idArray = idArray2;
                    status_tvs[i3][j2].setText("  —   ");
                }
                j2++;
                idArray2 = idArray;
                rollArray = rollArray2;
                nameArray = nameArray2;
            }
            int[] iArr2 = rollArray;
            String[] strArr = nameArray;
        }
        int[] iArr3 = rollArray;
        String[] strArr2 = nameArray;
        for (int i4 = 0; i4 < rowSize; i4++) {
            rows2[i4] = new TableRow(this);
            rows2[i4].addView(roll_tvs[i4]);
            rows2[i4].addView(name_tvs[i4]);
            for (int j3 = 1; j3 <= dayinmonth; j3++) {
                rows2[i4].addView(status_tvs[i4][j3]);
            }
            tableLayout3.addView(rows2[i4]);
        }
    }

    private int getDayInMonth(String date, int mayhem) {
        int year = Integer.parseInt(date);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2, mayhem - 1);
        calendar.set(1, year);
        return calendar.getActualMaximum(5);
    }
}
