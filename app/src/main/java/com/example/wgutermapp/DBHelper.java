package com.example.wgutermapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TheDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTermTable(String tableName) {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title VARCHAR(50), " +
                "start_date DATE, " +
                "end_date DATE)");
    }

    public void createMentorTable(String tableName) {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(50), " +
                "phone VARCHAR(15), " +
                "email VARCHAR)");
    }

    public void createAssessmentTable(String tableName) {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title VARCHAR(50), " +
                "type VARCHAR(50), " +
                "due_date DATE, " +
                "course_id INTEGER)"); // TODO: course_id should be a foreign key for the course_tbl
    }

    public void createCourseTable(String tableName) {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title VARCHAR(50), " +
                "start_date DATE, " +
                "end_date DATE, " +
                "status VARCHAR(20), " +
                "note VARCHAR(140), " +
                "mentor_id INTEGER, " + // TODO: mentor_id should be a foreign key for the mentor_tbl
                "term_id INTEGER)"); // TODO: term_id should be a foreign key for the term_tbl
    }

    public void insertRecord(String sqlStatement) {
        this.getWritableDatabase().execSQL(sqlStatement);
    }
}
