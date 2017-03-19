package org.jdhp.android.gpscoordinates;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

import static android.R.attr.permission;

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
 * - Option to choose the "Coordinate System": "Decimal" or "Sexigesimal"
 * - Add altitude ?
 * - Add detailed low level GPS status (num satellites, signal strength, ...)
 */

public class MainActivity extends AppCompatActivity {

    static int GPS_PERMISSION_REQUEST_ID = 1;            // An integer request code that is used to identify this permission request

    TextView tvGpsCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGpsCoordinates = (TextView) findViewById(R.id.tv_gps_coordinates);

        // Check For Permissions
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission != PackageManager.PERMISSION_GRANTED) {

            /*
             * See:
             * - http://stackoverflow.com/questions/33865445/gps-location-provider-requires-access-fine-location-permission-for-android-6-0
             * - https://developer.android.com/training/permissions/requesting.html
             */

            Log.i(MainActivity.class.getName(), "Request the missing permission");

            // Request the missing permissions
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, GPS_PERMISSION_REQUEST_ID);

        } else {

            Log.i(MainActivity.class.getName(), "Permission granted");
            requestLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == GPS_PERMISSION_REQUEST_ID) {
            // If request is cancelled, the result arrays are empty
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(MainActivity.class.getName(), "Permission granted");
                requestLocation();

            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }
    }

    private void requestLocation() {

        // Make the format object to well format GPS coordinate figures
        Locale locale = new Locale("en", "US");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
        final DecimalFormat gpsDecimalFormat = new DecimalFormat("#.######", decimalFormatSymbols);

        // Get the location manager
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // GPS Service
        if (locationManager != null) {
            Log.i(MainActivity.class.getName(), "locationManager != null");

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() { // TODO

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.v(MainActivity.class.getName(), "StatusChanged");
                    // TODO
                }

                public void onProviderEnabled(String provider) {
                    // GPS ON
                    Log.v(MainActivity.class.getName(), "ProviderEnabled (GPS ON)");

                    //String toastMessage = "GPS turned ON";
                    //Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

                    // TODO
                }

                public void onProviderDisabled(String provider) {
                    // GPS OFF
                    Log.v(MainActivity.class.getName(), "ProviderDisabled (GPS OFF)");

                    //String toastMessage = "GPS turned ON";
                    //Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

                    // TODO
                }

                public void onLocationChanged(Location location) {
                    // GPS position updated
                    Log.v(MainActivity.class.getName(), "LocationChanged (GPS position updated)");

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //location.getAltitude();

                    String gpsCoordinatesString = gpsDecimalFormat.format(latitude) + " : " + gpsDecimalFormat.format(longitude);

                    Log.v(MainActivity.class.getName(), gpsCoordinatesString);

                    tvGpsCoordinates.setText(gpsCoordinatesString);
                }
            });

            // GpsStatusListener
            //        	locationManager.addGpsStatusListener(new GpsStatus.Listener() {
            //
            //				public void onGpsStatusChanged(int event) {
            //					if(event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            //						GpsStatus gpsStatus = locationManager.getGpsStatus(null);
            //						Iterable<GpsSatellite> iterable = gpsStatus.getSatellites();
            //
            //						int cpt = 0;
            //						for(GpsSatellite satellite : iterable) {
            //							System.out.println("- " + satellite.toString());
            //							cpt++;
            //						}
            //						System.out.println(cpt + " satellites found.");
            //					}
            //				}
            //
            //        	});
        } else {
            Log.i(MainActivity.class.getName(), "locationManager == null");
            // TODO
        }
    }
}
