package it.lucichkevin.cip.kalima.requester;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.kalima.response.Response;

/**
 *  It implements the struct of request to send to server
 *
 *  @author     Kevin Lucich    19/02/14
 */
public class Request implements Serializable {

    protected transient long delay = 0;
    protected transient int timeout = 20000;
    protected transient Class<? extends Response> classOfResponse;
    protected transient boolean autoHandleData = false;
    protected transient Callbacks callbacks = new EmptyKalimaCallbacks();

    protected String classNameResponse;
    protected Request.Header header;
    protected Request.Query query;
    protected String request_id = "";

    public Request( String request_id ){
        this( request_id, null, null, Response.class, false, null );
    }
    public Request( Header header, Query query ){
        this( null, header, query, Response.class, false, null );
    }
    public Request( String request_id, Header header, Query query ){
        this( request_id, header, query, Response.class, false, null );
    }
    public Request( String request_id, Header header, Query query, Class<? extends Response> classOfResponse ){
        this( request_id, header, query, classOfResponse, false, null );
    }
    public Request( Header header, Query query, Class<? extends Response> classOfResponse ){
        this( null, header, query, classOfResponse, false, null );
    }
    public Request( Header header, Query query, Class<? extends Response> classOfResponse, boolean autoHandleData ){
        this( null, header, query, classOfResponse, autoHandleData, null );
    }
    public Request( Header header, Query query, Class<? extends Response> classOfResponse, String url ){
        this( null, header, query, classOfResponse, false, null );
    }
    public Request( String request_id, Header header, Query query, Class<? extends Response> classOfResponse, boolean autoHandleData ){
        this(request_id, header, query, classOfResponse, autoHandleData, null );
    }

    /**
     * @param request_id      The request_id to identifiy the request to sent
     * @param header          Header of request, contain the product, the function and another infos to perform the request
     * @param query           The data to send with the request to server
     * @param classOfResponse La classe in cui dovrò convertire l'oggetto della risposta
     * @param autoHandleData  If TRUE the AbstractRequester called automatically the handleData() method of Respose
     * @param callbacks       The instance of callback called from AbstractRequester Object
     */
    public Request(String request_id, Header header, Query query, Class<? extends Response> classOfResponse, boolean autoHandleData, Callbacks callbacks) {
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Utils.logger( "md5 = "+ md5, Utils.LOG_DEBUG );
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



    ////////////////////////////////////////
    //  Header

    /**
     *  Defines a structure to be passed to a Request that represents the "product" and "type" to call
     *  @author Kevin Lucich
     */
    static public class Header {

        private String ID_DEVICE = Utils.getIdDevice();
        private String version;
        private String product;
        private String method;

        /**
         *  Create the header using the product and method, the version of request is automatically setted to "1"
         *  @param  product    The product to use for the request
         *  @param  method     The method of the product to call
         */
        public Header( String product, String method ){
            this( product, method, "1" );
        }

        /**
         *  Defines a structure to be passed to a Request that represents the "product" and "type" to call
         *  @param  product    The product to use for the request
         *  @param  method     The method of the product to call
         *  @param  version    The version of product to use
         */
        public Header( String product, String method, String version ){
            this.setProduct(product);
            this.setMethod(method);
            this.setVersion(version);
        }


        ////////////////////////////////////////
        //	Getter and Setter

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
         @author     Kevin Lucich
    */
    public static abstract class Callbacks{

        public static String getPrefixLog( Request request ){
            return getPrefixLog( request.getClassNameResponse(), request.getRequestId() );
        }
        protected static String getPrefixLog( String simple_name, String request_id ){
            return "["+ simple_name +" "+ request_id +"]";
        }

        /**
             Method called before the AbstractRequester keep the connection with the server, view onPreExecute of AsyncTask
             @see android.os.AsyncTask
        */
        public void onSend( Request request, String url ){
            Utils.logger( getPrefixLog(request) +" SEND"+ ((url!=null)?"\n"+url:""), Utils.LOG_INFO );
        }

        /**
             Method called after a request has been successful and the response from the server has been correctly interpreted
             @param      request     Reference to the instance of the Request
             @param      response    The response received from the server
        */
        public void onEnd( Request request, Response response ){
            Utils.logger( getPrefixLog(request) +" END", Utils.LOG_INFO );
        }

        /**
             Method called when an error is encountered
             @param      request     Reference to the instance of the Request
        */
        public void onError( Request request, Exception e ){
            e.printStackTrace();
            Utils.logger( e.getMessage(), Utils.LOG_ERROR );
        }

        /**
             Method called when a request id cancelled calling the method of AbstractRequester "cancel()"
             @param      request     Reference to the instance of the Request
             @see android.os.AsyncTask
        */
        public void onCancelled( Request request ){
            Utils.logger( getPrefixLog(request) +" CANCELLED", Utils.LOG_INFO );
        }

        /**
            Method called the AbstractRequester while a request is made. The placeholder indicates the status of the request: <ul>
            <li> "A" => The request was successfully converted into JSON    </li>
            <li> "B" => Did you follow the connection with the server       </li>
            <li> "C" => You have received a response from the server        </li>
            <li> "D" => The response has been accessed      </li>
            <li> "E" => The response was converted from JSON to the type of class in the method send()   </li>
            </ul>

            @param      request     Reference to the instance of the Request
            @param      placeholder  Indicates the status of the request
            @see android.os.AsyncTask
        */
        public void onProgressUpdate( Request request, String placeholder ){
            Utils.logger( getPrefixLog(request) +" onProgressUpdate ==> "+ placeholder, Utils.LOG_INFO );
        }
    }

    /**
        Implements a empty callback: does nothing. The AbstractRequester will only call the method handleData() of the Response (if set it)
    */
    public static class EmptyKalimaCallbacks extends Callbacks{}

}


