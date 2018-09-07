package it.lucichkevin.cip.kalima;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import it.lucichkevin.cip.Utils;

/**
 *  It implements the struct of request to send to server
 *
 *  @author	 Kevin Lucich	19/02/14
 *
 *  @updated
 *  	2018-08-16
 *  		- Remove EmptyKalimaCallbacks in favour of an empty implementation: "new Callbacks(){}"
 *  		- By default a Callbacks object do nothing, use SpeakingCallbacks to see logs in Logcat
 *  	2015-03-03
 */
public class Request implements Serializable {

	protected transient long delay = 0;
	protected transient int timeout = 20000;
	protected transient Class<? extends Response> classOfResponse;
	protected transient boolean autoHandleData = false;
	protected transient Callbacks callbacks = new Callbacks(){};
	protected transient AbstractRequester.Sender senderReference = null;

	protected String request_id = "";
	protected Request.Header header;
	protected Request.Query query;
	protected String classNameResponse;
	protected int status = Status.PENDING;

	public Request( String request_id ){
		this( request_id, null, null );
	}
	public Request( Header header, Query query ){
		this( null, header, query, Response.class, false, null );
	}
	public Request( Header header, Query query, Class<? extends Response> classOfResponse ){
		this( null, header, query, classOfResponse, false, null );
	}
	public Request( Header header, Query query, Class<? extends Response> classOfResponse, boolean autoHandleData ){
		this( null, header, query, classOfResponse, autoHandleData, null );
	}
	public Request( Header header, Query query, Class<? extends Response> classOfResponse, boolean autoHandleData, Callbacks callbacks ){
		this( null, header, query, classOfResponse, autoHandleData, callbacks );
	}
	public Request( String request_id, Header header, Query query ){
		this( request_id, header, query, Response.class, false, null );
	}
	public Request( String request_id, Header header, Query query, Class<? extends Response> classOfResponse ){
		this( request_id, header, query, classOfResponse, false, null );
	}
	public Request( String request_id, Header header, Query query, Class<? extends Response> classOfResponse, boolean autoHandleData ){
		this(request_id, header, query, classOfResponse, autoHandleData, null );
	}

	/**
	 * @param request_id	  The request_id to identifiy the request to sent
	 * @param header		  Header of request, contain the product, the function and another infos to perform the request
	 * @param query		   The data to send with the request to server
	 * @param classOfResponse La classe in cui dovrò convertire l'oggetto della risposta
	 * @param autoHandleData  If TRUE the AbstractRequester called automatically the handleData() method of Respose
	 * @param callbacks	   The instance of callback called from AbstractRequester Object
	 */
	public Request( String request_id, Header header, Query query, Class<? extends Response> classOfResponse, boolean autoHandleData, Callbacks callbacks ){
		setRequestId(request_id);
		setHeader(header);
		setQuery(query);
		setClassOfResponse(classOfResponse);
		setClassNameResponse(classOfResponse.getSimpleName());
		setAutoHandleData(autoHandleData);
		if (callbacks != null) {
			setCallbacks(callbacks);
		}
	}

	/**
	 *	When called cancel the request
	 */
	public void cancel(){
		if( this.getSenderReference() != null ){
			this.getSenderReference().cancel();
			this.setStatus( Status.CANCELLED );
		}
	}


	////////////////////////////////////////
	//	Getter and Setter

	public Request.Header getHeader() {
		return header;
	}
	public void setHeader(Request.Header header) {
		this.header = header;
	}

	public Request.Query getQuery() {
		return query;
	}
	public void setQuery(Request.Query query) {
		this.query = query;
	}

	public String getRequestId() {
		return request_id;
	}
	public void setRequestId( String request_id ){
		if( request_id == null ){
			setRequestId();
			return;
		}
		this.request_id = request_id;
	}

