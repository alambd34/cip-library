package it.lucichkevin.cip.examples.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.lucichkevin.cip.examples.R;
import it.lucichkevin.cip.expandableView.ExpandableTextView;

/**
 * Created by Kevin on 27/05/2014.
 */
public class FragmentExpandableTextView extends Fragment {

    public FragmentExpandableTextView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_expandable_text_view, container, false);

        final ExpandableTextView view_expandable = ((ExpandableTextView) rootView.findViewById(R.id.expandable_text_view));
//        view_expandable.setExpanded(true);

        ((Button) rootView.findViewById(R.id.btn_setText)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_expandable.setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                                        "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" +
                                        "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
            }
        });

        return rootView;
    }


}
