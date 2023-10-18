package com.inan.cmhs.attendance;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.util.Calendar;

public class SheetHalkhata extends AppCompatActivity {
    Intent intent;
    ImageView back,save;
    TextView title,section;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_halkhata);
        dbHelper = new DBHelper(this);
        title=findViewById(R.id.title_tool);
        section=findViewById(R.id.section_tool);
        back=findViewById(R.id.back);
        save=findViewById(R.id.save);
        intent = getIntent();
        String month=intent.getStringExtra("month");
        title.setText("List of Attendance");
        System.out.println("SECTION -0--------"+month);
        section.setText(month);
        save.setOnClickListener(v-> {pdf();
        });
        back.setOnClickListener(v->onBackPressed());
        showTable();
    }

    private void pdf() {
        TableLayout tableLayout = findViewById(R.id.halkhata);
        String pdfFileName = "table_layout.pdf";

        File pdfFile = new File(getExternalFilesDir(null), pdfFileName);
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFile));
            Document document = new Document(pdfDocument);

            int rowCount = tableLayout.getChildCount();
            for (int i = 0; i < rowCount; i++) {
                TableRow row = (TableRow) tableLayout.getChildAt(i);
                int cellCount = row.getChildCount();
                TextView cell = (TextView) row.getChildAt(i);
                String cellText = cell.getText().toString();
                document.add(new Paragraph(cellText));
                for (int j = 0; j < cellCount; j++) {
                    TextView cell2 = (TextView) row.getChildAt(j);
                    String cellText2 = cell2.getText().toString();
                    document.add(new Paragraph(cellText2));
                }
            }

            document.close();
            Toast.makeText(this, "PDF created successfully at " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            // Call a method to open the PDF file for viewing/downloading
            openPdfFile(pdfFile);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while creating PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPdfFile(File pdfFile) {
        Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    private void showTable() {
        intent = getIntent();
        TableLayout tableLayout= findViewById(R.id.halkhata);
        long[] idArray=intent.getLongArrayExtra("idArray");
        int[] rollArray=intent.getIntArrayExtra("rollArray");
        String[] nameArray=intent.getStringArrayExtra("nameArray");
        String month=intent.getStringExtra("month");
        int mayhem = 0;
        String dima=month;
        System.out.println("Month ---------------"+month);
        if(month.contains("January")){ mayhem=1; month=month.replaceAll("January","");}
        if(month.contains("February")){ mayhem=2;month=month.replaceAll("February","");}
        if(month.contains("March")){ mayhem=3;month=month.replaceAll("March","");}
        if(month.contains("April")){ mayhem=4;month=month.replaceAll("April","");}
        if(month.contains("May")){ mayhem=5;month=month.replaceAll("May","");}
        if(month.contains("June")){ mayhem=6;month=month.replaceAll("June","");}
        if(month.contains("July")){ mayhem=7;month=month.replaceAll("July","");}
        if(month.contains("August")){ mayhem=8;month=month.replaceAll("August","");}
        if(month.contains("September")){ mayhem=9;month=month.replaceAll("September","");}
        if(month.contains("October")){ mayhem=10;month=month.replaceAll("October","");}
        if(month.contains("November")){ mayhem=11;month=month.replaceAll("November","");}
        if(month.contains("December")){ mayhem=12;month=month.replaceAll("December","");}
        month=month.replaceAll(" ","");
        System.out.println("Month ---------------"+month);
        System.out.println("Month ---------------"+mayhem);
        int dayinmonth=getDayInMonth(month,mayhem);
        int rowSize=idArray.length+1;
        TableRow[] rows = new TableRow[rowSize];
        TextView[] roll_tvs = new TextView[rowSize];
        TextView[] name_tvs = new TextView[rowSize];
        TextView[][] status_tvs = new TextView[rowSize][dayinmonth+1];
        for(int i=0;i<rowSize;i++){
            roll_tvs[i]=new TextView(this);
            name_tvs[i]=new TextView(this);
            roll_tvs[i].setTextColor(Color.BLACK);
            name_tvs[i].setTextColor(Color.BLACK);
            name_tvs[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            roll_tvs[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            name_tvs[i].setPadding(16, 16, 16, 16);
            roll_tvs[i].setPadding(16, 16, 16, 16);
            for (int j=1;j<=dayinmonth;j++){
                status_tvs[i][j] = new TextView(this);
                status_tvs[i][j].setPadding(16, 16, 16, 16);
                status_tvs[0][j].setTextColor(Color.BLACK);
                status_tvs[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        }
        roll_tvs[0].setText("Roll   ");
        roll_tvs[0].setTypeface(roll_tvs[0].getTypeface(), Typeface.BOLD);
        name_tvs[0].setText("  Name  ");
        name_tvs[0].setTypeface(name_tvs[0].getTypeface(), Typeface.BOLD);
        status_tvs[0][1].setTypeface(status_tvs[0][1].getTypeface(), Typeface.ITALIC);
        for(int i=1;i<rowSize;i++){
            roll_tvs[i].setText(String.valueOf(rollArray[i-1]));
            name_tvs[i].setText(nameArray[i-1]);
            for(int j=1;j<=dayinmonth;j++){
                String day=String.valueOf(j);
                if(day.length()==1) day="0"+day;
                String date=day+"."+mayhem+"."+month;
                status_tvs[0][j].setText(day);
                System.out.println(date);
                String status = dbHelper.getStatus(idArray[i - 1], date);
                System.out.println(status);
                if (status != null) {
                    status_tvs[i][j].setText("  "+status+"  ");
                } else {
                    status_tvs[i][j].setText("  —   "); // Display a placeholder for null values
                }
            }
        }
        for(int i=0;i<rowSize;i++){
            rows[i]=new TableRow(this);
            rows[i].addView(roll_tvs[i]);
            rows[i].addView(name_tvs[i]);
            for(int j=1;j<=dayinmonth;j++){
                rows[i].addView(status_tvs[i][j]);
            }
            tableLayout.addView(rows[i]);
        }
    }


    private int getDayInMonth(String date, int mayhem) {
        int year = Integer.parseInt(date);

        // Subtract 1 from mayhem because Calendar.MONTH starts from 0 for January
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, mayhem - 1);
        calendar.set(Calendar.YEAR, year);

        // Return the actual maximum day of the month
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}