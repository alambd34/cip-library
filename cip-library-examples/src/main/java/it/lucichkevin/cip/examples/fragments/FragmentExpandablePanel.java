package it.lucichkevin.cip.examples.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.lucichkevin.cip.examples.R;
import it.lucichkevin.cip.expandableView.ExpandablePanel;

/**
 * Created by Kevin on 27/05/2014.
 */
public class FragmentExpandablePanel extends Fragment {

    public FragmentExpandablePanel() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_expandable_panel, container, false);

        ExpandablePanel view_expandable = ((ExpandablePanel) rootView.findViewById(R.id.expandable_panel));
//        view_expandable.setExpanded(true);

        return rootView;
    }


}
