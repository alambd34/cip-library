package it.lucichkevin.cip.preferences;

import android.content.Context;
import android.preference.DialogPreference;


public abstract class AbstractDialogPreference extends DialogPreference implements Preference {

	//  null OnPreferenceChangeListener
	public AbstractDialogPreference( Context context, String key, int title, int summary ){
		this( context, key, context.getString(title), context.getString(summary), null, null, null );
	}

	public AbstractDialogPreference( Context context, String key, int title, int summary, Object default_value ){
		this( context, key, context.getString(title), context.getString(summary), null, null, default_value );
	}

	//  OnPreferenceChangeListener set
	public AbstractDialogPreference(Context context, String key, int title, int summary, android.preference.Preference.OnPreferenceChangeListener changeListener, android.preference.Preference.OnPreferenceClickListener clickListener ){
		this( context, key, context.getString(title), context.getString(summary), changeListener, clickListener, null );
	}

	protected AbstractDialogPreference(Context context, String key, String title, String summary, android.preference.Preference.OnPreferenceChangeListener changeListener, android.preference.Preference.OnPreferenceClickListener clickListener, Object default_value ){
		super( context, null );

		setPositiveButtonText("Set");
		setNegativeButtonText("Cancel");

		setKey(key);
		setTitle(title);
		setSummary(summary);
		setDefaultValue(default_value);
	}

	@Override
	public Object getDefaultValue() {
		return null;
	}
}
