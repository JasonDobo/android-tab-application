package com.nbkuk.tabapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jason.dobo on 21/01/2015.
 */
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
        TextView taskBody = (TextView) view.findViewById(R.id.firstLine);
        TextView dateBody = (TextView) view.findViewById(R.id.secondLine);
        ImageView statusIMage = (ImageView) view.findViewById(R.id.icon);

        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndex("task"));

        taskBody.setText(body);
    }


}
