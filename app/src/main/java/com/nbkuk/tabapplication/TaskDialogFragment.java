package com.nbkuk.tabapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDialogFragment extends DialogFragment {
    EditText mTaskTextView;
    TextView mDateTextView;
    CheckBox mStatusCheckBox;
    Button mSaveButton;
    Button mCancelButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.custom_task_dialog);
        dialog.show();

        mTaskTextView = (EditText) dialog.findViewById(R.id.dialogTaskText);
        mDateTextView = (TextView) dialog.findViewById(R.id.dialogTaskDate);
        mStatusCheckBox = (CheckBox) dialog.findViewById(R.id.dialogCheckBox);
        mSaveButton = (Button) dialog.findViewById(R.id.dialogSave);
        mCancelButton = (Button) dialog.findViewById(R.id.dialogCancel);

        mCancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Bundle args = getArguments();
        setDialogValuesFromId((Integer) args.get("_id"));

        return dialog;
    }

    private void setDialogValuesFromId(int id) {
        MySQLiteHelper databaseHelper = new MySQLiteHelper(getActivity());
        Cursor data = databaseHelper.getData(id);

        mTaskTextView.setText(data.getString(1));

        long dateLong = data.getLong(2);
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(dateLong));
        mDateTextView.setText(dateString);

        Boolean status = (data.getInt(data.getColumnIndex("status")) == 1)? true : false;
        mStatusCheckBox.setChecked(status);
    }
}
