package com.inan.cmhs.attendance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //version
    public static final int VERSION = 1;
    //CLASS TABLE
    public static final String ClassTableName="Class_Table";
    public static final String C_ID="_CID";
    public static final String ClassNameKey = "Class_Name";
    public static final String SectionNameKey = "Section_Name";

    public static final String Create_Class_Table = "CREATE TABLE "+ClassTableName+"("+C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+ClassNameKey+" TEXT NOT NULL,"+SectionNameKey+" TEXT NOT NULL,"+"UNIQUE (" + ClassNameKey + "," + SectionNameKey + ")"+");";
    public static final String Delete_Class_Table="DROP TABLE IF EXISTS "+ClassTableName;
    public static final String Get_Class_Table="SELECT * FROM "+ClassTableName;

    //STUDENT TABLE
    public static final String StudentTableName="Student_Table";
    public static final String S_ID="_SID";
    public static final String C_ID_Student= "_CID";
    public static final String RollKey = "_ROLL";
    public static final String StudentNameKey = "Student_Name";

    public static final String Create_Student_Table = "CREATE TABLE "+StudentTableName+"("+S_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+C_ID_Student+" INTEGER NOT NULL,"+RollKey+" INTEGER NOT NULL,"+StudentNameKey+" TEXT NOT NULL,"+"UNIQUE (" + C_ID_Student + "," + RollKey + "," + StudentNameKey + ") );";

    public static final String Delete_Student_Table="DROP TABLE IF EXISTS "+StudentTableName;
    public static final String Get_Student_Table="SELECT * FROM "+StudentTableName;
    //STATUS TABLE
    public static final String StatusTableName="Status_Table";
    public static final String ID="_ID";
    public static final String S_ID_Status= "_SID";
    public static final String DateKey = "Date_Key";
    public static final String StatusKey = "Status_Key";

    public static final String Create_Status_Table = "CREATE TABLE "+StatusTableName+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+S_ID_Status+" INTEGER NOT NULL,"+C_ID+" INTEGER NOT NULL,"+DateKey+" DATE NOT NULL,"+StatusKey+" TEXT NOT NULL,"+"UNIQUE (" + S_ID_Status + " ," + DateKey + " ," + StatusKey + " )," + " FOREIGN KEY ("+S_ID+") REFERENCES " + StudentTableName + "("+S_ID+"), FOREIGN KEY ("+C_ID+") REFERENCES " + ClassTableName + "("+C_ID+")" + "  );";

    public static final String Delete_Status_Table="DROP TABLE IF EXISTS "+StatusTableName;
    public static final String Get_Status_Table="SELECT * FROM "+StatusTableName;
    //Start of query

    public DBHelper(@Nullable Context context) {
        super(context,"CMHS.db",null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_Class_Table);
        sqLiteDatabase.execSQL(Create_Student_Table);
        sqLiteDatabase.execSQL(Create_Status_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            sqLiteDatabase.execSQL(Delete_Class_Table);
            sqLiteDatabase.execSQL(Delete_Student_Table);
            sqLiteDatabase.execSQL(Delete_Status_Table);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    long addClass(String name,String section){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClassNameKey, name);
        values.put(SectionNameKey, section);
        return sqLiteDatabase.insert(ClassTableName, null, values);

    }



    long editclass(int pos, String name,String section){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClassNameKey, name);
        values.put(SectionNameKey, section);
        return sqLiteDatabase.update(ClassTableName,values,C_ID+"=?",new String[]{String.valueOf(pos)});

    }
    Cursor cursor(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery(Get_Class_Table,null);
    }
    long removeClass(int cid){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(ClassTableName,C_ID+"=?",new String[]{String.valueOf(cid)});
    }
    long addStudent(long cid,int roll,String studentName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_ID_Student,cid);
        values.put(RollKey, roll);
        values.put(StudentNameKey,studentName);
        return sqLiteDatabase.insert(StudentTableName, null, values);

    }



    long editstudent(long pos,long roll,String studentName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RollKey, roll);
        values.put(StudentNameKey,studentName);
        return sqLiteDatabase.update(StudentTableName,values,S_ID+"=?",new String[]{String.valueOf(pos)});

    }
    Cursor cursor_student(long cid){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.query(StudentTableName,null,C_ID+"=?",new String[]{String.valueOf(cid)},null,null,RollKey);
    }
    long removeStudent(long cid){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(StudentTableName,S_ID+"=?",new String[]{String.valueOf(cid)});
    }
    long addStatus(long sid, long cid, String date, String status){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(S_ID_Status,sid);
        values.put(C_ID, cid);
        values.put(DateKey, date);

        // Use equals method to compare strings
        if (!status.equals("P")) {
            status = "A";
        }

        values.put(StatusKey, status);
        return sqLiteDatabase.insert(StatusTableName, null, values);
    }
    long updateStatus(long sid, String date, String status){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StatusKey, status);
        String selection = DateKey + "='" + date + "' AND " + S_ID_Status + "=" + sid;
        int numRowsUpdated = sqLiteDatabase.update(StatusTableName, values, selection, null);
        Log.d("DBHelper", "Rows updated: " + numRowsUpdated);
        return numRowsUpdated;
    }

    @SuppressLint("Range")
    String getStatus(long sid, String date) {
        String status = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selection = S_ID_Status + " = ? AND " + DateKey + " = ?";
        String[] selectionArgs = {String.valueOf(sid), date};
        Cursor cursor = sqLiteDatabase.query(StatusTableName, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndex(StatusKey));
            System.out.println("Checkpoint No. 0003 loadStatus()");
            System.out.println(status);
        }
        cursor.close();
        return status;
    }

    Cursor getMonths(long cid){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        return sqLiteDatabase.query(StatusTableName,new String[]{DateKey},C_ID+"="+cid,null,"substr("+DateKey+",4,7)",null,null);
    }

    public void deleteStatus(long sid, long cid, String data) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(StatusTableName,C_ID+"="+cid+" AND "+S_ID_Status+"="+sid+" AND "+DateKey+"='"+data+"'",null);
    }
}
