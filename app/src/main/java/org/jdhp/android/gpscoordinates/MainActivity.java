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
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tvGpsCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGpsCoordinates = (TextView) findViewById(R.id.tv_gps_coordinates);

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Locale locale = new Locale("en", "US");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
        final DecimalFormat gpsDecimalFormat = new DecimalFormat("#.######", decimalFormatSymbols);


        // GPS Service
        if (locationManager != null) {

//        	// GpsStatusListener
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

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                return;
            }


            // LocationListener
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, new LocationListener() { // TODO
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() { // TODO

                public void onStatusChanged(String provider, int status, Bundle extras) {
                } // TODO !

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                } // TODO !

                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //location.getAltitude();

                    tvGpsCoordinates.setText(gpsDecimalFormat.format(latitude) + " : " + gpsDecimalFormat.format(longitude));
                }
            });
        }
    }
}
