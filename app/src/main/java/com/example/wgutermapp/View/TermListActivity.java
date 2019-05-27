package com.example.wgutermapp.View;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wgutermapp.Adapter.TermCursorAdapter;
import com.example.wgutermapp.Database.DBHelper;
import com.example.wgutermapp.Model.Term;
import com.example.wgutermapp.R;
import com.example.wgutermapp.Util.MyReceiver;

import java.util.List;

public class TermListActivity extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    Context mContext = this;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        try {
            ListView lvTerms = (ListView) findViewById(R.id.listTerms);
            if (lvTerms != null) {
                final TermCursorAdapter termAdapter = new TermCursorAdapter(this, helper.getTermsCursor());
                lvTerms.setAdapter(termAdapter);
                lvTerms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = termAdapter.getItem(position).toString();
                        Intent intent = new Intent(mContext, TermDetailActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(mContext, "No Terms to Display", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog termForm = onCreateDialog();
                termForm.show();
            }
        });
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        final EditText termTitleText = findViewById(R.id.termTitleText);
        final EditText termStartDate = findViewById(R.id.termStart);
        final EditText termEndDate = findViewById(R.id.termEnd);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because it's going in the dialog layout
        builder.setView(inflater.inflate(R.layout.term_form, null));
        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // TODO: Save to database
                Term thisTerm = new Term();
//                thisTerm.setTitle(termTitleText.getText().toString());
//                thisTerm.setStartDate(termStartDate.getText().toString());
//                thisTerm.setEndDate(termEndDate.getText().toString());
//                dbHelper = new DBHelper(mContext);
//                dbHelper.addTerm(thisTerm);
//                dbHelper.close();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.sample_data:
                // TODO: generate some sample Terms, Courses, and Assessments
//                helper.insertTermSample();
//
//                Toast toast = Toast.makeText(mContext, "Refresh to see sample data",
//                        Toast.LENGTH_SHORT);
//                toast.show();
                return true;
            case R.id.sample_alarm:
                // TODO: generate a sample alarm to notify user in five seconds
                Intent intent=new Intent(TermListActivity.this, MyReceiver.class);
                PendingIntent sender= PendingIntent.getBroadcast(TermListActivity.this,0,intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, sender);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
