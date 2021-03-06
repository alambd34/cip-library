package it.lucichkevin.cip.examples;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.activity_drawer_menu.AbstractActivityWithActionBarMenu;
import it.lucichkevin.cip.activity_drawer_menu.DrawerItemMenu;
import it.lucichkevin.cip.examples.fragments.FragmentMainTest;
import it.lucichkevin.cip.kalima.RequesterStatisticsActivity;


public class TestMainActivity extends AbstractActivityWithActionBarMenu {


	public TestMainActivity(){

		items_menu_list = new DrawerItemMenu[]{

			new DrawerItemMenu( R.string.test_drawer_arrayitemnumber1, R.drawable.ic_menu_camera, new OnClickItemMenuListener(1) ),

			new DrawerItemMenu( R.string.test_drawer_arrayitemnumber2, new OnClickItemMenuListener(2)),

			new DrawerItemMenu( R.string.test_drawer_arrayitemnumber3, new OnClickItemMenuListener(3)),

			new DrawerItemMenu( R.string.open_requester_statistics, RequesterStatisticsActivity.class ),

			new DrawerItemMenu( R.string.show_prefereces, TestPreferencesListActivity.class)

		};

	}

	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);

		if( savedInstanceState != null ){
			return;
		}

		((TextView) getNavigationHeaderView().findViewById(R.id.nav_header_title)).setText(R.string.app_name);

		getFragmentManager().beginTransaction()
			.addToBackStack("Main")
			.add(R.id.container, new FragmentMainTest())
			.commit();

	}

	@Override
	protected Integer getContentActivity() {
		return R.layout.activity_with_fragment;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ){

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


	private class OnClickItemMenuListener extends DrawerItemMenu.OnClickListener {
		private int index_item;
		public OnClickItemMenuListener( int index_item ){
			this.index_item = index_item;
		}
		@Override
		public void onClick() {
			Utils.Toaster( TestMainActivity.this, "I'm number "+ index_item );
		}
	}
}