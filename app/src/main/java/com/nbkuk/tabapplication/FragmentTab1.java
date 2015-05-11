package com.nbkuk.tabapplication;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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


public class FragmentTab1 extends Fragment implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    private TaskCursorAdapter customAdapter;
    private MySQLiteHelper databaseHelper;

    //private OnFragmentInteractionListener mListener;

    private Button mAddTaskButton;
    private Button mListAddButton;
    private Button mListTestButton;
    private EditText mNewTaskText;
    private ListView mListView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentTab1.
     */
    public static FragmentTab1 newInstance() {
        FragmentTab1 fragment = new FragmentTab1();
        return fragment;
    }

    public FragmentTab1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab1, container, false);

        mAddTaskButton = (Button) view.findViewById(R.id.addnewtask);
        mListAddButton = (Button) view.findViewById(R.id.listAddButton);
        mListTestButton = (Button) view.findViewById(R.id.button2);
        mNewTaskText = (EditText) view.findViewById(R.id.newtasktext);
        mListView = (ListView) view.findViewById(R.id.tasklistview);

        mNewTaskText.addTextChangedListener(mTextWatcher);
        mAddTaskButton.setEnabled(false);
        mAddTaskButton.setOnClickListener(this);
        mListAddButton.setOnClickListener(this);
        mListTestButton.setOnClickListener(this);
        mListView.setOnItemLongClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseHelper = new MySQLiteHelper(getActivity());
        Cursor cursor = databaseHelper.getAllData();

        customAdapter = new TaskCursorAdapter(getActivity(), cursor, 0);

        cursor.getCount();

        ListView listview = (ListView) getView().findViewById(R.id.tasklistview);
        listview.setAdapter(customAdapter);
        //databaseHelper.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addnewtask:
                databaseHelper.insertData(mNewTaskText.getText().toString());
                mNewTaskText.setText("");
                mAddTaskButton.setEnabled(false);

                customAdapter.changeCursor(databaseHelper.getAllData());
                break;
            case R.id.listAddButton:

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                BlankFragment fragment = new BlankFragment();
                Fragment newFragment = fragment.newInstance("Jason", "Dobo");
                ft.add(this.getId(), newFragment);
                ft.commit();

                break;
            case R.id.button2:
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_task);
                dialog.setTitle("Jason Dobo");

                dialog.show();

                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("long clicked","pos: " + i);
        return true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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
        Button b = (Button) getView().findViewById(R.id.addnewtask);

        String s1 = mNewTaskText.getText().toString();

        if(s1.equals("")){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

}

