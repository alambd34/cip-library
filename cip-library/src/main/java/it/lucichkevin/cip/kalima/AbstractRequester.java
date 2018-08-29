package it.lucichkevin.cip.kalima;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;


/**

	This object allows you to send one or more requests to a server. Calling the "send ()",
	the object sends all requests stored, the mode is determined by the "sendingMode" (a value
	declared by one of the constants:

	<ul>
		<li>AbstractRequester.SendingMode.SYNCHRONOUS</li>
		<li>AbstractRequester.SendingMode.ASYNCHRONOUS</li>
	</ul>

	<br />

	<pre>
	AbstractRequester requester = new AbstractRequester() {
		&#64;Override
		protected String getServerUrl() {
			return "http://www.vogaepara.it/_project/WhereAreYou/kalima/call.php";
		}
	};
	requester.add( new Request( "1", new Request.Header("TestAsyncRequests","fn_1"), new TestQuery("1"), TestResponse.class, true) );
	requester.setCallbacks(new Request.Callbacks() {
		&#64;Override
		public void onSend( Request request ){
			super.onSend(request);
			setSomethingToGreen("request_" + request.getRequestId() + "_STARTED");
		}

		&#64;Override
		public void onEnd( Request request, Response response ){
			super.onEnd(request, response);
			setSomethingToGreen("request_" + request.getRequestId() + "_FINISHED");
		}

		&#64;Override
		public void onProgressUpdate( Request request, String placeholder ){
			super.onProgressUpdate(request, placeholder);
			setSomethingToGreen("request_" + request.getRequestId() + "_" + placeholder);
		}
	});

	//  requester.setSendingMode(AbstractRequester.SendingMode.ASYNCHRONOUS);
	//  requester.setSendingMode(AbstractRequester.SendingMode.SYNCHRONOUS);

	//  requester.setRequestMethod(AbstractRequester.RequestMethod.POST);

	requester.send();

	</pre>


 @author  Kevin Lucich (2014-03-04)
 @version 2.0.0 (2018-08-16)

	@update
		v2.0.0 - 2018-08-16
 			[ADD] Added possibly to change the request method (GET or POST)
	@see	android.os.AsyncTask
	@see	AbstractRequester.SendingMode
*/
public abstract class AbstractRequester {

	/**
	 *	The url to contact, to send the request
	 */
	protected String url;

	protected long delay = 0;
	protected int INDEX_CURRENT_REQUEST = 0;

	/**
	 *	Set if requests synchronous or asynchronous
	 */
	protected int sendingMode = SendingMode.SYNCHRONOUS;

	/**
	 *	Set if the request is gzip of not (default: true)
	 */
	protected boolean is_gzip_request = true;

	/**
	 *	Set the method to use for the request
	 */
	protected int request_method = AbstractRequester.RequestMethod.GET;

	/**
	 *	Array with requests to send
	 */
	protected ArrayList<Request> requests = new ArrayList<Request>();
	protected Request.Callbacks callbacks = new Request.Callbacks() {
		@Override
		public void onSend( Request request, String url ){
			request.callbacks.onSend(request,url);
		}
		@Override
		public void onEnd( Request request, Response response ){
			request.callbacks.onEnd(request, response);
		}
		@Override
		public void onError( Request request, Exception e ){
			request.callbacks.onError(request, e);
		}
		@Override
		public void onCancelled( Request request ){
			request.callbacks.onCancelled(request);
		}
		@Override
		public void onProgressUpdate( Request request, String placeholder ){
			request.callbacks.onProgressUpdate(request, placeholder);
		}
	};


	public AbstractRequester( String url, Request request, long delay ){
		this();
		setUrl(url);
		add(request);
		setDelay(delay);
	}

	public AbstractRequester( String url ){
		this();
		setUrl(url);
	}

	public AbstractRequester( long delay ){
		this();
		setDelay(delay);
	}

	public AbstractRequester( int sendingMode ){
		this();
		setSendingMode(sendingMode);
	}

	public AbstractRequester(){
		url = getServerUrl();
	}

	protected abstract String getServerUrl();

	/**
	 *  Send the request to the server
	 *  @param  callbacks		   The instance of callback called from AbstractRequester Object
	*/
	public void send( Request.Callbacks callbacks ){
		this.setCallbacks(callbacks);
		send();
	}

