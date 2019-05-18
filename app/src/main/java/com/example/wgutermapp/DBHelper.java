package com.example.wgutermapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TheDatabase.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TERM_TABLE = "term_tbl";
    public static final String TCOL_0 = "_id";
    public static final String TCOL_1 = "title";
    public static final String TCOL_2 = "start_date";
    public static final String TCOL_3 = "end_date";
    public static final String COURSE_TABLE = "course_tbl";
    public static final String CCOL_0 = "_id";
    public static final String CCOL_1 = "title";
    public static final String CCOL_2 = "start_date";
    public static final String CCOL_3 = "end_date";
    public static final String CCOL_4 = "status";
    public static final String CCOL_5 = "note";
    public static final String CCOL_6 = "mentor_name";
    public static final String CCOL_7 = "mentor_phone";
    public static final String CCOL_8 = "mentor_email";
    public static final String CCOL_9 = "term_id";
    public static final String ASSESSMENT_TABLE = "assessment_tbl";
    public static final String ACOL_0 = "_id";
    public static final String ACOL_1 = "title";
    public static final String ACOL_2 = "type";
    public static final String ACOL_3 = "due_date";
    public static final String ACOL_4 = "course_id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables IF NOT EXISTS
        createTermTable();
        createCourseTable();
        createAssessmentTable();
        // TODO: Allow input from GUI to update database
