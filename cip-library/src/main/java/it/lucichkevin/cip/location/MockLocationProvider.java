package it.lucichkevin.cip.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

import java.util.HashMap;
import java.util.List;

import it.lucichkevin.cip.Utils;

/**
 *  A mock location provider. It notify a group of listeners (LocationListener object) with a mock
 *  location. The provider name is "MOCK_PROVIDER", you can selected a name of your personal
 *  provider. Is recommended to use the static method getBestProvider (LocationUtils object)
 *  to get the best provider available (it returns MOCK_PROVIDER if this is active :) ),
 *  otherwise it searches the real and best provider existing.
 *
 *  @see    MockLocationProvider#MOCK_PROVIDER_NAME
 *  @see    LocationListener
 *  @see    it.lucichkevin.cip.location.LocationUtils#getBestProvider()
 *
 *  @author Kevin Lucich (15/10/2014)
 */
public class MockLocationProvider {

    public static final int ERROR_PROVIDER_UNAVAILABLE = 1;
    public static final int ERROR_PROVIDER_OUT_OF_SERVICE = 2;

//    public static final String MOCK_PROVIDER_NAME = "MOCK_PROVIDER";
    public static final String MOCK_PROVIDER_NAME = LocationManager.GPS_PROVIDER;

    private List data = null;
    private HashMap<String, LocationListener> listeners = new HashMap<String, LocationListener>();
    private static int DATA_INDEX = 0;

    private Location LAST_LOCATION = null;

    private int supply_duration = 120000;  //  Milliseconds
    private int interval_location_updates = 1000;

    private ProviderTask providerTask = null;
    private LocationManager locationManager;
    private String mockLocationProvider;

    public MockLocationProvider() {
        this( MockLocationProvider.MOCK_PROVIDER_NAME, null );
    }

    public MockLocationProvider(String mockLocationProvider) {
        this(mockLocationProvider, null);
    }

    public MockLocationProvider(String mockLocationProvider, List data) {
        this( mockLocationProvider, data, 1000, 120000 );
    }

    public MockLocationProvider(String mockLocationProvider, List data, int interval_location_updates, int supply_duration) {
        this.mockLocationProvider = mockLocationProvider;
        this.data = data;
        setIntervalLocationUpdates(interval_location_updates);
        setSupplyDuration(supply_duration);

        if( data == null ){
            //  Default start location
            setStartLocation();
        }

        locationManager = (LocationManager) Utils.getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(mockLocationProvider, false, false, false, false, true, true, true, 0, 5);
        locationManager.setTestProviderEnabled(mockLocationProvider, true);
    }

    private Location getMockLocation(){

        if( data != null ){
            updateMockLocationByData();
        }else{
            updateMockLocation();
        }

        // Set the time in the location. If the time on this location matches
        // the time on the one in the previous set call, it will be ignored
        LAST_LOCATION.setTime(System.currentTimeMillis());

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ){
            LAST_LOCATION.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }

        LAST_LOCATION.setAccuracy(100.0f);

