package com.example.wgutermapp.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wgutermapp.R;

public class CourseListActivity extends AppCompatActivity {

    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog courseForm = onCreateDialog();
                courseForm.show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final EditText courseTitleText = findViewById(R.id.editTextCourseTitle);
        final EditText courseStartDateText = findViewById(R.id.editTextCourseStart);
        final EditText courseEndDateText = findViewById(R.id.editTextEndDate);
        final RadioGroup courseStatusRadio = findViewById(R.id.courseStatusRBG);
        final EditText courseNoteText = findViewById(R.id.editTextNote);
        final EditText courseMentorNameText = findViewById(R.id.editTextMentorName);
        final EditText courseMentorPhoneText = findViewById(R.id.editTextMentorPhone);
        final EditText courseMentorEmailText = findViewById(R.id.editTextMentorEmail);

        builder.setView(inflater.inflate(R.layout.course_form, null));
        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: Save course to database
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.sample_data:
                // TODO: generate some sample Terms, Courses, and Assessments
                Toast toast = Toast.makeText(mContext, "This option will create sample data",
                        Toast.LENGTH_LONG);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
