package org.jdhp.android.gpscoordinates;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static org.jdhp.android.gpscoordinates.CoordinatesFormat.formatDMSCoordinates;
import static org.jdhp.android.gpscoordinates.CoordinatesFormat.formatDecimalCoordinates;

/**
 * Created by jdecock on 21/03/2017.
 */

public class LocationHandler implements LocationListener {

    /*
     * This tag will be used for logging. It is best practice to use the class's name using
     * getSimpleName as that will greatly help to identify the location from which your logs are
     * being posted.
     */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView tvGpsCoordinates;

    public LocationHandler(TextView tvGpsCoordinates) {
        this.tvGpsCoordinates = tvGpsCoordinates;
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v(LOG_TAG, "StatusChanged");
        // TODO
    }

    public void onProviderEnabled(String provider) {
        // GPS ON
        Log.v(LOG_TAG, "ProviderEnabled (GPS ON)");

        //String toastMessage = "GPS turned ON";
        //Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        // TODO
    }

    public void onProviderDisabled(String provider) {
        // GPS OFF
        Log.v(LOG_TAG, "ProviderDisabled (GPS OFF)");

        //String toastMessage = "GPS turned ON";
        //Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        // TODO
    }

    public void onLocationChanged(Location location) {
        // GPS position updated
        Log.v(LOG_TAG, "LocationChanged (GPS position updated)");

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();

        String gpsCoordinatesString = "Latitude : Longitude : Altitude";
        gpsCoordinatesString += "\n\n" + formatDecimalCoordinates(latitude, longitude, altitude);
        gpsCoordinatesString += "\n\n" + formatDMSCoordinates(latitude, longitude, altitude);

        Log.v(LOG_TAG, gpsCoordinatesString);

        this.tvGpsCoordinates.setText(gpsCoordinatesString);
    }
}
