package com.inan.cmhs.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.svg.SvgConstants;

public class DBHelper extends SQLiteOpenHelper {
    public static final String C_ID = "_CID";
    public static final String C_ID_Student = "_CID";
    public static final String ClassNameKey = "Class_Name";
    public static final String ClassTableName = "Class_Table";
    public static final String Create_Class_Table = "CREATE TABLE Class_Table(_CID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,Class_Name TEXT NOT NULL,Section_Name TEXT NOT NULL,UNIQUE (Class_Name,Section_Name));";
    public static final String Create_Status_Table = "CREATE TABLE Status_Table(_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,_SID INTEGER NOT NULL,_CID INTEGER NOT NULL,Date_Key DATE NOT NULL,Status_Key TEXT NOT NULL,UNIQUE (_SID ,Date_Key ,Status_Key ), FOREIGN KEY (_SID) REFERENCES Student_Table(_SID), FOREIGN KEY (_CID) REFERENCES Class_Table(_CID)  );";
    public static final String Create_Student_Table = "CREATE TABLE Student_Table(_SID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,_CID INTEGER NOT NULL,_ROLL INTEGER NOT NULL,Student_Name TEXT NOT NULL,UNIQUE (_CID,_ROLL,Student_Name) );";
    public static final String DateKey = "Date_Key";
    public static final String Delete_Class_Table = "DROP TABLE IF EXISTS Class_Table";
    public static final String Delete_Status_Table = "DROP TABLE IF EXISTS Status_Table";
    public static final String Delete_Student_Table = "DROP TABLE IF EXISTS Student_Table";
    public static final String Get_Class_Table = "SELECT * FROM Class_Table";
    public static final String Get_Status_Table = "SELECT * FROM Status_Table";
    public static final String Get_Student_Table = "SELECT * FROM Student_Table";

    /* renamed from: ID */
    public static final String f1136ID = "_ID";
    public static final String RollKey = "_ROLL";
    public static final String S_ID = "_SID";
    public static final String S_ID_Status = "_SID";
    public static final String SectionNameKey = "Section_Name";
    public static final String StatusKey = "Status_Key";
    public static final String StatusTableName = "Status_Table";
    public static final String StudentNameKey = "Student_Name";
    public static final String StudentTableName = "Student_Table";
    public static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, "CMHS.db", (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_Class_Table);
        sqLiteDatabase.execSQL(Create_Student_Table);
        sqLiteDatabase.execSQL(Create_Status_Table);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(Delete_Class_Table);
            sqLiteDatabase.execSQL(Delete_Student_Table);
            sqLiteDatabase.execSQL(Delete_Status_Table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public long addClass(String name, String section) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClassNameKey, name);
        values.put(SectionNameKey, section);
        return sqLiteDatabase.insert(ClassTableName, (String) null, values);
    }

    /* access modifiers changed from: package-private */
    public long editclass(int pos, String name, String section) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClassNameKey, name);
        values.put(SectionNameKey, section);
        return (long) sqLiteDatabase.update(ClassTableName, values, "_CID=?", new String[]{String.valueOf(pos)});
    }

    /* access modifiers changed from: package-private */
    public Cursor cursor() {
        return getReadableDatabase().rawQuery(Get_Class_Table, (String[]) null);
    }

    /* access modifiers changed from: package-private */
    public long removeClass(int cid) {
        return (long) getWritableDatabase().delete(ClassTableName, "_CID=?", new String[]{String.valueOf(cid)});
    }

    /* access modifiers changed from: package-private */
    public long addStudent(long cid, int roll, String studentName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_CID", Long.valueOf(cid));
        values.put(RollKey, Integer.valueOf(roll));
        values.put(StudentNameKey, studentName);
        return sqLiteDatabase.insert(StudentTableName, (String) null, values);
    }

    /* access modifiers changed from: package-private */
    public long editstudent(long pos, long roll, String studentName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RollKey, Long.valueOf(roll));
        values.put(StudentNameKey, studentName);
        return (long) sqLiteDatabase.update(StudentTableName, values, "_SID=?", new String[]{String.valueOf(pos)});
    }

    /* access modifiers changed from: package-private */
    public Cursor cursor_student(long cid) {
        return getReadableDatabase().query(StudentTableName, (String[]) null, "_CID=?", new String[]{String.valueOf(cid)}, (String) null, (String) null, RollKey);
    }

    /* access modifiers changed from: package-private */
    public long removeStudent(long cid) {
        return (long) getWritableDatabase().delete(StudentTableName, "_SID=?", new String[]{String.valueOf(cid)});
    }

    /* access modifiers changed from: package-private */
    public long addStatus(long sid, long cid, String date, String status) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_SID", Long.valueOf(sid));
        values.put("_CID", Long.valueOf(cid));
        values.put(DateKey, date);
        if (!status.equals(StandardRoles.f1511P)) {
            status = SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A;
        }
        values.put(StatusKey, status);
        return sqLiteDatabase.insert(StatusTableName, (String) null, values);
    }

    /* access modifiers changed from: package-private */
    public long updateStatus(long sid, String date, String status) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StatusKey, status);
        int numRowsUpdated = sqLiteDatabase.update(StatusTableName, values, "Date_Key='" + date + "' AND " + "_SID" + "=" + sid, (String[]) null);
        Log.d("DBHelper", "Rows updated: " + numRowsUpdated);
        return (long) numRowsUpdated;
    }

    /* access modifiers changed from: package-private */
    public String getStatus(long sid, String date) {
        String status = null;
        Cursor cursor = getReadableDatabase().query(StatusTableName, (String[]) null, "_SID = ? AND Date_Key = ?", new String[]{String.valueOf(sid), date}, (String) null, (String) null, (String) null);
        if (cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndex(StatusKey));
            System.out.println("Checkpoint No. 0003 loadStatus()");
            System.out.println(status);
        }
        cursor.close();
        return status;
    }

    /* access modifiers changed from: package-private */
    public Cursor getMonths(long cid) {
        return getReadableDatabase().query(StatusTableName, new String[]{DateKey}, "_CID=" + cid, (String[]) null, "substr(Date_Key,4,7)", (String) null, (String) null);
    }

    public void deleteStatus(long sid, long cid, String data) {
        getWritableDatabase().delete(StatusTableName, "_CID=" + cid + " AND " + "_SID" + "=" + sid + " AND " + DateKey + "='" + data + "'", (String[]) null);
    }
}
