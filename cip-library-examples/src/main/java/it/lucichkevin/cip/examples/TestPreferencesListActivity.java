package it.lucichkevin.cip.examples;

import java.util.ArrayList;
import java.util.Date;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.preferences.DatePickerPreference;
import it.lucichkevin.cip.preferences.Preference;
import it.lucichkevin.cip.preferences.ListPreference;
import it.lucichkevin.cip.preferences.SwitchPreference;
import it.lucichkevin.cip.preferences.TimePickerPreference;
import it.lucichkevin.cip.preferences.activity.AbstractPreferencesListActivity;
import it.lucichkevin.cip.preferences.CategoryPreference;


public class TestPreferencesListActivity extends AbstractPreferencesListActivity {

	@Override
	public void populatePreferencesList(){
		super.populatePreferencesListWithDefault();

		ArrayList<Preference> test_category = new ArrayList<>();

		DatePickerPreference dpp = new DatePickerPreference( this, "TEST_DATEPICKER", R.string.test_title_first_preference, R.string.test_summary_first_preference);
		dpp.setOnPreferenceChangeListener(new DatePickerPreference.OnDatePickerPreferenceChangeListener(){
			@Override
			public boolean onDatePickerPreferenceChange(android.preference.Preference preference, Date date ){
				Utils.logger( "DatePickerPreference.onDatePickerPreferenceChange => "+ String.valueOf(date), Utils.LOG_DEBUG );
				return true;
			}
		});
		test_category.add(dpp);

		TimePickerPreference tpp = new TimePickerPreference( this,"TEST_TIMEPICKER", R.string.test_title_second_preference, R.string.test_summary_second_preference);
		tpp.setOnPreferenceChangeListener(new TimePickerPreference.OnTimePickerPreferenceChangeListener(){
			@Override
			public boolean onTimePickerPreferenceChange( android.preference.Preference preference, int hours, int minutes ){
				Utils.logger( "TimePickerPreference.onTimePickerPreferenceChange => h="+ String.valueOf(hours) +" / m="+ String.valueOf(minutes), Utils.LOG_DEBUG );
				return true;
			}
		});
		test_category.add(tpp);

		SwitchPreference sp = new SwitchPreference( "TEST_SWITCH", R.string.test_title_third_preference, R.string.test_summary_third_preference);
		sp.setOnPreferenceChangeListener(new SwitchPreference.OnSwitchPreferenceChangeListener(){
			@Override
			public boolean onSwitchPreferenceChange( android.preference.Preference preference, Boolean newValue ){
				Utils.logger( "SwitchPreference.onSwitchPreferenceChange => "+ newValue, Utils.LOG_DEBUG );
				return true;
			}
		});
		test_category.add(sp);

		addCategory(new CategoryPreference("Test Category", test_category ));

		ListPreference ip = new ListPreference( "TEST LIST OPTIONS", R.string.test_title_fourth_preference, R.string.test_summary_fourth_preference );
		ip.setEntriesList(getHowOftenOptions());
		ip.setOnPreferenceChangeListener(new ListPreference.OnListPreferenceChangeListener(){
			@Override
			public boolean onListPreferenceChange( android.preference.Preference preference, String newValue ){
				Utils.logger( "ListPreference.onPreferenceChange => "+ newValue, Utils.LOG_DEBUG );
				return false;
			}
		});
		addItem(ip);

		//  Others items...
	}



	private ArrayList<ListPreference.Entry> getHowOftenOptions(){

		ArrayList<ListPreference.Entry> entries = new ArrayList<>();

		entries.add(new ListPreference.Entry("10", "10 secondi") );
		entries.add(new ListPreference.Entry( "20", "20 secondi") );
		entries.add(new ListPreference.Entry( "30", "30 secondi") );
		entries.add(new ListPreference.Entry( "60", "1 minuto") );
		entries.add(new ListPreference.Entry( "120", "2 minuti") );
		entries.add(new ListPreference.Entry( "180", "3 minuti") );
		entries.add(new ListPreference.Entry( "300", "5 minuti") );
		entries.add(new ListPreference.Entry( "600", "10 minuti") );
		entries.add(new ListPreference.Entry( "900", "15 minuti") );
		entries.add(new ListPreference.Entry( "1200", "20 minuti") );
		entries.add(new ListPreference.Entry( "1500", "25 minuti") );
		entries.add(new ListPreference.Entry( "1800", "30 minuti") );
		entries.add(new ListPreference.Entry( "2700", "45 minuti") );
		entries.add(new ListPreference.Entry( "3600", "1 ora") );
		entries.add(new ListPreference.Entry( "7200", "2 ore") );

		return entries;
	}

}
