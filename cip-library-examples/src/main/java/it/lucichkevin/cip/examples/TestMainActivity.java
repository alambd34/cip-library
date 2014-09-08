package it.lucichkevin.cip.examples;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.navigationdrawermenu.DrawerLayoutHelper;
import it.lucichkevin.cip.navigationdrawermenu.ItemDrawerMenu;

import it.lucichkevin.cip.examples.fragments.FragmentMainTest;


public class TestMainActivity extends ActionBarActivity {

    private DrawerLayoutHelper drawerLayoutHelper = null;
    private ItemDrawerMenu[] ARRAY_ITEMS = new ItemDrawerMenu[]{

        //  Duration in hours and minutes
        new ItemDrawerMenu( R.string.test_drawer_arrayitemnumber1, new ItemDrawerMenu.OnClickListener() {
            @Override
            public void onClick() {
                Utils.Toaster( TestMainActivity.this, "I'm number 1" );
            }
        }),

        //
        new ItemDrawerMenu( R.string.test_drawer_arrayitemnumber2, new ItemDrawerMenu.OnClickListener() {
            @Override
            public void onClick() {
                Utils.Toaster( TestMainActivity.this, "I'm number 2" );
            }
        }),

        //
        new ItemDrawerMenu( R.string.test_drawer_arrayitemnumber3, new ItemDrawerMenu.OnClickListener() {
            @Override
            public void onClick() {
                Utils.Toaster( TestMainActivity.this, "I'm number 3" );
            }
        }),

    };


    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        if( savedInstanceState != null ){
            return;
        }

        getFragmentManager().beginTransaction()
                .addToBackStack( "Main" )
                .add( R.id.container, new FragmentMainTest() )
                .commit();

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
    }

//    @Override
//    public boolean onCreateOptionsMenu( Menu menu ){
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.test_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch( item.getItemId() ){
//            case R.id.action_settings:
//                return true;
            case android.R.id.home:
                drawerLayoutHelper.toggle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