	/**
	 *  Send requests using the mode of sending setted
	 */
	public void send(){

		if( requests.isEmpty() ){
			return;
		}

		switch( getSendingMode() ){

			case SendingMode.ASYNCHRONOUS:
				sendAsynchronous();
				break;

			default:
			case SendingMode.SYNCHRONOUS:
				sendSynchronous();
				break;
		}

	}

	/**
	 *  Force the sending of requests using asynchronous mode
	 */
	public void sendAsynchronous(){
		int size = requests.size();
		for(int i=0; i<size; i++ ){
			INDEX_CURRENT_REQUEST = i;
			send(requests.get(i));
		}
	}

	/**
	 *  Force the sending of requests using synchronous mode
	 */
	public void sendSynchronous(){
		this.send(getCurrentRequest());
	}

	public void send( Request request ){

		if( request == null ){
			return;
		}

		if( !requests.contains(request) ){
			requests.add(request);
		}

		Sender s = new Sender( AbstractRequester.this, getUrl(), getDelay() );
		Executor executor = (getSendingMode()==SendingMode.ASYNCHRONOUS) ? AsyncTask.THREAD_POOL_EXECUTOR : AsyncTask.SERIAL_EXECUTOR;
		s.executeOnExecutor( executor, new RequestAndResponse(request) );
	}

	public void onSenderEnd(){
		if( getSendingMode() == SendingMode.SYNCHRONOUS && (INDEX_CURRENT_REQUEST+1 < requests.size()) ){
			INDEX_CURRENT_REQUEST++;
			send();
		}
	}



	////////////////////////////////////////
	//	Getter and Setter

	public String getUrl() {
		return url;
	}
	public void setUrl( String url ){
		this.url = url;
	}

	public Request getCurrentRequest(){
		return getRequest(INDEX_CURRENT_REQUEST);
	}
	public Request getRequest( int index ){
		if( index >= this.requests.size() ){
			return null;
		}
		return this.requests.get(index);
	}
	public ArrayList<Request> getRequests(){
		return this.requests;
	}
	public void add( Request request ){
		requests.add(request);
	}

