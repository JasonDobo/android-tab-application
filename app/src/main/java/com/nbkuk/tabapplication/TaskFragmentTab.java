package com.nbkuk.tabapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TaskFragmentTab  extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TaskCursorAdapter customAdapter;
    private MySQLiteHelper databaseHelper;
    private Cursor mCursor;

    private Button mAddTaskButton;
    private EditText mNewTaskText;
    private ListView mListView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentTab1.
     */
    public static TaskFragmentTab newInstance() {
        TaskFragmentTab fragment = new TaskFragmentTab();
        return fragment;
    }

    public TaskFragmentTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment_tab1, container, false);

        mAddTaskButton = (Button) view.findViewById(R.id.newtaskbutton);
        mNewTaskText = (EditText) view.findViewById(R.id.newtasktext);
        mListView = (ListView) view.findViewById(R.id.tasklistview);

        mNewTaskText.addTextChangedListener(mTextWatcher);
        mAddTaskButton.setEnabled(false);
        mAddTaskButton.setOnClickListener(this);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseHelper = new MySQLiteHelper(getActivity());
        mCursor = databaseHelper.getAllData();
        customAdapter = new TaskCursorAdapter(getActivity(), mCursor, 0);
        mCursor.getCount(); // force a refresh

        ListView listview = (ListView) getView().findViewById(R.id.tasklistview);
        listview.setAdapter(customAdapter);
        //databaseHelper.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newtaskbutton:
                String saveNewTask = mNewTaskText.getText().toString();
                databaseHelper.insertData(saveNewTask);
                mNewTaskText.setText("");
                mAddTaskButton.setEnabled(false);

                mCursor = databaseHelper.getAllData();
                customAdapter.changeCursor(mCursor);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> var1, View var2, int position, long var4) {
        TaskDialogFragment taskDialog = new TaskDialogFragment();
        FragmentManager fm = getActivity().getFragmentManager();
        Bundle args = new Bundle();

        mCursor.moveToPosition(position);
        int i = mCursor.getColumnIndex("_id");
        int id = mCursor.getInt(i);
        args.putInt("_id", id);

        taskDialog.setArguments(args);
        taskDialog.show(fm, "Task id " + id);
    }

    //  create a textWatcher member
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){
        Button b = (Button) getView().findViewById(R.id.newtaskbutton);
        String s1 = mNewTaskText.getText().toString();

        if(s1.equals("")){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

}