	private void setRequestId(){
		String md5 = "MD5-ERROR";
		try {
			byte[] bytes = (MessageDigest.getInstance("MD5")).digest(Utils.getDeviceName().getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * bytes.length);
			for( byte b : bytes ){
				sb.append("0123456789ABCDEF".charAt((b & 0xF0) >> 4));
				sb.append("0123456789ABCDEF".charAt((b & 0x0F)));
			}
			md5 = sb.toString();
		}catch( NoSuchAlgorithmException | UnsupportedEncodingException e ){
			e.printStackTrace();
		}

		setRequestId(md5);
	}

	public Class<? extends Response> getClassOfResponse() {
		return classOfResponse;
	}
	public void setClassOfResponse( Class<? extends Response> classOfResponse ){
		this.classOfResponse = classOfResponse;
	}

	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}

	public boolean isAutoHandleData() {
		return autoHandleData;
	}
	public void setAutoHandleData(boolean autoHandleData) {
		this.autoHandleData = autoHandleData;
	}

	public Request.Callbacks getCallbacks() {
		return callbacks;
	}
	public void setCallbacks( Request.Callbacks callbacks ){
		this.callbacks = callbacks;
	}

	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getClassNameResponse() {
		return classNameResponse;
	}
	public void setClassNameResponse(String classNameResponse) {
		this.classNameResponse = classNameResponse;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus( int status ){
		this.status = status;
	}
	public boolean isStatus( int status ){
		return Status.is( getStatus(), status );
	}

	protected AbstractRequester.Sender getSenderReference() {
		return senderReference;
	}
	protected void setSenderReference( AbstractRequester.Sender senderReference ){
		this.senderReference = senderReference;
	}

	////////////////////////////////////////
	//  Equals methods

	public boolean equals( Object o ){
		return (o instanceof Request) && equals((Request) o);
	}
	public boolean equals( Request r ){
		return this.getRequestId().equals(r.getRequestId());
	}


	////////////////////////////////////////
	//  Header

	/**
	 *  Defines a structure to be passed to a Request that represents the "product" and "type" to call
	 *  @author Kevin Lucich
	 */
	public static class Header {

		private String device_id;
		private String version;
		private String product;
		private String method;

		/**
		 *  Create the header using the product and method, the version of request is automatically setted to "1"
		 *  @param  product	The product to use for the request
		 *  @param  method	 The method of the product to call
		 */
		public Header( String product, String method ){
			this( product, method, "1" );
		}

		/**
		 *  Defines a structure to be passed to a Request that represents the "product" and "type" to call
		 *  @param  product	The product to use for the request
		 *  @param  method	 The method of the product to call
		 *  @param  version	The version of product to use
		 */
		public Header( String product, String method, String version ){
			this( Utils.getDeviceId(), product, method, version );
		}

		/**
		 *  Defines a structure to be passed to a Request that represents the "product" and "type" to call
		 *  @param	device_id	The device id to use in the request to identify the device
		 *  @param  product		The product to use for the request
		 *  @param  method		The method of the product to call
		 *  @param  version		The version of product to use
		 */
		public Header( String device_id, String product, String method, String version ){
			this.setDeviceId(device_id);
			this.setProduct(product);
			this.setMethod(method);
			this.setVersion(version);
		}


		////////////////////////////////////////
		//	Getter and Setter

		public String getDeviceId() {
			return device_id;
		}
		public void setDeviceId( String device_id ){
			this.device_id = device_id;
		}

		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}

		public String getProduct() {
			return product;
		}
		public void setProduct(String product) {
			this.product = product;
		}

		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}

	}



	////////////////////////////////////////
	//  Query

	/**
	 *  Defines a structure to be passed to a Request that represents the data to send to the "method"
	 *  @author Kevin Lucich
	 */
	static public class Query{
		public Query(){}
	}



	////////////////////////////////////////
	//	Static Classes

	/**
		 This his abstract class structure functions that the object AbstractRequester called when an action is performed (before a req onStart, etc ...)
		 @author	 Kevin Lucich
	*/
	public static abstract class Callbacks {

		public static String getPrefixLog( Request request ){
			return getPrefixLog( request.getClassNameResponse(), request.getRequestId() );
		}
		protected static String getPrefixLog( String simple_name, String request_id ){
			return "["+ simple_name +" "+ request_id +"]";
		}

		/**
		 *	Method called before the AbstractRequester keep the connection with the server, view onPreExecute of AsyncTask
		 *	@param	request		Request		Reference to the instance of the Request
		 *	@param	url			String		The response received from the server
		 *	@see android.os.AsyncTask
		*/
		public void onSend( Request request, String url ){ };

		/**
		 *	Method called after a request has been successful and the response from the server has been correctly interpreted
		 *	@param	request	Request		Reference to the instance of the Request
		 *	@param	response	Response	The response received from the server
		*/
		public void onEnd( Request request, Response response ){ }

		/**
		 *	Method called when an error is encountered
		 *	@param	  request	Request 	Reference to the instance of the Request
		 *	@param	  e			Exception	Reference to the instance of the Request
		*/
		public void onError( Request request, Exception e ){ }

		/**
		 *	Method called when a request id cancelled calling the method of AbstractRequester "cancel()"
		 *	@param	  request	 Request	Reference to the instance of the Request
		 *	@see android.os.AsyncTask
		*/
		public void onCancelled( Request request ){ }

		/**
		 *	Method called the AbstractRequester while a request is made. The placeholder indicates the status of the request: <ul>
		 *	<li> "A" => The request was successfully converted into JSON	</li>
		 *	<li> "B" => Did you follow the connection with the server	   </li>
		 *	<li> "C" => You have received a response from the server		</li>
		 *	<li> "D" => The response has been accessed	  </li>
		 *	<li> "E" => The response was converted from JSON to the type of class in the method send()   </li>
		 *	</ul>
		 *
		 *	@param	  request		 Reference to the instance of the Request
		 *	@param	  placeholder	 Indicates the status of the request {@link Request.Status}
		 *	@see android.os.AsyncTask
		*/
		public void onProgressUpdate( Request request, String placeholder ){ }
	}

	/**
	 *	Each callbacks write a log in Logcat
	 */
	public static class SpeakingCallbacks extends Callbacks {

		/**
		 *	Method called before the AbstractRequester keep the connection with the server, view onPreExecute of AsyncTask
		 *	@param	request		Request		Reference to the instance of the Request
		 *	@param	url			String		The response received from the server
		 *	@see android.os.AsyncTask
		*/
		public void onSend( Request request, String url ){
			Utils.logger( getPrefixLog(request) +" SEND"+ ((url!=null)?"\n"+url:""), Utils.LOG_INFO );
		}

		/**
		 *	Method called after a request has been successful and the response from the server has been correctly interpreted
		 *	@param	request	Request		Reference to the instance of the Request
		 *	@param	response	Response	The response received from the server
		*/
		public void onEnd( Request request, Response response ){
			Utils.logger( getPrefixLog(request) +" END", Utils.LOG_INFO );
		}

		/**
		 *	Method called when an error is encountered
		 *	@param	  request	Request 	Reference to the instance of the Request
		 *	@param	  e			Exception	Reference to the instance of the Request
		*/
		public void onError( Request request, Exception e ){
			e.printStackTrace();
			Utils.logger( e.getMessage(), Utils.LOG_ERROR );
		}

		/**
		 *	Method called when a request id cancelled calling the method of AbstractRequester "cancel()"
		 *	@param	  request	 Request	Reference to the instance of the Request
		 *	@see android.os.AsyncTask
		*/
		public void onCancelled( Request request ){
			Utils.logger( getPrefixLog(request) +" CANCELLED", Utils.LOG_INFO );
		}

		/**
		 *	Method called the AbstractRequester while a request is made. The placeholder indicates the status of the request: <ul>
		 *	<li> "A" => The request was successfully converted into JSON	</li>
		 *	<li> "B" => Did you follow the connection with the server	   </li>
		 *	<li> "C" => You have received a response from the server		</li>
		 *	<li> "D" => The response has been accessed	  </li>
		 *	<li> "E" => The response was converted from JSON to the type of class in the method send()   </li>
		 *	</ul>
		 *
		 *	@param	  request		 Reference to the instance of the Request
		 *	@param	  placeholder	 Indicates the status of the request {@link Request.Status}
		*/
		public void onProgressUpdate( Request request, String placeholder ){
			Utils.logger( getPrefixLog(request) +" onProgressUpdate ==> "+ placeholder, Utils.LOG_INFO );
		}
	}



	/**
	 *   Indicates the current status of the request. Each status will be set only once during
	 *   the lifetime of the request.
	 */
	public static class Status {

		/**
		 *  Indicates that the request has waiting to send.
		 */
		public static final int PENDING = 1;

		/**
		 *  Indicates that the request is running (including all sub-status).
		 */
		public static final int RUNNING = 100;
		/**
		 *  Indicates that the request is just started.
		 */
		public static final int RUNNING_A = 101;
		/**
		 *  The request was converted in the URL to connect.
		 */
		public static final int RUNNING_B = 102;
		/**
		 *  Indicates that is arrived a response from the server.
		 */
		public static final int RUNNING_C = 103;
		/**
		 *  Indicates that it is going to read the response arrived from the server.
		 */
		public static final int RUNNING_D = 104;
		/**
		 *  Indicates that the response of the server has been read.
		 */
		public static final int RUNNING_E = 105;
		/**
		 *  Indicates that the response of the server has been converted to Response object
		 */
		public static final int RUNNING_F = 106;

		/**
		 *  Indicates that the request has finished.
		 */
		public static final int FINISHED = 200;

		/**
		 *  Indicates that the request has been cancelled.
		 */
		public static final int CANCELLED = 201;

		/**
		 *  Check is two status are equal
		 *  @param  current_status  The current status to check
		 *  @param  status		  The another status
		 *  @return Returns true if status are equal.
		 */
		public static boolean is( Integer current_status, Integer status ){
			//  If RUNNING, check the range of running status (A,B,C,D,E)
			if( status == RUNNING ){
				return (current_status>PENDING && current_status<FINISHED);
			}
			return current_status.equals(status);
		}

		/**
		 *  @param  status	The status to convert
		 *	@return	String	Return the name of status id
		 *  @see it.lucichkevin.cip.kalima.Request.Status#statusToString(int, boolean)
		 */
		public static String statusToString( int status ){
			return statusToString(status,false);
		}

		/**
		 *  Check is two status are equal
		 *  @param  status	  The status to convert
		 *  @param  toExplain   In the log, write a description about the status
		 *  @return Returns a string representing the status.
		 */
		public static String statusToString( int status, boolean toExplain ){

			String placeholder = "INVALID";

			switch( status ) {
				case PENDING:
					return "PENDING";
				case RUNNING:
					return "RUNNING";
				case RUNNING_A:
					return "A"; //  [request is just started]
				case RUNNING_B:
					return "B"; //  [request converted]
				case RUNNING_C:
					return "C"; //  [response arrived]
				case RUNNING_D:
					return "D"; //  [it is going to read the response]
				case RUNNING_E:
					return "E"; //  [response has been read]
				case RUNNING_F:
					return "F"; //  [response converted]
				case FINISHED:
					return "FINISHED";
			}

			if( toExplain ){
				String description = explain(status);
				if( !description.equals("") ){
					placeholder += "["+ description +"]";
				}
			}
			return placeholder;
		}

		/**
		 *  Returns a description of the status
		 *	@param	status	int		The status code
		 *	@return	String	Explain the status code
		 */
		public static String explain( int status ){

			switch( status ) {
				case RUNNING_A:
					return "request is just started";
				case RUNNING_B:
					return "request converted in JSON";
				case RUNNING_C:
					return "response arrived";
				case RUNNING_D:
					return "it is going to read the response";
				case RUNNING_E:
					return "response has been read";
				case RUNNING_F:
					return "response converted";
				case FINISHED:
				case PENDING:
				case RUNNING:
					return "";
			}

			return "";
		}

	}

}


