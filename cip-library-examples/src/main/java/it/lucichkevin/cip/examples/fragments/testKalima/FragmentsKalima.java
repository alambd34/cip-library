package it.lucichkevin.cip.examples.fragments.testKalima;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import it.lucichkevin.cip.examples.R;
import it.lucichkevin.cip.kalima.AbstractRequester;
import it.lucichkevin.cip.kalima.Request;
import it.lucichkevin.cip.kalima.Response;
import it.lucichkevin.cip.examples.fragments.testKalima.requester.test.TestQuery;
import it.lucichkevin.cip.examples.fragments.testKalima.response.test.TestResponse;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentsKalima extends Fragment {

	protected View rootView = null;
	protected int COLOR_GREEN = 0;
	protected int COLOR_RED = 0;

	protected AbstractRequester requester;

	public FragmentsKalima() {
		super();
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

		rootView = inflater.inflate(R.layout.fragment_kalima, container, false);

		COLOR_GREEN = ContextCompat.getColor( getActivity(), R.color.green);
		COLOR_RED = ContextCompat.getColor( getActivity(), R.color.red);

		((Button) rootView.findViewById(R.id.btn_start_requests)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				restoreRedColors();
				populateRequester();
				start_requests();
			}
		});
		
		populateRequester();

		return rootView;
	}

	protected void populateRequester(){

		requester = new AbstractRequester() {
			@Override
			protected String getServerUrl() {
				return "https://www.lucichkevin.it/_project/where_are_you/kalima/call.php";
			}
		};

		requester.add( new Request( "1", new Request.Header("TestAsyncRequests","fn_1"), new TestQuery("1"), TestResponse.class, true) );
		requester.add( new Request( "2", new Request.Header("TestAsyncRequests","fn_2"), new TestQuery("2"), TestResponse.class, true ) );
		requester.add( new Request( "3", new Request.Header("TestAsyncRequests","fn_3"), new TestQuery("3"), TestResponse.class, true ) );
		requester.add( new Request( "4", new Request.Header("TestAsyncRequests","fn_4"), new TestQuery("4"), TestResponse.class, true ) );
		requester.add( new Request( "5", new Request.Header("TestAsyncRequests","fn_5"), new TestQuery("5"), TestResponse.class, true ) );


		requester.setCallbacks(new Request.Callbacks() {

			@Override
			public void onSend( Request request, String url ){
				super.onSend(request, url);
				setSomethingToGreen("request_" + request.getRequestId() + "_STARTED");
			}

			@Override
			public void onEnd( Request request, Response response ){
				super.onEnd(request, response);
				setSomethingToGreen("request_" + request.getRequestId() + "_FINISHED");
			}

			@Override
			public void onProgressUpdate( Request request, String placeholder ){
				super.onProgressUpdate(request, placeholder);
				setSomethingToGreen("request_" + request.getRequestId() + "_" + placeholder);
			}
		});

	}

	protected View getResource( String id ){
		int resource = getActivity().getResources().getIdentifier(id, "id", getActivity().getPackageName());
		return rootView.findViewById(resource);
	}

	protected void restoreRedColors(){
		for( Request r : requester.getRequests() ){
			getResource("request_" + r.getRequestId() + "_STARTED").setBackgroundColor(COLOR_RED);
			getResource("request_" + r.getRequestId() + "_A").setBackgroundColor(COLOR_RED);
			getResource("request_" + r.getRequestId() + "_B").setBackgroundColor(COLOR_RED);
			getResource("request_" + r.getRequestId() + "_C").setBackgroundColor(COLOR_RED);
			getResource("request_" + r.getRequestId() + "_D").setBackgroundColor(COLOR_RED);
			getResource("request_" + r.getRequestId() + "_E").setBackgroundColor(COLOR_RED);
			getResource("request_" + r.getRequestId() + "_F").setBackgroundColor(COLOR_RED);
			getResource("request_" + r.getRequestId() + "_FINISHED").setBackgroundColor(COLOR_RED);
		}
	}

	protected void start_requests(){

		if( ((RadioGroup) rootView.findViewById(R.id.requester_sending_type)).getCheckedRadioButtonId() == R.id.radio_TYPE_ASYNCHRONOUS ){
			requester.setSendingMode(AbstractRequester.SendingMode.ASYNCHRONOUS);
		}else{
			requester.setSendingMode(AbstractRequester.SendingMode.SYNCHRONOUS);
		}

		if( ((RadioGroup) rootView.findViewById(R.id.requester_request_method)).getCheckedRadioButtonId() == R.id.radio_request_method_GET){
			requester.setRequestMethod(AbstractRequester.RequestMethod.GET);
		}else{
			requester.setRequestMethod(AbstractRequester.RequestMethod.POST);
		}

		requester.send();
	}

	public void setSomethingToGreen( final String id ){

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int resource = getActivity().getResources().getIdentifier(id, "id", getActivity().getPackageName());
				((TextView) rootView.findViewById(resource)).setBackgroundColor(COLOR_GREEN);
			}
		});

	}


}