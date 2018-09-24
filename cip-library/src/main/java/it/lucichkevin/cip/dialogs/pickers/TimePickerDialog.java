package it.lucichkevin.cip.dialogs.pickers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import it.lucichkevin.cip.*;

/**
	Create a dialog with a two NumberPickers to choose hours and minutes

	@author	 Kevin Lucich	2014-09-03
	@since	  0.3.0
*/
public class TimePickerDialog extends DialogFragment {

	protected final static int BUTTON_POSITIVE = R.id.btn_positive;
	protected final static int BUTTON_NEGATIVE = R.id.btn_negative;

	protected Dialog timePickerDialog = null;
	protected TimePicker timePicker = null;
	protected Holder viewHolder = null;

	//  Default behavior - Log (if in debug) and close dialog
	protected Callbacks callbacks = new EmptyCallbacks(){
		public void onButtonPositiveClicked( Dialog dialog, int hour, int minute ){
			super.onButtonPositiveClicked( dialog, hour, minute );
//			Utils.logger("Cip.TimePickerDialog", "Callback onButtonPositiveClicked( Button, "+ String.valueOf(hour) +", "+ String.valueOf(minute) +" ) called!", Utils.LOG_INFO);
		}
		public void onButtonCancelClicked( Dialog dialog, int hour, int minute ){
			super.onButtonCancelClicked( dialog, hour, minute );
//			Utils.logger("Cip.TimePickerDialog", "Callback onButtonCancelClicked( Button, "+ String.valueOf(hour) +", "+ String.valueOf(minute) +" ) called!", Utils.LOG_INFO);
		}
	};

	public TimePickerDialog(){
		this(Utils.getContext());
	}

	@SuppressWarnings("deprecation")
	public TimePickerDialog( Context context ){

		timePickerDialog = new Dialog(context);
		timePickerDialog.setContentView(R.layout.timepicker_layout);
		timePickerDialog.setTitle("TEST");
		timePickerDialog.setCancelable(false);

		timePicker = ((TimePicker) timePickerDialog.findViewById(R.id.timepicker));

		final Calendar c = Calendar.getInstance();

		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
			timePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
			timePicker.setMinute(c.get(Calendar.MINUTE));
		}else{
			timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
		}

		timePicker.setIs24HourView(DateFormat.is24HourFormat(context));

		viewHolder = new Holder(timePicker);
	}

	@Override
	@SuppressWarnings("deprecation")
	public Dialog onCreateDialog( Bundle savedInstanceState ){

		try {
			(viewHolder.hour).setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
				@Override
				public void onValueChange( NumberPicker picker, int oldVal, int newVal ){
					timePicker.setCurrentHour(newVal);
				}
			});

			(viewHolder.minute).setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
				@Override
				public void onValueChange( NumberPicker picker, int oldVal, int newVal ){
					timePicker.setCurrentMinute( newVal );
				}
			});

		}catch( Exception e ){
			e.printStackTrace();
		}

		setCallbacksOfButtons();

		return timePickerDialog;
	}

	@SuppressWarnings("deprecation")
	protected void setCallbacksOfButtons() {

		((Button) timePickerDialog.findViewById(BUTTON_POSITIVE)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View view ){
				if( viewHolder.minute != null ){
					viewHolder.minute.clearFocus();
				}
				if( viewHolder.hour != null ){
					viewHolder.hour.clearFocus();
				}
				callbacks.onButtonPositiveClicked( timePickerDialog, timePicker.getCurrentHour(), timePicker.getCurrentMinute() );
			}
		});
		((Button) timePickerDialog.findViewById(BUTTON_NEGATIVE)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View view ){
				callbacks.onButtonCancelClicked( timePickerDialog, timePicker.getCurrentHour(), timePicker.getCurrentMinute() );
			}
		});
	}

	public void show( FragmentManager fragmentManager ){
		super.show( fragmentManager, "PickerDialog-" + Math.random()*100 );
	}

	///////////////////////////////
	//  Getters and Setters

	@SuppressWarnings("deprecation")
	public int getHour(){
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
			return timePicker.getHour();
		}else{
			return timePicker.getCurrentHour();
		}
	}

	@SuppressWarnings("deprecation")
	public void setHour( int hour ){
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
			timePicker.setHour(hour);
		}else{
			timePicker.setCurrentHour(hour);
		}
	}

	@SuppressWarnings("deprecation")
	public int getMinute(){
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
			return timePicker.getMinute();
		}else{
			return timePicker.getCurrentMinute();
		}
	}

	@SuppressWarnings("deprecation")
	public void setMinute( int minute ){
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
			timePicker.setMinute(minute);
		}else{
			timePicker.setCurrentMinute(minute);
		}
	}

	public void setCallbacks( Callbacks callbacks ){
		this.callbacks = callbacks;
	}

	public void setIs24HourFormat( boolean is24HourFormat ){
		timePicker.setIs24HourView(is24HourFormat);
	}

	public Dialog getDialog(){
		return timePickerDialog;
	}




	/////////////////////////////////////
	//  Callbacks

	public static interface Callbacks {
		public void onButtonPositiveClicked( Dialog dialog, int hour, int minute);
		public void onButtonCancelClicked( Dialog dialog, int hour, int minute);
	}

	public static class EmptyCallbacks implements Callbacks {
		@Override
		public void onButtonPositiveClicked( Dialog dialog, int hour, int minute ){
			dialog.dismiss();
		}
		@Override
		public void onButtonCancelClicked( Dialog dialog, int hour, int minute ){
			dialog.dismiss();
		}
	}



	/**
	 *  Used to hold the elements of TimePicker
	 */
	protected class Holder {

		public NumberPicker hour = null;
		public NumberPicker minute = null;
		public TextView divider = null;

		public Holder( TimePicker timePicker ){

			try {
				Class<?> classForId = Class.forName("com.android.internal.R$id");

				this.hour = (NumberPicker) timePicker.findViewById( (classForId.getField("hour")).getInt(null) );
				this.minute = (NumberPicker) timePicker.findViewById( (classForId.getField("minute")).getInt(null) );
				this.divider = (TextView) timePicker.findViewById( (classForId.getField("divider")).getInt(null) );

			}catch( Exception e ){
				e.printStackTrace();
			}
		}
	}

}