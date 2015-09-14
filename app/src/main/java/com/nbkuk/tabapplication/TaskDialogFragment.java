package com.nbkuk.tabapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

public class TaskDialogFragment extends DialogFragment implements View.OnClickListener {
    final String TAG_ID = "_id";

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

        mSaveButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        Bundle args = getArguments();
        setDialogValuesFromId((Integer) args.get(TAG_ID));

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialogCancel:
                dismiss();
                break;
            case R.id.dialogSave:
                Bundle args = getArguments();
                Integer id = (Integer) args.get(TAG_ID);

                MySQLiteHelper databaseHelper = new MySQLiteHelper(getActivity());
                Cursor data = databaseHelper.getData(id);

                if (!data.getString(1).equalsIgnoreCase(mTaskTextView.getText().toString())) {
                    String newtask = mTaskTextView.getText().toString();
                    databaseHelper.updateData(id, newtask);
                } else {
                    dismiss();
                }

                break;
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);

        final Activity activity = getActivity();
        ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);


        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
