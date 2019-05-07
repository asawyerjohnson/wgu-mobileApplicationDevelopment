package com.example.wgutermapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TermActivity extends AppCompatActivity {

    private Button btnLaunchCourseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        btnLaunchCourseActivity = (Button) findViewById(R.id.launchCourseActivityBtn);

        btnLaunchCourseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCourseActivity();
            }
        });
    }

    private void launchCourseActivity() {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }
}