        return LAST_LOCATION;
    }

    private void updateMockLocationByData(){

        if( data.size() < DATA_INDEX ){
            data = null;
            updateMockLocation();
            return;
        }

        String[] parts = ((String) data.get(DATA_INDEX)).split(",");
        Double latitude = Double.valueOf(parts[0]);
        Double longitude = Double.valueOf(parts[1]);
        Double altitude = Double.valueOf(parts[2]);

        LAST_LOCATION.setLatitude(latitude);
        LAST_LOCATION.setLongitude(longitude);
        LAST_LOCATION.setAltitude(altitude);

        DATA_INDEX++;
    }

    private void updateMockLocation(){
        LAST_LOCATION.setLatitude( LAST_LOCATION.getLatitude() + 0.000150 );
        LAST_LOCATION.setLongitude( LAST_LOCATION.getLongitude() + 0.000130 );
//        if( new Random().nextInt(1) == 1 ){
//            LAST_LOCATION.setAltitude( LAST_LOCATION.getAltitude() + 0.1 );
//        }
    }




    //////////////////////////////////////////////
    //  Public methods

    /**
     *  Start the mock location provider
     */
    public void start(){
        if( providerTask == null || providerTask.getStatus() == AsyncTask.Status.FINISHED ){
            providerTask = new ProviderTask(locationManager);
        }
        if( providerTask.getStatus() != AsyncTask.Status.RUNNING ){
            Utils.logger("MockLocationProvider.start()", Utils.LOG_DEBUG);
            providerTask.execute();
        }
    }

    /**
     *  Stop the mock location provider
     */
    public void stop(){
        Utils.logger("MockLocationProvider.stop()", Utils.LOG_DEBUG);
        providerTask.cancel(true);
    }


    //////////////////////////////////////////////
    //  Getters and Setters

    public int getIntervalLocationUpdates(){
        return this.interval_location_updates;
    }
    public void setIntervalLocationUpdates( int millis ){
        this.interval_location_updates = millis;
    }

    public int getSupplyDuration(){
        return this.supply_duration;
    }
    public void setSupplyDuration( int supply_duration ){
        this.supply_duration = supply_duration;
    }

    /**
     *  Set the the starting point
     */
    protected void setStartLocation(){
        Location location = new Location(mockLocationProvider);
        location.setLatitude(45.484116);
        location.setLongitude(12.250984);
        location.setAltitude(3.0);
        location.setAccuracy(2);
        location.setTime(System.currentTimeMillis());
        setStartLocation(location);
    }

    /**
     *  If you not provide a List of points, use this method to set the starting point
     */
    public void setStartLocation( Location location ){
        LAST_LOCATION = location;
    }

    /***
     *  Return the number of listeners
     *  @return  int     The number of listeners
     */
    public int size() {
        return this.listeners.size();
    }

    /**
     *  Add a new listener
     *  @param  locationListener     Implementation of LocationChanged class
     */
    public void addListener( String key, LocationListener locationListener) {
        this.listeners.put( key, locationListener);
    }
    /**
     *  Add a new listener
     *  @param  locationListener     Implementation of LocationChanged class
     */
    public void addListener( LocationListener locationListener) {
        addListener( locationListener.getClass().getSimpleName(), locationListener );
    }

    /**
     *  Remove a listener by the instance
     *  @param  locationListener     Implementation of LocationListener class
     *  @see    #removeListener(String)
     */
    public void removeListener( LocationListener locationListener){
        removeListener( locationListener.getClass().getSimpleName() );
    }

    /**
     *  Remove a listener by the name of class. If the #size() of listeners is 0, the provider will be stopped
     *  @param  class_name     The name of implementation of LocationListener class
     */
    public void removeListener( String class_name ){
        if( this.listeners.containsKey(class_name) ){
            this.listeners.remove(class_name);
        }
        if( this.size() == 0 ){
            providerTask.cancel(true);
        }
    }

    public void clearListener(){
        providerTask.cancel(true);
        this.listeners = new HashMap<String, LocationListener>();;
    }




    /**
     *  Abstract class its methods called the location changed or location fail.
     */
    public static abstract class LocationListener {

        /**
         *  Callback called when the MockLocationProvider change the location
         *  @param  location    The new location
         *
         *  @see android.location.LocationListener#onLocationChanged
         */
        public abstract void onLocationChanged( Location location );

        /**
         *  Called when an error occurred:
         *  @param  code    The code of error
         *  @param  message A message that describe the error occurred
         *
         *  @see    MockLocationProvider#ERROR_PROVIDER_UNAVAILABLE
         *  @see    MockLocationProvider#ERROR_PROVIDER_OUT_OF_SERVICE
         */
        public abstract void onLocationFail( int code, String message );

        /**
         *  Called when the provider is enabled by the user.
         *  @param  provider    The name of the location provider associated with this update
         *
         *  @see android.location.LocationListener#onProviderEnabled
         */
        public void onProviderEnabled( String provider ){}

        /**
         *  Called when the provider is disabled by the user.
         *  @param  provider    The name of the location provider associated with this update
         *
         *  @see android.location.LocationListener#onProviderDisabled
         */
        public void onProviderDisabled( String provider ){}

        /**
         *  Called when the provider finished to generate new locations
         *  @param  provider    The name of the location provider associated with this update
         */
        public void onProviderFinished( String provider ){}
    }





    //////////////////////////////////////////////
    //  AsyncTask methods

    //  Notify the listeners when Location fails
    private void notifyLocationFail( int code, String provider_name ){
        for( LocationListener locationListener : this.listeners.values() ){
            (locationListener).onLocationFail(code, provider_name);
        }
    }

    private class ProviderTask extends AsyncTask<Void,Void,String>  {

        private LocationManager locationManager;

        public ProviderTask( LocationManager locationManager ){
            this.locationManager = locationManager;
        }

        @Override
        protected void onPreExecute() {

            locationManager.requestLocationUpdates( mockLocationProvider, getIntervalLocationUpdates(), 5, new android.location.LocationListener() {

                @Override
                public void onLocationChanged( Location location ){
                    Utils.logger("The location has been updated!", Utils.LOG_INFO);
                    for( LocationListener locationListener : MockLocationProvider.this.listeners.values() ){
                        (locationListener).onLocationChanged(location);
                    }
                }

                @Override
                public void onProviderEnabled( String provider ){
                    Utils.logger("Location provider " + provider + " has been enabled", Utils.LOG_INFO);
                    for( LocationListener locationListener : MockLocationProvider.this.listeners.values() ){
                        (locationListener).onProviderEnabled(provider);
                    }
                }

                @Override
                public void onProviderDisabled( String provider ){
                    Utils.logger("Location provider '" + provider + "' disabled", Utils.LOG_INFO);
                    for( LocationListener locationListener : MockLocationProvider.this.listeners.values() ){
                        (locationListener).onProviderDisabled(provider);
                    }
                }

                @Override
                public void onStatusChanged( String provider, int status, Bundle extras ){
                    Utils.logger("The status of the provider " + provider + " has changed", Utils.LOG_INFO);
                    if( status == 0 ){
                        Utils.logger(provider + " is OUT OF SERVICE", Utils.LOG_ERROR);

                        MockLocationProvider.this.notifyLocationFail(MockLocationProvider.ERROR_PROVIDER_OUT_OF_SERVICE, provider);
                    }
                    else if( status == 1){
                        Utils.logger(provider + " is TEMPORARILY_UNAVAILABLE", Utils.LOG_ERROR);
                        MockLocationProvider.this.notifyLocationFail(MockLocationProvider.ERROR_PROVIDER_UNAVAILABLE, provider);
                    }
                    else{
                        Utils.logger(provider + " is AVAILABLE", Utils.LOG_INFO);
                    }
                }

            });

        }

        @Override
        protected String doInBackground( Void... params ){

            long STARTED = System.currentTimeMillis();

            while(true){

                if( isCancelled() ){
                    return null;
                }

                try{
                    Thread.sleep(getIntervalLocationUpdates());
                }catch( InterruptedException e ){
//                e.printStackTrace();
                    return null;
                }

                Location location = getMockLocation();
                Utils.logger(location.toString(), Utils.LOG_DEBUG);

                locationManager.setTestProviderLocation( mockLocationProvider, location );

                if( (System.currentTimeMillis()-STARTED) > getSupplyDuration()){
                    this.cancel(true);
                    return null;
                }
            }
        }

        protected void onPostExecute( String result ) {
            Utils.logger("[MockLocationProvider] Provider \""+ mockLocationProvider +"\" has finished", Utils.LOG_INFO);
            for( LocationListener locationListener : MockLocationProvider.this.listeners.values() ){
                (locationListener).onProviderFinished(mockLocationProvider);
            }
        }

    }
}
