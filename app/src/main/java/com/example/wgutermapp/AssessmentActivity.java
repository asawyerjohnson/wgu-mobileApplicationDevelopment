package com.example.wgutermapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AssessmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        String myToast = "The End.";
        showToast(myToast);
    }

    public void showToast(String theToast) {

        Toast.makeText(getApplicationContext(), theToast, Toast.LENGTH_LONG).show();
    }
}
