package it.lucichkevin.cip.examples.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.dialogs.DialogHelper;
import it.lucichkevin.cip.dialogs.pickers.MinutesPickerDialog;
import it.lucichkevin.cip.dialogs.pickers.TimePickerDialog;
import it.lucichkevin.cip.examples.R;

/**
 * Created by kevin on 08/09/14.
 */
public class FragmentsDialogs extends Fragment{

    public FragmentsDialogs(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dialogs, container, false);

        (Utils.findViewById( rootView, R.id.open_dialoghelper )).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper dialog = new DialogHelper();
                dialog.show();
            }
        });

        (Utils.findViewById( rootView, R.id.open_timepickerdialog )).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(getActivity());
                dialog.show();
            }
        });

        (Utils.findViewById( rootView, R.id.open_minutespickerdialog )).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinutesPickerDialog dialog = new MinutesPickerDialog(getActivity());
                dialog.show();
            }
        });

        return rootView;
    }

}
