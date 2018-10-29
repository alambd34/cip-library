package it.lucichkevin.cip.examples.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.dialogs.PickerDialogBuilder;
import it.lucichkevin.cip.examples.R;


public class FragmentsDialogs extends Fragment {

	public FragmentsDialogs(){

	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

		View rootView = inflater.inflate(R.layout.fragment_dialogs, container, false);

		rootView.findViewById(R.id.open_simple_native_dialog).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick( View v ){
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.welcome)
					.setMessage(R.string.app_name)
					.show();
			}
		});

		rootView.findViewById(R.id.open_date_picker_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ){
				PickerDialogBuilder.DatePickerDialog.show( getActivity(), null, new DatePickerDialog.OnDateSetListener(){
					@Override
					public void onDateSet( DatePicker view, int year, int month, int dayOfMonth ){
						Utils.loggerDebug("[DatePickerDialog.OnDateSetListener] year = "+ year +" | month = "+ month +" | dayOfMonth = "+ dayOfMonth );
					}
				});
			}
		});

		rootView.findViewById(R.id.open_time_picker_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ){
				PickerDialogBuilder.TimePickerDialog.show(getActivity(), null, new TimePickerDialog.OnTimeSetListener(){
					@Override
					public void onTimeSet( TimePicker view, int hourOfDay, int minute ){
						Utils.loggerDebug("[TimePickerDialog.OnTimeSetListener] hourOfDay = "+ hourOfDay +" | minute = "+ minute );
					}
				});
			}
		});

		(Utils.findViewById( rootView, R.id.open_number_picker_dialog )).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ){
				PickerDialogBuilder.NumberPickerDialog.show(getActivity(), 0, 10, 8, new PickerDialogBuilder.NumberPickerDialog.OnNumberPickerChangeListener() {
					@Override
					public void onNumberPickerChange( NumberPicker number_picker, int number ){
						Utils.loggerDebug("[PickerDialogBuilder.OnNumberPickerChangeListener] number = "+ number );
					}
				});
			}
		});

		return rootView;
	}

}
