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
	protected ArrayList<CategoryPreference> categories = new ArrayList<CategoryPreference>();

	public PreferencesListFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setPreferenceScreen( createPreferenceHierarchy() );
	}

	protected PreferenceScreen createPreferenceHierarchy() {

		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(getActivity());

		if( (items.size() == 0) && (categories.size() == 0) ){
			populatePreferencesListWithDefault();
		}

		for( ItemPreference item : items ){
			root.addPreference(createPreference(item));
		}

		for( CategoryPreference category : categories ){

			PreferenceCategory preferenceCategory = new PreferenceCategory(getActivity());
			preferenceCategory.setTitle( category.getName() );
			root.addPreference(preferenceCategory);

			for( ItemPreference item : category.getItems() ){
				preferenceCategory.addPreference(createPreference(item));
			}
		}

		return root;
	}

	protected Preference createPreference( final ItemPreference item ){

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
//				//  Type not valid, no will be added the preference :)
				throw new IllegalArgumentException("[CipLibrary -> createPreferenceHierarchy()] Type preference ("+ item.getType() +") unknown");
		}

		if( item.getDefaultValue() != null ){
			preference.setDefaultValue( item.getDefaultValue() );
		}
		preference.setKey(item.getKey());
		preference.setTitle(item.getTitle());
		preference.setSummary(item.getSummary());

		preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange( Preference preference, Object newValue ) {
				//  Communicate to Preferences method that new value must be saved
				return (item.getOnPreferenceChangeListener() == null) || item.getOnPreferenceChangeListener().onPreferenceChange(preference, newValue);
			}
		});
		preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick( Preference preference ) {
				//  Communicate to Preferences method that click event is not handled
				return (item.getOnPreferenceClickListener() != null) && item.getOnPreferenceClickListener().onPreferenceClick(preference);
			}
		});

		return preference;
	}

	protected void populatePreferencesListWithDefault(){

		ArrayList<ItemPreference> debug_items = new ArrayList<ItemPreference>();

		ItemPreference DEBUG_LOG = new ItemPreference("DEBUG_LOG", "Debug Log", "Check this if you want debug logging enabled on log cat", ItemPreference.TYPE_SWITCH, false );
		debug_items.add(DEBUG_LOG);

		ItemPreference TOASTER_TO_LOGCAT = new ItemPreference("TOASTER_TO_LOGCAT", "Toast vs. Log", "Check this and all toasts will be converted in Info Log", ItemPreference.TYPE_SWITCH, false );
		debug_items.add(TOASTER_TO_LOGCAT);

		addCategory(new CategoryPreference("Debug", debug_items ));
	}

	protected void setItems( ArrayList<ItemPreference> items ){
		this.items = items;
	}

	public void addItem( ItemPreference item ){
		items.add(item);
	}

	public void addCategory( CategoryPreference category ){
		categories.add(category);
	}

}