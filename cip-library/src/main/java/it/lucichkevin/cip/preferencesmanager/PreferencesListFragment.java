package it.lucichkevin.cip.preferencesmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kevin on 02/09/2014.
 */
public class PreferencesListFragment extends Fragment {

    public PreferencesListFragment() {
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
        View rootView = super.onCreateView( inflater, container, savedInstanceState );

        //  qui dovremmo inserire la lista

//        View rootView = inflater.inflate(R.layout.fragment_object_adapter, container, false);

//        int number = 100;
//
//        MyObject[] myObjects = new MyObject[number];
//        for( int i=0; i<number; i++ ){
//            myObjects[i] = new MyObject( "#"+ i );
//        }
//
//        ObjectAdapter<MyObject> adapter = new ObjectAdapter<MyObject>( getActivity(), android.R.layout.simple_list_item_1, myObjects ){
//            @Override
//            protected void attachItemToLayout(MyObject myObject, int position) {
//                ((TextView) findViewById(android.R.id.text1)).setText( myObject.getName() );
//            }
//        };
//
//        ListView listView = (ListView) rootView.findViewById(R.id.list_object_example);
//        listView.setAdapter(adapter);
//
        return rootView;
    }


}
