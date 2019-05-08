package com.example.wgutermapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnLaunchTermActivity;

     DBHelper helperBee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLaunchTermActivity = (Button) findViewById(R.id.launchTermActivitybutton);

        btnLaunchTermActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { launchTermActivity(); }
        });

        // Create SQLiteOpenHelper object
        helperBee = new DBHelper(MainActivity.this);
        helperBee.getWritableDatabase();
        // Indicate that the database has been opened
        Toast.makeText(MainActivity.this, helperBee.getDatabaseName() + " opened", Toast.LENGTH_SHORT).show();
        // Create tables IF NOT EXISTS
        helperBee.createTermTable("term_tbl");
        helperBee.createMentorTable("mentor_tbl");
        helperBee.createCourseTable("course_tbl");
        helperBee.createAssessmentTable("assessment_tbl");

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
    protected void onPause() {
        super.onPause();

        // Closing database to avoid memory leaks
        helperBee.close();
        Toast.makeText(MainActivity.this, helperBee.getDatabaseName() + "closed", Toast.LENGTH_SHORT).show();

    }

    private void launchTermActivity() {
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
    }

}
