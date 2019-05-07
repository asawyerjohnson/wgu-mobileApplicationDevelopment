package com.example.wgutermapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourseActivity extends AppCompatActivity {

    private Button btnLaunchAssessmentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        btnLaunchAssessmentActivity = (Button) findViewById(R.id.launchAssessmentActivityBtn);

        btnLaunchAssessmentActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAssessmentActivity();
            }
        });
    }

    private void launchAssessmentActivity() {
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }
}
