package it.tabaccaiofurbo.com.tabaccaiofurboapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.tabaccaiofurbo.com.tabaccaiofurboapp.R;

;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    private int id;

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        TextView textViewFragment = (TextView) rootView.findViewById(R.id.textViewFragment);
        textViewFragment.setText("ID = "+String.valueOf("id"));
        return rootView;
    }

    public void setId(int id){
        this.id = id;
    }

}
