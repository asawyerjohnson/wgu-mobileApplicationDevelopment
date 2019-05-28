package com.example.wgutermapp.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wgutermapp.Adapter.CourseCursorAdapter;
import com.example.wgutermapp.Database.DBHelper;
import com.example.wgutermapp.R;

public class CourseListActivity extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog courseForm = onCreateDialog();
                courseForm.show();
            }
        });
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        try {
            ListView lvCourses = (ListView) findViewById(R.id.listCourses);
            if (lvCourses != null) {
                final CourseCursorAdapter courseAdapter = new CourseCursorAdapter(this, helper.getCoursesCursor());
                lvCourses.setAdapter(courseAdapter);
                lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = courseAdapter.getItem(position).toString();
                        Intent intent = new Intent(mContext, CourseDetailActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(mContext, "No Courses to Show", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            case R.id.add_sample_course:
                helper.insertCourseSample();
                Toast.makeText(mContext, "Refresh to view course", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
