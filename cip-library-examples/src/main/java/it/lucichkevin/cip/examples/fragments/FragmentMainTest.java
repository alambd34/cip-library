package it.lucichkevin.cip.examples.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import it.lucichkevin.cip.examples.R;
import it.lucichkevin.cip.ObjectAdapter;

/**
 * Created by Kevin on 27/05/2014.
 */
public class FragmentMainTest extends Fragment {

    private ArrayList<Example> examplesList = new ArrayList<Example>();
    private Example[] examplesArray = null;

    private HashMap<String, Fragment> fragments = new HashMap<String, Fragment>();
    private Activity activity;

    public FragmentMainTest(){

        examplesList.add(new Example( "ObjectAdapter", new FragmentObjectAdapter() ) );
        examplesList.add(new Example( "ExpandablePanel", new FragmentExpandablePanel() ) );
        examplesList.add(new Example( "ExpandableTextView", new FragmentExpandableTextView() ) );
        examplesList.add(new Example( "FragmentsDialogs", new FragmentsDialogs() ) );

        examplesArray = examplesList.toArray( new Example[examplesList.size()] );

//        titles = (fragments.keySet()).toArray( new String[fragments.keySet().size()] );
//        fragments_instance = (fragments.values()).toArray( new Fragment[(fragments.values()).size()] );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test_main, container, false);

        ObjectAdapter<Example> adapter = new ObjectAdapter<Example>(getActivity(), android.R.layout.simple_list_item_1, examplesArray ){
            @Override
            protected void attachItemToLayout( Example example, int position ){
                ((TextView) findViewById(android.R.id.text1)).setText( example.title );
            }
        };

        ListView listView = (ListView) rootView.findViewById(R.id.list_sample);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ){

                Example example = examplesArray[position];

                getFragmentManager().beginTransaction()
                        .addToBackStack( example.title )
                        .replace( R.id.container, example.fragment_instance )
                        .commit();
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }





    private class Example{
        public String title;
        public Fragment fragment_instance;

        public Example( String title, Fragment fragment_instance ){
            this.title = title;
            this.fragment_instance = fragment_instance;
        }
    }

}
