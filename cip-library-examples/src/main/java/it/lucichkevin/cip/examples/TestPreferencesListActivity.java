package it.lucichkevin.cip.examples;

import android.preference.Preference;

import java.util.ArrayList;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.preferencesmanager.activity.AbstractPreferencesListActivity;
import it.lucichkevin.cip.preferencesmanager.activity.CategoryPreference;
import it.lucichkevin.cip.preferencesmanager.activity.ItemPreference;

/**
 * Created by kevin on 11/09/14.
 */
public class TestPreferencesListActivity extends AbstractPreferencesListActivity {

	@Override
	public void populatePreferencesList() {
		super.populatePreferencesListWithDefault();

		ArrayList<ItemPreference> test_category = new ArrayList<ItemPreference>();

		test_category.add(new ItemPreference( "TEST_DATEPICKER", R.string.test_title_first_preference, R.string.test_summary_first_preference, ItemPreference.TYPE_DATEPICKER ));

		test_category.add(new ItemPreference( "TEST_TIMEPICKER", R.string.test_title_second_preference, R.string.test_summary_second_preference, ItemPreference.TYPE_TIMEPICKER ));

		test_category.add(new ItemPreference( "TEST_SWITCH", R.string.test_title_third_preference, R.string.test_summary_third_preference, ItemPreference.TYPE_SWITCH, true ));

		addCategory(new CategoryPreference("Test Category", test_category ));

		ItemPreference ip = new ItemPreference( "TEST LIST OPTIONS", R.string.test_title_fourth_preference, R.string.test_summary_fourth_preference, ItemPreference.TYPE_LIST );
		ip.setEntriesList(getHowOftenOptions());
		addItem(ip);

		//  Others items...
	}



	private ArrayList<ItemPreference.PreferenceListEntry> getHowOftenOptions(){

		ArrayList<ItemPreference.PreferenceListEntry> entries = new ArrayList<ItemPreference.PreferenceListEntry>();

		entries.add(new ItemPreference.PreferenceListEntry("10", "10 secondi") );
		entries.add(new ItemPreference.PreferenceListEntry( "20", "20 secondi") );
		entries.add(new ItemPreference.PreferenceListEntry( "30", "30 secondi") );
		entries.add(new ItemPreference.PreferenceListEntry( "60", "1 minuto") );
		entries.add(new ItemPreference.PreferenceListEntry( "120", "2 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "180", "3 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "300", "5 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "600", "10 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "900", "15 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "1200", "20 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "1500", "25 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "1800", "30 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "2700", "45 minuti") );
		entries.add(new ItemPreference.PreferenceListEntry( "3600", "1 ora") );
		entries.add(new ItemPreference.PreferenceListEntry( "7200", "2 ore") );

		return entries;
	}

}
