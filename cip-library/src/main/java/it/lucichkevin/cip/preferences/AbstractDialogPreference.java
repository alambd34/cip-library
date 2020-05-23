package it.lucichkevin.cip.preferences;

import android.content.Context;
import androidx.preference.DialogPreference;
import androidx.annotation.StringRes;


public abstract class AbstractDialogPreference extends DialogPreference implements Preference {

	private Object default_value = null;

	//  null OnPreferenceChangeListener
	public AbstractDialogPreference( Context context, String key, @StringRes int title, @StringRes int summary ){
		this( context, key, title, summary, null, null, null );
	}

	public AbstractDialogPreference( Context context, String key, @StringRes int title, @StringRes int summary, Object default_value ){
		this( context, key, title, summary, null, null, default_value );
	}

	//  OnPreferenceChangeListener set
	public AbstractDialogPreference(Context context, String key, @StringRes int title, @StringRes int summary, androidx.preference.Preference.OnPreferenceChangeListener changeListener, androidx.preference.Preference.OnPreferenceClickListener clickListener ){
		this( context, key, title, summary, changeListener, clickListener, null );
	}

	protected AbstractDialogPreference( Context context, String key, @StringRes int title, @StringRes int summary, androidx.preference.Preference.OnPreferenceChangeListener changeListener, androidx.preference.Preference.OnPreferenceClickListener clickListener, Object default_value ){
		super( context, null );

		setPositiveButtonText("Set");
		setNegativeButtonText("Cancel");

		setKey(key);
		setTitle(title);
		setSummary(summary);
		setDefaultValue(default_value);
	}

	@Override
	public Object getDefaultValue(){
		return default_value;
	}
	public void setDefaultValue( Object default_value ){
		this.default_value = default_value;
	}

}
