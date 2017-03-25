package org.jdhp.android.gpscoordinates;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static org.jdhp.android.gpscoordinates.CoordinatesFormat.formatDMSCoordinates;
import static org.jdhp.android.gpscoordinates.CoordinatesFormat.formatDecimalCoordinates;

/*
 * Doc:
 * - https://developer.android.com/guide/topics/location/index.html
 * - https://developer.android.com/guide/topics/location/strategies.html
 * - https://developer.android.com/training/location/index.html
 * - https://developer.android.com/reference/android/location/LocationManager.html
 * - https://android-developers.googleblog.com/2011/06/deep-dive-into-location.html
 *
 * TODO
 * - "menu / copy" to copy to clipboard the current GPS position
 * - "menu / send" to send the current GPS position (mail, sms, tweet, ...)
 * - Option to choose the "Coordinate System": "Decimal" or "Sexigesimal" (aka "DMS")
 * - Add altitude ?
 * - Add detailed low level GPS status (num satellites, signal strength, ...)
 */

public class MainActivity extends AppCompatActivity {

    /*
     * This tag will be used for logging. It is best practice to use the class's name using
     * getSimpleName as that will greatly help to identify the location from which your logs are
     * being posted.
     */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    static int GPS_PERMISSION_REQUEST_ID = 1;            // An integer request code that is used to identify this permission request

    TextView tvDmsCoordinates;
    TextView tvGpsCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDmsCoordinates = (TextView) findViewById(R.id.tv_dms_coordinates);
        tvGpsCoordinates = (TextView) findViewById(R.id.tv_gps_coordinates);

        // Check For Permissions
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission != PackageManager.PERMISSION_GRANTED) {

            /*
             * See:
             * - http://stackoverflow.com/questions/33865445/gps-location-provider-requires-access-fine-location-permission-for-android-6-0
             * - https://developer.android.com/training/permissions/requesting.html
             */

            Log.i(LOG_TAG, "Request the missing permission");

            // Request the missing permissions
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, GPS_PERMISSION_REQUEST_ID);

        } else {

            Log.i(LOG_TAG, "Permission granted");
            requestLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == GPS_PERMISSION_REQUEST_ID) {
            // If request is cancelled, the result arrays are empty
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(LOG_TAG, "Permission granted");
                requestLocation();

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission

            }
            return;
        }
    }

    private void requestLocation() {

        // Get the location manager
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // GPS Service
        if (locationManager != null) {
            Log.i(LOG_TAG, "locationManager != null");

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationHandler(tvDmsCoordinates, tvGpsCoordinates));

            /*
            // GpsStatusListener
            locationManager.addGpsStatusListener(new GpsStatus.Listener() {

                public void onGpsStatusChanged(int event) {
                    if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                        Iterable<GpsSatellite> iterable = gpsStatus.getSatellites();

                        int cpt = 0;
                        for (GpsSatellite satellite : iterable) {
                            System.out.println("- " + satellite.toString());
                            cpt++;
                        }
                        System.out.println(cpt + " satellites found.");
                    }
                }

            });
            */

        } else {
            Log.i(LOG_TAG, "locationManager == null");
            // TODO
        }
    }
}