	public Request.Callbacks getCallbacks() {
		return callbacks;
	}
	public void setCallbacks( Request.Callbacks callbacks ){
		this.callbacks = callbacks;
	}

	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}

	public int getSendingMode() {
		return sendingMode;
	}
	public void setSendingMode(int sendingMode) {
		this.sendingMode = sendingMode;
	}

	public boolean isGZipRequest(){
		return this.is_gzip_request;
	}
	public void setGZipRequest( boolean is_gzip_request ){
		this.is_gzip_request = is_gzip_request;
	}

	public int getRequestMethod(){
		return this.request_method;
	}
	public void setRequestMethod( int request_method ){
		this.request_method = request_method;
	}



	/**
	 *  Indicates the mode how to requests will be sending
	 */
	public static class SendingMode {
		public static final int SYNCHRONOUS = 1;
		public static final int ASYNCHRONOUS = 2;
	}

	/**
	 *  Indicates the mode how to requests will be sending
	 */
	public static class RequestMethod {
		public static final int GET = 1;
		public static final int POST = 2;
	}

	protected class RequestAndResponse{
		public Request request = null;
		public Response response = null;

		public RequestAndResponse( Request request ){
			this.request = request;
		}
		public RequestAndResponse( Request request, Response response ){
			this.request = request;
			this.response = response;
		}
	}

	protected class Sender extends AsyncTask<RequestAndResponse, Request, RequestAndResponse>{

		private String url;
		private long delay = 0;
		private AbstractRequester abstractRequester;
		private HttpURLConnection conn = null;

		public Sender( AbstractRequester abstractRequester, String url, long delay ){
			this.abstractRequester = abstractRequester;
			this.url = url;
			this.delay = delay;
		}

		@Override
		protected RequestAndResponse doInBackground( RequestAndResponse... rr ) {

			final boolean connection_POST_method = getRequestMethod() == AbstractRequester.RequestMethod.POST;

			RequestAndResponse requestAndResponse = rr[0];

			Request CURRENT_REQUEST = (requestAndResponse).request;
			CURRENT_REQUEST.setStatus(Request.Status.RUNNING_A);
			onProgressUpdate(CURRENT_REQUEST);

			try{
				Thread.sleep( getDelay() );
			}catch( InterruptedException e ){
				onErrorTrigger(CURRENT_REQUEST, e);
			}

			String url = getUrl();
			Gson gson = new Gson();
			String encoded_query = "";

			try {
				encoded_query = URLEncoder.encode(gson.toJson(CURRENT_REQUEST), "UTF-8");
			}catch( UnsupportedEncodingException e ){
				onErrorTrigger(CURRENT_REQUEST, e);
			}

			if( !connection_POST_method ){
				//	Concateno la richiesta convertita in JSON alla URL da contattare
				url += (!url.contains("?") ? "?" : "&") +"q=" + encoded_query;
			}

			//  Send the request to the server
			abstractRequester.callbacks.onSend(CURRENT_REQUEST,url);

			if( this.isCancelled() ){
				return null;
			}

			CURRENT_REQUEST.setStatus(Request.Status.RUNNING_B);
			onProgressUpdate(CURRENT_REQUEST);

			try {

				conn = (HttpURLConnection) (new URL(url)).openConnection();
				conn.setRequestMethod( connection_POST_method ? "POST" : "GET" );
				conn.setConnectTimeout(CURRENT_REQUEST.getTimeout());
				conn.setRequestProperty("Accept", "application/json");

				if( abstractRequester.isGZipRequest() ){
					conn.setRequestProperty("Accept-Encoding", "gzip");
				}

				if( connection_POST_method ){
					conn.setReadTimeout(10000);
					conn.setDoInput(true);
					conn.setDoOutput(true);

					OutputStream os = conn.getOutputStream();
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
					writer.write("q="+ encoded_query );
					writer.flush();
					writer.close();
					os.close();
				}

				if( conn.getResponseCode() != 200 ){
					RuntimeException e = new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() );
					onErrorTrigger(CURRENT_REQUEST, e);
				}

				CURRENT_REQUEST.setStatus(Request.Status.RUNNING_C);
				onProgressUpdate(CURRENT_REQUEST);

				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String line;

				CURRENT_REQUEST.setStatus(Request.Status.RUNNING_D);
				onProgressUpdate(CURRENT_REQUEST);

				StringBuilder sb = new StringBuilder();

				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				conn.disconnect();
				CURRENT_REQUEST.setStatus(Request.Status.RUNNING_E);
				onProgressUpdate(CURRENT_REQUEST);

				try{
					requestAndResponse.response = CURRENT_REQUEST.getClassOfResponse().cast( gson.fromJson(sb.toString(), CURRENT_REQUEST.getClassOfResponse()) );
					if( CURRENT_REQUEST.isAutoHandleData() ){
						requestAndResponse.response.handleData();
					}
				}catch( Exception e ){
					onErrorTrigger(CURRENT_REQUEST, e);
					return requestAndResponse;
				}

				CURRENT_REQUEST.setStatus(Request.Status.RUNNING_F);
				onProgressUpdate(CURRENT_REQUEST);

				return requestAndResponse;

			}catch( SocketTimeoutException e ){
				onErrorTrigger(CURRENT_REQUEST, new SocketTimeoutException("Timeout connection (" + CURRENT_REQUEST.getTimeout() + "ms)") );
			}catch( Exception e ){
				onErrorTrigger(CURRENT_REQUEST, e);
			}

			return requestAndResponse;
		}

		public void cancel(){
			if( conn != null ){
				conn.disconnect();
			}

			Request current = getCurrentRequest();
			current.setStatus(Request.Status.CANCELLED);
			current.callbacks.onCancelled(current);
			super.cancel(true);
		}

		@Override
		protected void onPostExecute( RequestAndResponse rr ){
			rr.request.setStatus(Request.Status.FINISHED);
			if( rr.response != null ){
				this.abstractRequester.callbacks.onEnd( rr.request, rr.response );
			}
			this.abstractRequester.onSenderEnd();
		}

		@Override
		protected void onProgressUpdate( Request... request ){
			this.abstractRequester.callbacks.onProgressUpdate( request[0], Request.Status.statusToString(request[0].getStatus()) );
		}

		protected void onErrorTrigger( Request request, Exception e ){
			this.abstractRequester.callbacks.onError( request, e );
		}




		////////////////////////////////////////
		//	Getter and Setter

		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}

		public long getDelay() {
			return delay;
		}
		public void setDelay(long delay) {
			this.delay = delay;
		}
	}

}