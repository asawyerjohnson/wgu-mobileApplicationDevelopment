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

import com.example.wgutermapp.Adapter.AssessmentCursorAdapter;
import com.example.wgutermapp.Adapter.CourseCursorAdapter;
import com.example.wgutermapp.Database.DBHelper;
import com.example.wgutermapp.Model.Assessment;
import com.example.wgutermapp.R;

public class AssessmentListActivity extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog assessmentForm = onCreateDialog();
                assessmentForm.show();
            }
        });
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        try {
            ListView lvAssessments = (ListView) findViewById(R.id.assessment_list);
            if (lvAssessments != null) {
                final AssessmentCursorAdapter assessmentAdapter = new AssessmentCursorAdapter(this, helper.getAssessmentCursor());
                lvAssessments.setAdapter(assessmentAdapter);
                lvAssessments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = assessmentAdapter.getItem(position).toString();
                        Intent intent = new Intent(mContext, AssessmentDetailActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(mContext, "No Assessments to Show", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final EditText assessmentTitleText = findViewById(R.id.assessmentTitleText);
        final RadioGroup assessmentTypeRadio = findViewById(R.id.assessmentRBG);
        final EditText assessmentDueText = findViewById(R.id.assessmentDateText);

        builder.setView(inflater.inflate(R.layout.assessment_form, null));
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
        getMenuInflater().inflate(R.menu.menu_assessment_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.add_sample_assessment:
                helper.insertAssessmentSample();
                Toast.makeText(this, "Refresh to view assessment", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
