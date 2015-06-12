package com.nbkuk.tabapplication;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by jason.dobo on 20/05/2015.
 */
public class MyDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText mEditText;
    private Button mSubmitTaskButton;

    public MyDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static MyDialogFragment newInstance(int taskid) {
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();
        //args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_tasks, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        mSubmitTaskButton = (Button) view.findViewById(R.id.button3);
        mSubmitTaskButton.setOnClickListener(this);

        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                MySQLiteHelper databaseHelper = new MySQLiteHelper(getActivity());
                databaseHelper.insertData(mEditText.getText().toString());
                this.dismiss();
        }
    }
}