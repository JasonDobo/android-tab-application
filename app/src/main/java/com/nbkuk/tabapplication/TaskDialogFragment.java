package com.nbkuk.tabapplication;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by jason.dobo on 08/05/2015.
 */
public class TaskDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linLayout = new LinearLayout(getActivity());
        Button b = new Button(getActivity());
        b.setText("Hello Button");
        linLayout.addView(b);
        return linLayout;
    }
}
