package com.example.wgutermapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.util.List;

/**
 * An activity representing a list of Terms.
 */
public class TermListActivity extends AppCompatActivity {

    Context mContext = this;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        DBHelper db = new DBHelper(mContext);
        List<Term> values = db.getAllTerms();
        final ArrayAdapter<Term> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        ListView listView = findViewById(R.id.listTerms);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String value = adapter.getItem(position).toString();
                Intent intent = new Intent(mContext, TermDetailActivity.class);
                startActivity(intent);
            }
        });

        db.close();

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
//                Term thisTerm = new Term();
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
                Toast toast = Toast.makeText(mContext, "This option will create sample data",
                        Toast.LENGTH_LONG);
                toast.show();
                return true;
            case R.id.sample_alarm:
                // TODO: generate a sample alarm to notify user in five seconds
                Intent intent=new Intent(TermListActivity.this,MyReceiver.class);
                PendingIntent sender= PendingIntent.getBroadcast(TermListActivity.this,0,intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, sender);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
