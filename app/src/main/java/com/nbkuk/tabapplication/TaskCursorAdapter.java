package com.nbkuk.tabapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskCursorAdapter extends CursorAdapter {

    public TaskCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView taskText = (TextView) view.findViewById(R.id.taskLine);
        TextView dateText = (TextView) view.findViewById(R.id.dateLine);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);

        // Extract properties from cursor
        String task = cursor.getString(cursor.getColumnIndex("task"));
        Boolean status = (cursor.getInt(cursor.getColumnIndex("status")) == 1)? true : false;

        long dateLong = cursor.getLong(cursor.getColumnIndex("date"));
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(dateLong));

        taskText.setText(task);
        dateText.setText(date);
        checkBox.setChecked(status);
    }
}
