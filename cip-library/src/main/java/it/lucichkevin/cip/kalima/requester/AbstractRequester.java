package it.lucichkevin.cip.kalima.requester;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import it.lucichkevin.cip.kalima.response.Response;


/**
    @author     Kevin Lucich    04/03/2014.
    @see        android.os.AsyncTask
*/
public abstract class AbstractRequester {

    public static final int TYPE_SYNCHRONOUS = 1;
    public static final int TYPE_ASYNCHRONOUS = 2;

    protected String url;
    protected long delay = 0;
    protected int INDEX_CURRENT_REQUEST = 0;
    protected ArrayList<Request> requests = new ArrayList<Request>();
    protected Request.Callbacks callbacks = new Request.Callbacks() {
        @Override
        public void onSend( Request request, String url ){
            request.callbacks.onSend(request,url);
        }
        @Override
        public void onEnd(Request request, Response response) {
            request.callbacks.onEnd(request, response);
        }
        @Override
        public void onError(Request request, Exception e) {
            request.callbacks.onError(request, e);
        }
        @Override
        public void onCancelled(Request request) {
            request.callbacks.onCancelled(request);
        }
        @Override
        public void onProgressUpdate(Request request, String placeholder) {
            request.callbacks.onProgressUpdate(request, placeholder);
        }
    };

    protected int sendingMode = TYPE_SYNCHRONOUS;

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

    public AbstractRequester(){
        url = getServerUrl();
    }

    protected abstract String getServerUrl();

    /**
     *  Send the request to the server
     *  @param  callbacks           The instance of callback called from AbstractRequester Object
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

            case TYPE_ASYNCHRONOUS:
                sendAsynchronous();
                break;

            default:
            case TYPE_SYNCHRONOUS:
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
        this.send(getCurrentRequest());   //  Alias of:   this.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    public void send( Request request ){

        if( request == null ){
            return;
        }

        Sender s = new Sender( AbstractRequester.this, getUrl(), getDelay() );
        s.executeOnExecutor( (getSendingMode()==TYPE_ASYNCHRONOUS)?AsyncTask.THREAD_POOL_EXECUTOR:AsyncTask.SERIAL_EXECUTOR, new RequestAndResponse(request) );
    }

    public void onSenderEnd(){
        if( getSendingMode() == TYPE_SYNCHRONOUS && (INDEX_CURRENT_REQUEST+1 < requests.size()) ){
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

}

class RequestAndResponse{
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
class RequestAndPlaceholder{
    public Request request = null;
    public String placeholder = null;
    public RequestAndPlaceholder( Request request, String placeholder ){
        this.request = request;
        this.placeholder = placeholder;
    }
}




class Sender extends AsyncTask<RequestAndResponse, RequestAndPlaceholder, RequestAndResponse>{

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

        RequestAndResponse requestAndResponse = rr[0];

        Request CURRENT_REQUEST = (requestAndResponse).request;

        try{
            Thread.sleep( getDelay() );
        }catch( InterruptedException e ){
            onErrorTrigger(CURRENT_REQUEST, e);
        }

        String url = getUrl();
        Gson gson = new Gson();

        try {
            //	Concateno la richiesta convertita in JSON alla URL da contattare
            url += (!url.contains("?") ? "?" : "&") +"q=" + URLEncoder.encode(gson.toJson(CURRENT_REQUEST), "UTF-8");
        }catch( UnsupportedEncodingException e ){
            onErrorTrigger(CURRENT_REQUEST, e);
        }

        //  Send the request to the server
        abstractRequester.callbacks.onSend(CURRENT_REQUEST,url);

        if( this.isCancelled() ){
            return null;
        }

        onProgressUpdate(new RequestAndPlaceholder(CURRENT_REQUEST,"A"));

        try {
            conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(CURRENT_REQUEST.getTimeout());
            conn.setRequestProperty("Accept", "application/json");

            if( conn.getResponseCode() != 200 ){
                RuntimeException e = new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() );
                onErrorTrigger(CURRENT_REQUEST, e);
            }

            onProgressUpdate(new RequestAndPlaceholder(CURRENT_REQUEST,"B"));

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String line;

            onProgressUpdate(new RequestAndPlaceholder(CURRENT_REQUEST,"C"));

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            conn.disconnect();
            onProgressUpdate(new RequestAndPlaceholder(CURRENT_REQUEST,"D"));

            try{
                requestAndResponse.response = CURRENT_REQUEST.getClassOfResponse().cast( gson.fromJson(sb.toString(), CURRENT_REQUEST.getClassOfResponse()) );
                if( CURRENT_REQUEST.isAutoHandleData() ){
                    requestAndResponse.response.handleData();
                }
            }catch( Exception e ){
                onErrorTrigger(CURRENT_REQUEST, e);
                return requestAndResponse;
            }

            onProgressUpdate(new RequestAndPlaceholder(CURRENT_REQUEST,"E"));

            return requestAndResponse;

        }catch( SocketTimeoutException e ){
            onErrorTrigger(CURRENT_REQUEST, new SocketTimeoutException("Timeout connection (" + CURRENT_REQUEST.getTimeout() + "ms)") );
        }catch( Exception e ){
            onErrorTrigger(CURRENT_REQUEST, e);
        }

        return requestAndResponse;
    }

//    public void cancel(){
//        if( conn != null ){
//            conn.disconnect();
//        }
//        this.callbacks.onCancelled(this);
//        super.cancel(true);
//    }

    @Override
    protected void onPostExecute( RequestAndResponse rr ){
        if( rr.response != null ){
            this.abstractRequester.callbacks.onEnd( rr.request, rr.response );
        }
        this.abstractRequester.onSenderEnd();
    }

    @Override
    protected void onProgressUpdate( RequestAndPlaceholder... rps ){
        this.abstractRequester.callbacks.onProgressUpdate( rps[0].request, rps[0].placeholder );
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