//        // SQL Insert Statements for Sample Data
//        helperBee.insertRecord("INSERT INTO term_tbl(title, start_date, end_date) " +
//                "VALUES('Sample Term', '2019-06-01', '2019-12-01')");
//        helperBee.insertRecord("INSERT INTO mentor_tbl(name, phone, email) " +
//                "VALUES('Sample Mentor', '222-222-2222', 'sample.mentor@wgu.egu')");
//        helperBee.insertRecord("INSERT INTO course_tbl(title, start_date, end_date, status, note, mentor_id, term_id) " +
//                "VALUES('Sample Course', '2019-06-01', '2019-08-01', 'In-Progress', 'Pay attention to geometry section', 1, 1)");
//        helperBee.insertRecord("INSERT INTO assessment_tbl(title, type, due_date, course_id) " +
//                "VALUES('Sample Assessment', 'Objective Exam', '2019-08-01', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TERM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ASSESSMENT_TABLE);
        onCreate(db);
    }

    public void createTermTable() {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + TERM_TABLE +
                "(" + TCOL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TCOL_1 + " TEXT, " +
                TCOL_2 + " DATE, " +
                TCOL_3 + " DATE)");
    }

    public Term addTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TCOL_1, term.getTitle());
        values.put(TCOL_2, term.getStartDate());
        values.put(TCOL_3, term.getEndDate());
        long id = db.insert(TERM_TABLE, null, values);
        term.setId(id);
        db.close();
        return term;
    }

    Term getTerm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TERM_TABLE, new String[] {
                        TCOL_0, TCOL_1, TCOL_2, TCOL_3 }, TCOL_0 + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Term term = new Term(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
            term.setId(cursor.getLong(0));
            return term;
        }
        return null;
    }

    public List<Term> getAllTerms() {
        List<Term> termList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TERM_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            Term term = new Term();
            term.setId(Long.parseLong(cursor.getString(0)));
            term.setTitle(cursor.getString(1));
            term.setStartDate(cursor.getString(2));
            term.setEndDate(cursor.getString(3));
            termList.add(term);
        }
        return termList;
    }

    public Cursor getTermsCursor() {
        String selectQuery = "SELECT * FROM " + TERM_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public int updateTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TCOL_1, term.getTitle());
        values.put(TCOL_2, term.getStartDate());
        values.put(TCOL_3, term.getEndDate());
        return db.update(TERM_TABLE, values, TCOL_0 + "=?",
                new String[] { String.valueOf(term.getId())});
    }

    public void deleteTerm(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TERM_TABLE, TCOL_0 + "=?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void createCourseTable() {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + COURSE_TABLE +
                "(" + CCOL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CCOL_1 + " TEXT, " +
                CCOL_2 + " DATE, " +
                CCOL_3 + " DATE, " +
                CCOL_4 + " TEXT, " +
                CCOL_5 + " TEXT, " +
                CCOL_6 + " TEXT, " +
                CCOL_7 + " TEXT, " +
                CCOL_8 + " TEXT," +
                CCOL_9 + " INTEGER)");
    }

    public Course addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CCOL_1, course.getTitle());
        values.put(CCOL_2, course.getStartDate());
        values.put(CCOL_3, course.getEndDate());
        values.put(CCOL_4, course.getStatus());
        values.put(CCOL_5, course.getNote());
        values.put(CCOL_6, course.getMentorName());
        values.put(CCOL_7, course.getMentorPhone());
        values.put(CCOL_8, course.getMentorEmail());
        // TODO: automatically collect termId based on the term which the course is created in
        values.put(CCOL_9, course.getTermId());
        long id = db.insert(COURSE_TABLE, null, values);
        course.setId(id);
        db.close();
        return course;
    }

    Course getCourse(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(COURSE_TABLE, new String[] {
               CCOL_0, CCOL_1, CCOL_2, CCOL_3, CCOL_4, CCOL_5, CCOL_6, CCOL_7, CCOL_8, CCOL_9 },
                CCOL_0 + "=?", new String[] { String.valueOf(id) }, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Course course = new Course(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8));
            course.setId(cursor.getLong(0));
            // THIS MIGHT BE WRONG
            course.setTermId(cursor.getLong(9));
            return course;
        }
        return null;
    }

    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        // TODO: Need to retrieve courses for the selected term - fix this.
        String selectQuery = "SELECT * FROM " + COURSE_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            Course course = new Course();
            course.setId(Long.parseLong(cursor.getString(0)));
            course.setTitle(cursor.getString(1));
            course.setStartDate(cursor.getString(2));
            course.setEndDate(cursor.getString(3));
            course.setStatus(cursor.getString(4));
            course.setNote(cursor.getString(5));
            course.setMentorName(cursor.getString(6));
            course.setMentorPhone(cursor.getString(7));
            course.setMentorEmail(cursor.getString(8));
            course.setTermId(Long.parseLong(cursor.getString(9)));
            courseList.add(course);
        }
        return courseList;
    }

    public Cursor getCoursesCursor() {
        String selectQuery = "SELECT * FROM " + COURSE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public int updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CCOL_1, course.getTitle());
        values.put(CCOL_2, course.getStartDate());
        values.put(CCOL_3, course.getEndDate());
        values.put(CCOL_4, course.getStatus());
        values.put(CCOL_5, course.getNote());
        values.put(CCOL_6, course.getMentorName());
        values.put(CCOL_7, course.getMentorPhone());
        values.put(CCOL_8, course.getMentorEmail());
        return db.update(COURSE_TABLE, values, CCOL_0 + "=?",
                new String[] { String.valueOf(course.getId())});
    }

    public void deleteCourse(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSE_TABLE, CCOL_0 + "=?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void createAssessmentTable() {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + ASSESSMENT_TABLE +
                "(" + ACOL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACOL_1 + " TEXT, " +
                ACOL_2 + " TEXT, " +
                ACOL_3 + " DATE, " +
                ACOL_4 + " INTEGER)");
    }

    public Assessment addAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACOL_1, assessment.getTitle());
        values.put(ACOL_2, assessment.getType());
        values.put(ACOL_3, assessment.getDueDate());
        // TODO: auto_collect courseId based on the course which this assessment is created in
        values.put(ACOL_4, assessment.getCourseId());
        long id = db.insert(ASSESSMENT_TABLE, null, values);
        assessment.setId(id);
        db.close();
        return assessment;
    }

    Assessment getAssessment(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ASSESSMENT_TABLE, new String[] {
                ACOL_0, ACOL_1, ACOL_2, ACOL_3, ACOL_4 }, ACOL_0 + "=?",
                new String[] { String.valueOf(id) }, null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
            Assessment assessment = new Assessment(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
            assessment.setId(cursor.getLong(0));
            // THIS MIGHT BE WRONG
            assessment.setCourseId(cursor.getLong(4));
            return assessment;
        }
        return null;
    }

    public List<Assessment> getAllAssessments() {
        List<Assessment> assessmentList = new ArrayList<>();
        // TODO: Need to retrieve assessments for the selected course - fix this.
        String selectQuery = "SELECT * FROM " + ASSESSMENT_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            Assessment assessment = new Assessment();
            assessment.setId(Long.parseLong(cursor.getString(0)));
            assessment.setTitle(cursor.getString(1));
            assessment.setType(cursor.getString(2));
            assessment.setDueDate(cursor.getString(3));
            assessment.setCourseId(Long.parseLong(cursor.getString(4)));
            assessmentList.add(assessment);
        }
        return assessmentList;
    }

    public Cursor getAssessmentCursor() {
        String selectQuery = "SELECT * FROM " + ASSESSMENT_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public int updateAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACOL_1, assessment.getTitle());
        values.put(ACOL_2, assessment.getType());
        values.put(ACOL_3, assessment.getDueDate());
        return db.update(ASSESSMENT_TABLE, values, ACOL_0 + "=?",
                new String[] { String.valueOf(assessment.getId())});
    }

    public void deleteAssessment(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ASSESSMENT_TABLE, ACOL_0 + "=?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}
