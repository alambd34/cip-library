package it.lucichkevin.cip.examples.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.examples.R;
import it.lucichkevin.cip.adapters.ObjectAdapter;
import it.lucichkevin.cip.examples.fragments.testKalima.FragmentsKalima;

/**
 * Created by Kevin on 27/05/2014.
 */
public class FragmentMainTest extends Fragment {

	private ArrayList<Example> examplesList = null;

	public FragmentMainTest(){
		examplesList = new ArrayList<Example>();
		examplesList.add(new Example("ObjectAdapter", new FragmentObjectAdapter()));
		examplesList.add(new Example("FragmentsDialogs", new FragmentsDialogs()));
		examplesList.add(new Example("FragmentsKalima", new FragmentsKalima()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_test_main, container, false);

		((TextView) rootView.findViewById(R.id.device_id)).setText(Utils.getDeviceId());

		ObjectAdapter<Example> adapter = new ObjectAdapter<Example>(getActivity(), android.R.layout.simple_list_item_1, examplesList ){
			@Override
			protected void attachItemToLayout( Example example, int position, View view ){
				((TextView) view.findViewById(android.R.id.text1)).setText( example.title );
			}
		};

		ListView listView = (ListView) rootView.findViewById(R.id.list_sample);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick( AdapterView<?> parent, View view, int position, long id ){

				Example example = examplesList.get(position);

				getFragmentManager().beginTransaction()
					.addToBackStack( example.title )
					.replace( R.id.container, example.fragment_instance )
					.commit();
			}
		});

		return rootView;
	}



	private class Example {
		public String title;
		public Fragment fragment_instance;

		public Example( String title, Fragment fragment_instance ){
			this.title = title;
			this.fragment_instance = fragment_instance;
		}
	}

}
