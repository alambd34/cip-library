package it.lucichkevin.cip.preferences.activity;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import java.util.ArrayList;

import it.lucichkevin.cip.R;
import it.lucichkevin.cip.preferences.CategoryPreference;
import it.lucichkevin.cip.preferences.DatePickerPreference;
import it.lucichkevin.cip.preferences.NumberPickerPreference;
import it.lucichkevin.cip.preferences.Preference;
import it.lucichkevin.cip.preferences.ListPreference;
import it.lucichkevin.cip.preferences.PreferencesManager;
import it.lucichkevin.cip.preferences.SwitchPreference;
import it.lucichkevin.cip.preferences.TimePickerPreference;


/**
 *  PreferencesListFragment with the list of preferences listed in ArrayList items
 *  @author	Kevin Lucich	(2018-08-13)
 *  @since	CipLibrary v2.1.1
 */
public class PreferencesListFragment extends PreferenceFragmentCompat {

	protected ArrayList<Preference> items = new ArrayList<>();
	protected ArrayList<CategoryPreference> categories = new ArrayList<CategoryPreference>();

	public PreferencesListFragment() {

	}

	@Override
	public void onDisplayPreferenceDialog( androidx.preference.Preference preference ) {

		DialogFragment dialogFragment = null;

		if( preference instanceof NumberPickerPreference ){
			dialogFragment = NumberPickerDialogPreferenceFragmentCompat.newInstance(preference.getKey());
		} else if( preference instanceof TimePickerPreference ){
			dialogFragment = TimePickerDialogPreferenceFragmentCompat.newInstance(preference.getKey());
		} else if( preference instanceof DatePickerPreference ){
			dialogFragment = DatePickerDialogPreferenceFragmentCompat.newInstance(preference.getKey());
		}

		if( dialogFragment != null ){
			dialogFragment.setTargetFragment(this, 0);
			dialogFragment.show(getParentFragmentManager(), null);
		}else{
			super.onDisplayPreferenceDialog(preference);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setPreferenceScreen( createPreferenceHierarchy() );
	}

	@Override
	public void onCreatePreferences( Bundle savedInstanceState, String rootKey ) {

	}

	protected PreferenceScreen createPreferenceHierarchy() {

		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(getActivity());

		if( (items.size() == 0) && (categories.size() == 0) ){
			populatePreferencesListWithDefault();
		}

		for( Preference item : items ){
			root.addPreference(createPreference(item));
		}

		for( CategoryPreference category : categories ){

			PreferenceCategory preferenceCategory = new PreferenceCategory(getActivity());
			preferenceCategory.setTitle( category.getName() );
			root.addPreference(preferenceCategory);

			for( Preference item : category.getItems() ){
				preferenceCategory.addPreference(createPreference(item));
			}
		}

		return root;
	}

	protected androidx.preference.Preference createPreference( final Preference item ){

		androidx.preference.Preference preference = null;


		if( item instanceof SwitchPreference ){
			preference = new androidx.preference.SwitchPreference(getActivity());
		}else if( item instanceof ListPreference ){
			ArrayList<ListPreference.Entry> entries_list = ((ListPreference) item).getEntriesList();

			String[] entry_keys = new String[entries_list.size()];
			String[] entry_values = new String[entries_list.size()];

			for( int i=0; i<entries_list.size(); i++ ){
				ListPreference.Entry entry = entries_list.get(i);
				entry_keys[i] = entry.getLabel();	// label show to user
				entry_values[i] = entry.getValue();	//	value saved into preferences
			}

			preference = new androidx.preference.ListPreference(getActivity());
			((androidx.preference.ListPreference) preference).setEntries(entry_keys);
			((androidx.preference.ListPreference) preference).setEntryValues(entry_values);
		}else if( item instanceof NumberPickerPreference ){
			preference = (NumberPickerPreference) item;
		}else if( item instanceof TimePickerPreference ){
			preference = (TimePickerPreference) item;
		}else if( item instanceof DatePickerPreference ){
			preference = (DatePickerPreference) item;
		}


		if( item.getDefaultValue() != null ){
			preference.setDefaultValue( item.getDefaultValue() );
		}

		preference.setKey(item.getKey());
		preference.setTitle(item.getTitle());
		preference.setSummary(item.getSummary());

		if( !((item instanceof NumberPickerPreference) || (item instanceof TimePickerPreference) || (item instanceof DatePickerPreference)) ){

			preference.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(androidx.preference.Preference preference, Object newValue ) {
					//  Communicate to Preferences method that new value must be saved
					return (item.getOnPreferenceChangeListener() == null) || item.getOnPreferenceChangeListener().onPreferenceChange(preference, newValue);
				}
			});

			preference.setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(androidx.preference.Preference preference ) {
					//  Communicate to Preferences method that click event is not handled
					return (item.getOnPreferenceClickListener() != null) && item.getOnPreferenceClickListener().onPreferenceClick(preference);
				}
			});
		}

		return preference;
	}

	protected void populatePreferencesListWithDefault(){

		ArrayList<Preference> debug_items = new ArrayList<Preference>();

		debug_items.add(new SwitchPreference(PreferencesManager.SHOULD_BE_LOG_TO_LOGCAT, R.string.should_be_log_to_logcat_title, R.string.should_be_log_to_logcat_desc ));

		addCategory(new CategoryPreference("Debug", debug_items ));
	}

	protected void setItems( ArrayList<Preference> items ){
		this.items = items;
	}

	public void addItem( Preference item ){
		items.add(item);
	}

	public void addCategory( CategoryPreference category ){
		categories.add(category);
	}

}