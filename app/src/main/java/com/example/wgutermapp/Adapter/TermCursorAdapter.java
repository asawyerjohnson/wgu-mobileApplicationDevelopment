package com.example.wgutermapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.wgutermapp.R;

public class TermCursorAdapter extends CursorAdapter {

    public TermCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.term_list_content, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvContent = view.findViewById(R.id.term_title);
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        tvContent.setText(title);
    }
}
