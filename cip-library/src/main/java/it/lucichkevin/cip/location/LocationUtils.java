package it.lucichkevin.cip.location;

import android.content.Context;
import android.location.LocationManager;
import android.location.LocationProvider;

import java.security.ProviderException;

import it.lucichkevin.cip.Utils;


/**
 *
 *  @author Kevin Lucich (15/10/2014)
 */
public class LocationUtils {

    /**
     *  Try to get the "best" provider.
     *  For use this you must call Utils.init(CONTEXT) as soon as possible
     *  @return String      The name of best provider
     */
    public static String getBestProvider() throws ProviderException{
        return getBestProvider(Utils.getContext());
    }

    /**
     *  Try to get the "best" provider
     *  @param  context     The context
     *  @return String      The name of best provider
     */
    public static String getBestProvider( Context context ) throws ProviderException{
        LocationManager locationManager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );

        LocationProvider MOCK = locationManager.getProvider(MockLocationProvider.MOCK_PROVIDER_NAME);
        if( MOCK != null ){
            return MockLocationProvider.MOCK_PROVIDER_NAME;
        }else{
            Utils.logger("No MOCK provider available.", Utils.LOG_INFO);
        }

        LocationProvider GPS = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        if( GPS != null ){
            return LocationManager.GPS_PROVIDER;
        }else{
            Utils.logger("No GPS provider available.", Utils.LOG_INFO);
        }

        LocationProvider NETWORK = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
        if( NETWORK != null ){
            return LocationManager.NETWORK_PROVIDER;
        }else{
            Utils.logger("No NETWORK provider available", Utils.LOG_INFO);
        }

        throw new ProviderException("");
    }

}
