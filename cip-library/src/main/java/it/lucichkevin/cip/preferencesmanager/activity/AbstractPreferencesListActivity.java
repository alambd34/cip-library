package it.lucichkevin.cip.preferencesmanager.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import java.util.ArrayList;


/**
 *  Create a base Activity with inside a Fragment (instance of AbstractPreferencesListFragment) with the list of Preferences
 *
 *  <code>
 *	  public class MyPreferencesListActivity extends PreferencesListActivity {
 *		  protected void addItems() {
 *			  //  Add your keys here... :)
 *			  //  items.add(new ItemPreference("KEY", R.string.KEY_TITLE, R.string.KEY_SUMMARY, ItemPreference.TYPE_X ));
 *		  }
 *	  }
 *  </code>
 *
 *  @author	 Kevin Lucich	11/09/2014
 *  @since	  CipLibrary v0.5.0
 */
public abstract class AbstractPreferencesListActivity extends Activity {

	protected AbstractPreferencesListFragment fragment;
	protected ArrayList<ItemPreference> items = new ArrayList<ItemPreference>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ItemPreference.setContext( AbstractPreferencesListActivity.this );

		FrameLayout frame = new FrameLayout( this, null );
		addContentView(frame, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		fragment = new AbstractPreferencesListFragment() {
			@Override
			protected void populatePreferencesList() {
				//  Do nothing :)
			}
		};

		populatePreferencesList();

		fragment.setItems(items);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, fragment )
			.commit();

		ActionBar actionBar = getActionBar();
		if( actionBar != null ){
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ){
		switch( item.getItemId() ){
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected abstract void populatePreferencesList();

	/**
	 *  Alias to call the method populatePreferencesListWithDefault() to AbstractPreferencesListFragment
	 */
	protected void populatePreferencesListWithDefault(){
		fragment.populatePreferencesListWithDefault();
	}



	/////////////////////////////////////////
	//  Getters and setters

	/**
	 *  Public method to get the instance of AbstractPreferencesListFragment created
	 */
	public AbstractPreferencesListFragment getFragment(){
		return fragment;
	}

	public ArrayList<ItemPreference> getItems(){
		return items;
	}

}