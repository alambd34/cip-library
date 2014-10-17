package it.lucichkevin.cip.location;

import android.location.Location;

import com.google.android.gms.maps.LocationSource;

/**
 *  Used this class for create a mock locations for your position in Google Map, add this
 *  to a MockLocationProvider for create a dynamic location :)
 *
 *  @see MockLocationProvider
 *  @see LocationSource
 *  @see Location
 *
 *  @author Kevin Lucich (16/10/2014)
 */
public class MyFakeLocationSource extends MockLocationProvider.LocationListener implements LocationSource {

    private OnLocationChangedListener listener;

    public MyFakeLocationSource(){
    }

    /**
     *  Return a new instance of MyFakeLocationSource
     *  @return MyFakeLocationSource
     */
    public static MyFakeLocationSource Builder(){
        return new MyFakeLocationSource();
    }

    /**
     *  Return a new instance of MyFakeLocationSource and add the listener created to mockProvider
     *  @return MyFakeLocationSource
     */
    public static MyFakeLocationSource Builder( MockLocationProvider mockLocationProvider ){
        MyFakeLocationSource dummy = MyFakeLocationSource.Builder();
        dummy.addToMockLocationProvider(mockLocationProvider);
        return dummy;
    }

    /**
     *  Add this listener to the mockLocationProvider in parameters, it is a simply alias when
     *  you want create a new MockLocationProvider for test the MyLocation (blue dot) in GoogleMap.
     *
     *  @param  mockLocationProvider    A MockLocationProvider to whom add the listener
     */
    public void addToMockLocationProvider( MockLocationProvider mockLocationProvider ){
        mockLocationProvider.addListener(this);
    }

    /**
     *  @see com.google.android.gms.maps.LocationSource#activate
     */
    @Override
    public void activate( OnLocationChangedListener listener ){
        this.listener = listener;
    }

    /**
     *  @see com.google.android.gms.maps.LocationSource#deactivate
     */
    @Override
    public void deactivate(){

    }

    /**
     *  @see it.lucichkevin.cip.location.MockLocationProvider.LocationListener
     */
    @Override
    public void onLocationChanged( Location location ){
        if( listener != null ){
            listener.onLocationChanged(location);
        }
    }

    /**
     *  @see it.lucichkevin.cip.location.MockLocationProvider.LocationListener
     */
    @Override
    public void onLocationFail( int code, String message ){

    }

}
