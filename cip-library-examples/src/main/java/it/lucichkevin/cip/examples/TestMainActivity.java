package it.lucichkevin.cip.examples;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.navigationdrawermenu.DrawerLayoutHelper;
import it.lucichkevin.cip.navigationdrawermenu.ItemDrawerMenu;

import it.lucichkevin.cip.examples.fragments.FragmentMainTest;


public class TestMainActivity extends ActionBarActivity {

    private DrawerLayoutHelper drawerLayoutHelper = null;
    private ItemDrawerMenu[] ARRAY_ITEMS = new ItemDrawerMenu[]{

        new ItemDrawerMenu( R.string.test_drawer_arrayitemnumber1, new ItemDrawerMenu.OnClickListener() {
            @Override
            public void onClick() {
                Utils.Toaster( TestMainActivity.this, "I'm number 1" );
            }
        }),

        new ItemDrawerMenu( R.string.test_drawer_arrayitemnumber2, new ItemDrawerMenu.OnClickListener() {
            @Override
            public void onClick() {
                Utils.Toaster( TestMainActivity.this, "I'm number 2" );
            }
        }),

        new ItemDrawerMenu( R.string.test_drawer_arrayitemnumber3, new ItemDrawerMenu.OnClickListener() {
            @Override
            public void onClick() {
                Utils.Toaster( TestMainActivity.this, "I'm number 3" );
            }
        }),

        new ItemDrawerMenu( R.string.show_prefereces, TestPreferencesListActivity.class),
    };


    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        drawerLayoutHelper = new DrawerLayoutHelper( TestMainActivity.this, R.id.drawer_layout, R.id.drawer_list_view, ARRAY_ITEMS, new DrawerLayoutHelper.Callbacks() {
            @Override
            public void onDrawerOpen( Activity activity, View drawerView) {
                Utils.Toaster( activity, "Drawer opened!");
            }
            @Override
            public void onDrawerClose( Activity activity, View drawerView) {
                Utils.Toaster( activity, "Drawer closed!");
            }
        });

        if( savedInstanceState != null ){
            return;
        }

        getSupportFragmentManager().beginTransaction()
            .addToBackStack("Main")
            .add(R.id.container, new FragmentMainTest())
            .commit();

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch( item.getItemId() ){
            case android.R.id.home:
                drawerLayoutHelper.toggle();
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