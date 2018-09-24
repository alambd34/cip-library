package it.lucichkevin.cip.dialogs.pickers;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.lucichkevin.cip.Utils;

/**
	Create a dialog with a NumberPicker to choose the minutes

	@author	 Kevin Lucich	2014-09-03
	@version	0.1.0
	@since	  0.3.0

*/
public class MinutesPickerDialog extends TimePickerDialog {

	protected MinutesPickerDialog.Callbacks callbacks = new MinutesPickerDialog.EmptyCallbacks(){
		public void onButtonPositiveClicked( Dialog dialog, int minute ){
			super.onButtonPositiveClicked( dialog, minute );
//			Utils.logger("Cip.MinutesPickerDialog", "Callback onButtonCancelClicked( Button, " + String.valueOf(minute) + " ) called!", Utils.LOG_INFO);
		}
		public void onButtonCancelClicked( Dialog dialog, int minute ){
			super.onButtonCancelClicked( dialog, minute );
//			Utils.logger("Cip.MinutesPickerDialog", "Callback onButtonCancelClicked( Button, " + String.valueOf(minute) + " ) called!", Utils.LOG_INFO);
		}
	};

	public MinutesPickerDialog(){
		super();
	}

	public MinutesPickerDialog( Context context ){
		super(context);
	}

	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ){
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		try {
			viewHolder.hour.setVisibility(View.GONE);
			viewHolder.divider.setVisibility(View.GONE);
		}catch( Exception e ){
			e.printStackTrace();
		}

		return dialog;
	}

	@SuppressWarnings("deprecation")
	protected void setCallbacksOfButtons() {
		super.setCallbacksOfButtons();

		((Button) timePickerDialog.findViewById(BUTTON_POSITIVE)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View view ){
				if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
					callbacks.onButtonPositiveClicked( timePickerDialog, timePicker.getMinute() );
				}else{
					callbacks.onButtonPositiveClicked( timePickerDialog, timePicker.getCurrentMinute() );
				}
			}
		});
		((Button) timePickerDialog.findViewById(BUTTON_NEGATIVE)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View view ){
				if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
					callbacks.onButtonCancelClicked( timePickerDialog, timePicker.getMinute() );
				}else{
					callbacks.onButtonCancelClicked( timePickerDialog, timePicker.getCurrentMinute() );
				}
			}
		});
	}


	///////////////////////////////
	//  Getters and Setters

	public void setCallbacks( Callbacks callbacks ){
		this.callbacks = callbacks;
	}



	/////////////////////////////////////
	//  Callbacks

	public static interface Callbacks {
		public void onButtonPositiveClicked( Dialog dialog, int minute );
		public void onButtonCancelClicked( Dialog dialog, int minute );
	}

	public static class EmptyCallbacks implements MinutesPickerDialog.Callbacks{
		public void onButtonPositiveClicked( Dialog dialog, int minute ){
			dialog.dismiss();
		}
		public void onButtonCancelClicked( Dialog dialog, int minute ){
			dialog.dismiss();
		}
	}

}