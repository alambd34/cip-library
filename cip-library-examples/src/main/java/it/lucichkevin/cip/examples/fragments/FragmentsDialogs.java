package it.lucichkevin.cip.examples.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.dialogs.AlertDialogHelper;
import it.lucichkevin.cip.dialogs.pickers.MinutesPickerDialog;
import it.lucichkevin.cip.dialogs.pickers.TimePickerDialog;
import it.lucichkevin.cip.examples.R;


public class FragmentsDialogs extends Fragment {

	public FragmentsDialogs(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_dialogs, container, false);

		(Utils.findViewById( rootView, R.id.open_dialoghelper )).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ){
				AlertDialogHelper.create(getActivity()).setTitle(R.string.dialoghelper_title);
			}
		});

		(Utils.findViewById( rootView, R.id.open_timepickerdialog )).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ){
				(new TimePickerDialog()).show( getFragmentManager(), "TimePickerDialog" );
			}
		});

		(Utils.findViewById( rootView, R.id.open_minutespickerdialog )).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ){
				(new MinutesPickerDialog()).show( getFragmentManager(), "MinutesPickerDialog" );
			}
		});

		return rootView;
	}

}
