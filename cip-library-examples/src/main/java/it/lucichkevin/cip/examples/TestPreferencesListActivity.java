package it.lucichkevin.cip.examples;

import it.lucichkevin.cip.preferencesmanager.activity.AbstractPreferencesListActivity;
import it.lucichkevin.cip.preferencesmanager.activity.ItemPreference;

/**
 * Created by kevin on 11/09/14.
 */
public class TestPreferencesListActivity extends AbstractPreferencesListActivity {

	@Override
	public void populatePreferencesList() {
		super.populatePreferencesListWithDefault();

		addItem(new ItemPreference( "TEST_DATEPICKER", R.string.test_title_first_preference, R.string.test_summary_first_preference, ItemPreference.TYPE_DATEPICKER ));

		addItem(new ItemPreference( "TEST_TIMEPICKER", R.string.test_title_second_preference, R.string.test_summary_second_preference, ItemPreference.TYPE_TIMEPICKER ));

		addItem(new ItemPreference( "TEST_SWITCH", R.string.test_title_third_preference, R.string.test_summary_third_preference, ItemPreference.TYPE_SWITCH, true ));

//		addItem(new ItemPreference( YourSettings.PAUSE_DURATION, R.string.change_break_time, R.string.empty_string, ItemPreference.TYPE_MINUTEPICKER, 15 ));
//
//		addItem(new ItemPreference( YourSettings.NOTIFICATIONS_ENABLED, R.string.setting_notifications_enabled, R.string.change_minimum_break_time_desc, ItemPreference.TYPE_SWITCH, true ));

//		addItem(new ItemPreference( YourSettings.MINIMUM_PAUSE_DURATION, R.string.change_minimum_break_time, R.string.change_minimum_break_time_desc, ItemPreference.TYPE_TIMEPICKER, 0 ));
//
//		addItem(new ItemPreference( YourSettings.PAUSE_COUNTED_FOR_EXIT, R.string.setting_pauses_count_for_exit, R.string.setting_pauses_count_for_exit_desc, ItemPreference.TYPE_SWITCH, false ));

		//  Others items...
	}

}
