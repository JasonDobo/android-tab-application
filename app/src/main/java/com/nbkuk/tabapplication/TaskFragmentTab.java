package com.nbkuk.tabapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by jason.dobo on 20/05/2015.
 */
public class TaskFragmentTab  extends Fragment implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private TaskCursorAdapter customAdapter;
    private MySQLiteHelper databaseHelper;

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
        mListView.setOnItemLongClickListener(this);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseHelper = new MySQLiteHelper(getActivity());
        Cursor cursor = databaseHelper.getAllData();
        customAdapter = new TaskCursorAdapter(getActivity(), cursor, 0);

        cursor.getCount(); // force a refresh

        ListView listview = (ListView) getView().findViewById(R.id.tasklistview);
        listview.setAdapter(customAdapter);
        //databaseHelper.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newtaskbutton:
                Cursor jason = databaseHelper.getAllData();
                jason.moveToFirst();
                int j = jason.getInt(0);
                jason = databaseHelper.getData(j);

                Cursor cursor = databaseHelper.getAllData();
                cursor.moveToFirst();
                int firstNameColIndex = cursor.getColumnIndex("_id");
                String jasona = cursor.getString(firstNameColIndex);

                FragmentManager fm = getActivity().getFragmentManager();
                MyDialogFragment editNameDialog = MyDialogFragment.newInstance(firstNameColIndex);
                editNameDialog.show(fm, "fragment_edit_name");
                //databaseHelper.

                /*databaseHelper.insertData(mNewTaskText.getText().toString());
                mNewTaskText.setText("");
                mAddTaskButton.setEnabled(false);

                customAdapter.changeCursor(databaseHelper.getAllData());*/
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("long clicked", "pos: " + i);
        return true;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String jaosn = "jason";

    }
}
