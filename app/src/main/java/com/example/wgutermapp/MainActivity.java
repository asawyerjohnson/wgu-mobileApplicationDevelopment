package com.example.wgutermapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnLaunchTermActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button transition
        btnLaunchTermActivity = (Button) findViewById(R.id.launchTermActivitybutton);
        btnLaunchTermActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { launchTermListActivity(); }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void launchTermListActivity() {
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }
}
