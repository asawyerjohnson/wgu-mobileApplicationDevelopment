package com.example.wgutermapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wgutermapp.Model.Assessment;
import com.example.wgutermapp.Model.Course;
import com.example.wgutermapp.Model.Term;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TheDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TERM_TABLE = "term_tbl";
    private static final String TCOL_0 = "_id";
    private static final String TCOL_1 = "title";
    private static final String TCOL_2 = "start_date";
    private static final String TCOL_3 = "end_date";
    private static final String COURSE_TABLE = "course_tbl";
    private static final String CCOL_0 = "_id";
    private static final String CCOL_1 = "title";
    private static final String CCOL_2 = "start_date";
    private static final String CCOL_3 = "end_date";
    private static final String CCOL_4 = "status";
    private static final String CCOL_5 = "note";
    private static final String CCOL_6 = "mentor_name";
    private static final String CCOL_7 = "mentor_phone";
    private static final String CCOL_8 = "mentor_email";
    private static final String CCOL_9 = "term_id";
    private static final String ASSESSMENT_TABLE = "assessment_tbl";
    private static final String ACOL_0 = "_id";
    private static final String ACOL_1 = "title";
    private static final String ACOL_2 = "type";
    private static final String ACOL_3 = "due_date";
    private static final String ACOL_4 = "course_id";
    private static final String CREATE_TERM_TABLE = "CREATE TABLE IF NOT EXISTS " + TERM_TABLE + "(" +
            TCOL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TCOL_1 + " TEXT, " +
            TCOL_2 + " DATE, " +
            TCOL_3 + " DATE)";
    private static final String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS " + COURSE_TABLE + "(" +
            CCOL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CCOL_1 + " TEXT, " +
            CCOL_2 + " DATE, " +
            CCOL_3 + " DATE, " +
            CCOL_4 + " TEXT, " +
            CCOL_5 + " TEXT, " +
            CCOL_6 + " TEXT, " +
            CCOL_7 + " TEXT, " +
            CCOL_8 + " TEXT," +
            CCOL_9 + " INTEGER," +
            " FOREIGN KEY (" + CCOL_9 + ") REFERENCES " + TERM_TABLE + "(" + TCOL_0 +"));";
    private static final String CREATE_ASSESSMENT_TABLE = "CREATE TABLE IF NOT EXISTS " + ASSESSMENT_TABLE + "(" +
            ACOL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ACOL_1 + " TEXT, " +
            ACOL_2 + " TEXT, " +
            ACOL_3 + " DATE, " +
            ACOL_4 + " INTEGER," +
            " FOREIGN KEY (" + ACOL_4 + ") REFERENCES " + COURSE_TABLE + "(" + CCOL_0 +"));";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TERM_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_ASSESSMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TERM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ASSESSMENT_TABLE);
        onCreate(db);
    }

//    public void createTermTable() {
//        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + TERM_TABLE +
//                "(" + TCOL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                TCOL_1 + " TEXT, " +
//                TCOL_2 + " DATE, " +
//                TCOL_3 + " DATE)");
//    }

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
                CCOL_9 + " INTEGER," +
                " FOREIGN KEY (" + CCOL_9 + ") REFERENCES " + TERM_TABLE + "(" + TCOL_0 +"));");
    }

    public Course addCourse(Course course, long termId) {
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
        try {
            Term thisTerm = new Term();
            termId = thisTerm.getId();
            values.put(CCOL_9, termId);
        } catch (SQLException s) {
            System.out.println("Error retrieving the termId for the selected term: " + s.getMessage());
        }
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
            course.setTermId(cursor.getLong(9));
            course.setId(cursor.getLong(0));

            return course;
        }
        return null;
    }

    public List<Course> getCoursesforTerm() {
        List<Course> courseList = new ArrayList<>();
        // TODO: Need to retrieve courses for the selected term.
        try {
            String selectQuery = "SELECT * FROM " + COURSE_TABLE + ", " + TERM_TABLE + " WHERE " + CCOL_9 + " = " + TCOL_0;
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
        } catch (SQLException s) {
            System.out.println("Error retrieving courses for the selected term: " + s.getMessage());
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
                ACOL_4 + " INTEGER," +
                " FOREIGN KEY (" + ACOL_4 + ") REFERENCES " + COURSE_TABLE + "(" + CCOL_0 +"));");
    }

    public Assessment addAssessment(Assessment assessment, long courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACOL_1, assessment.getTitle());
        values.put(ACOL_2, assessment.getType());
        values.put(ACOL_3, assessment.getDueDate());
        // TODO: auto_collect courseId based on the course which this assessment is created in
        try {
            Course thisCourse = new Course();
            courseId = thisCourse.getId();
            values.put(ACOL_4, courseId);
        } catch (SQLException s) {
            System.out.println("Error retrieving the courseId for the selected course: " + s.getMessage());
        }
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

    public List<Assessment> getAssessmentsForCourse() {
        List<Assessment> assessmentList = new ArrayList<>();
        // TODO: Need to retrieve assessments for the selected course.
        try {
            String selectQuery = "SELECT * FROM " + ASSESSMENT_TABLE + ", " + COURSE_TABLE + " WHERE " + ACOL_4 + " = " + CCOL_0;
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
        } catch (SQLException s) {
            System.out.println("Error retrieving assessments for the selected course: " + s.getMessage());
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
