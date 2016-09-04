package xyz.zhenhua.smartcom.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.zhenhua.smartcom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpListFragment extends Fragment {


    public HelpListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_list, container, false);
    }

}
