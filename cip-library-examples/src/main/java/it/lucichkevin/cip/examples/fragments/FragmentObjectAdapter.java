package it.lucichkevin.cip.examples.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import it.lucichkevin.cip.examples.R;
import it.lucichkevin.cip.ObjectAdapter;

/**
 * Created by Kevin on 27/05/2014.
 */
public class FragmentObjectAdapter extends Fragment {

    public FragmentObjectAdapter() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_object_adapter, container, false);

        int number = 100;

        MyObject[] myObjects = new MyObject[number];
        for( int i=0; i<number; i++ ){
            myObjects[i] = new MyObject( "#"+ i );
        }

        ObjectAdapter<MyObject> adapter = new ObjectAdapter<MyObject>( getActivity(), android.R.layout.simple_list_item_1, myObjects ){
            @Override
            protected void attachItemToLayout(MyObject myObject, int position) {
                ((TextView) findViewById(android.R.id.text1)).setText( myObject.getName() );
            }
        };

        ListView listView = (ListView) rootView.findViewById(R.id.list_object_example);
        listView.setAdapter(adapter);

        return rootView;
    }



    private class MyObject{

        private String name;

        public MyObject( String name ){
            setName(name);
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }

}
