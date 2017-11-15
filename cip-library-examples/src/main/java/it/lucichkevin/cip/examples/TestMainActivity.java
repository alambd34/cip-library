package it.lucichkevin.cip.examples;

import android.os.Bundle;
import android.view.MenuItem;
import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.examples.fragments.FragmentMainTest;
import it.lucichkevin.cip.navigationdrawermenu.AbstractActivityWithActionBarMenu;
import it.lucichkevin.cip.navigationdrawermenu.NavigationItemMenu;


public class TestMainActivity extends AbstractActivityWithActionBarMenu {

	protected NavigationItemMenu[] ARRAY_ITEMS = new NavigationItemMenu[]{

		new NavigationItemMenu( R.string.test_drawer_arrayitemnumber1, R.drawable.ic_menu_camera, new NavigationItemMenu.OnClickListener() {
			@Override
			public void onClick() {
				Utils.Toaster( TestMainActivity.this, "I'm number 1" );
			}
		}),

		new NavigationItemMenu( R.string.test_drawer_arrayitemnumber2, new NavigationItemMenu.OnClickListener() {
			@Override
			public void onClick() {
				Utils.Toaster( TestMainActivity.this, "I'm number 2" );
			}
		}),

		new NavigationItemMenu( R.string.test_drawer_arrayitemnumber3, new NavigationItemMenu.OnClickListener() {
			@Override
			public void onClick() {
				Utils.Toaster( TestMainActivity.this, "I'm number 3" );
			}
		}),

		new NavigationItemMenu( R.string.test_drawer_arrayitemnumber4, new NavigationItemMenu.OnClickListener() {
			@Override
			public void onClick(){
				Utils.Toaster( TestMainActivity.this, "Normal click" );
			}
		}),

		new NavigationItemMenu( R.string.show_prefereces, TestPreferencesListActivity.class),
	};


	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);

		NavigationViewHandling
				.inflateHeaderView(R.layout.navigation_header)
				.addItemsMenu(ARRAY_ITEMS);


		if( savedInstanceState != null ){
			return;
		}

		getFragmentManager().beginTransaction()
			.addToBackStack("Main")
			.add(R.id.container, new FragmentMainTest())
			.commit();

	}

	@Override
	protected int getContentActivity() {
		return R.layout.activity_test_main;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ){
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch( item.getItemId() ){
			case android.R.id.home:
//				drawerLayoutHelper.toggle();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Utils.init(TestMainActivity.this);
	}

}