package it.lucichkevin.cip.preferencesmanager.activity;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import java.util.ArrayList;
import java.util.Date;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.preferencesmanager.activity.pickers.DatePickerPreference;
import it.lucichkevin.cip.preferencesmanager.activity.pickers.MinutePickerPreference;
import it.lucichkevin.cip.preferencesmanager.activity.pickers.TimePickerPreference;


/**
 *  PreferencesListFragment with the list of preferences listed in ArrayList items
 *  @author	Kevin Lucich	(2018-08-13)
 *  @since	CipLibrary v2.1.1
 */
public class PreferencesListFragment extends PreferenceFragment {

	protected ArrayList<ItemPreference> items = new ArrayList<ItemPreference>();

	public PreferencesListFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setPreferenceScreen( createPreferenceHierarchy() );
	}

	protected PreferenceScreen createPreferenceHierarchy() {

		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(getActivity());

//		PreferenceCategory category = newCategory("Test category");
//		root.addPreference(category);

		int size = items.size();

		if( size == 0 ){
			populatePreferencesListWithDefault();
		}

		for( final ItemPreference item : items ){

			Preference preference;

			switch( item.getType() ){

				case ItemPreference.TYPE_LIST:
					preference = new ListPreference(getActivity());
					ArrayList<ItemPreference.PreferenceListEntry> entries_list = item.getEntriesList();

					String[] entry_keys = new String[entries_list.size()];
					String[] entry_values = new String[entries_list.size()];

					for( int i=0; i<entries_list.size(); i++ ){
						ItemPreference.PreferenceListEntry entry = entries_list.get(i);
						entry_keys[i] = entry.getValue();
						entry_values[i] = entry.getLabel();
					}

					((ListPreference) preference).setEntries(entry_keys);
					((ListPreference) preference).setEntryValues(entry_values);
					break;

				case ItemPreference.TYPE_SWITCH:
					preference = new SwitchPreference(getActivity());
					if( item.getDefaultValue() != null ){
						((SwitchPreference) preference).setChecked( (Boolean) item.getDefaultValue() );
					}
					break;

				case ItemPreference.TYPE_TIMEPICKER:
					preference = new TimePickerPreference(getActivity(), null) {
						@Override
						public void onSetTime(int hour, int minute) {
							//  Do nothing...
						}
					};
					break;

				case ItemPreference.TYPE_DATEPICKER:
					preference = new DatePickerPreference(getActivity(), null) {
						@Override
						public void onSetDate(Date date) {
							//  Do nothing...
						}
					};
					break;

				case ItemPreference.TYPE_MINUTEPICKER:
					preference = new MinutePickerPreference(getActivity(), null) {
						@Override
						public void onSetTime( int minute ) {
							//  Do nothing...
						}
					};
					break;

				default:
					//  Type not valid, no will be added the preference :)
					Utils.logger("[CipLibrary -> createPreferenceHierarchy()] Type preference ("+ item.getType() +") unknown", Utils.LOG_ERROR );
					continue;
			}

			if( item.getDefaultValue() != null ){
				preference.setDefaultValue( item.getDefaultValue() );
			}
			preference.setKey(item.getKey());
			preference.setTitle(item.getTitle());
			preference.setSummary(item.getSummary());

			preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange( Preference preference, Object newValue ){
					if( item.getOnPreferenceChangeListener() == null ){
						//  Communicate to Preferences method that new value must be saved
						return true;
					}
					return item.getOnPreferenceChangeListener().onPreferenceChange( preference, newValue );
				}
			});
			preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick( Preference preference ){
					if( item.getOnPreferenceClickListener() == null ){
						//  Communicate to Preferences method that click event is not handled
						return false;
					}
					return item.getOnPreferenceClickListener().onPreferenceClick( preference );
				}
			});

			root.addPreference(preference);
		}

		return root;
	}

	protected PreferenceCategory newCategory( String title ){
		PreferenceCategory inlinePrefCat = new PreferenceCategory(getActivity());
		inlinePrefCat.setTitle( title );
		return inlinePrefCat;
	}

	protected void populatePreferencesListWithDefault(){

		ItemPreference DEBUG_LOG = new ItemPreference("DEBUG_LOG", "Debug Log", "Check this if you want debug logging enabled on log cat", ItemPreference.TYPE_SWITCH, false );

		ItemPreference TOASTER_TO_LOGCAT = new ItemPreference("TOASTER_TO_LOGCAT", "Toast vs. Log", "Check this and all toasts will be converted in Info Log", ItemPreference.TYPE_SWITCH, false );

		items.add(DEBUG_LOG);
		items.add(TOASTER_TO_LOGCAT);
	}

	protected void setItems( ArrayList<ItemPreference> items ){
		this.items = items;
	}

	public void addItem( ItemPreference item ){
		items.add(item);
	}

}