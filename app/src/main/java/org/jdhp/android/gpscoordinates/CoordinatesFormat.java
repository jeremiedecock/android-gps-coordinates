package org.jdhp.android.gpscoordinates;

/**
 * Created by jdecock on 21/03/2017.
 */

public class CoordinatesFormat {

    /*
     * This tag will be used for logging. It is best practice to use the class's name using
     * getSimpleName as that will greatly help to identify the location from which your logs are
     * being posted.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
     *
     */
    public static String formatDecimalCoordinate(double coordinate) {
        return String.format("%.6f", coordinate);
    }

    /*
     *
     */
    public static String formatDecimalCoordinates(double latitude, double longitude, double altitude) {
        return formatDecimalCoordinate(latitude) + " : " + formatDecimalCoordinate(longitude) + " : " + String.format("%.1f", altitude) + "m";
    }

    /*
     * See:
     * - https://en.wikipedia.org/wiki/Decimal_degrees
     * - https://support.microsoft.com/fr-fr/help/213449/how-to-convert-degrees-minutes-seconds-angles-to-or-from-decimal-angles-in-excel
     */
    public static String formatDMSCoordinate(double coordinate) {
        boolean isNegative = coordinate < 0.;
        coordinate = Math.abs(coordinate);

        int degreesInt = (int) coordinate;
        double minutesDecimal = (coordinate - (double) degreesInt) * 60.;
        int minutesInt = (int) minutesDecimal;
        double secondesDecimal = (minutesDecimal - (double) minutesInt) * 60.;

        String signStr = "+";
        if(isNegative) {
            signStr = "-";
        }

        String degreesStr = Integer.toString(degreesInt);
        String minutesStr = String.format("%02d", minutesInt);
        String secondesStr = String.format("%02.2f", secondesDecimal);

        return signStr + degreesStr + "°" + minutesStr + "'" + secondesStr + "\"";
    }

    /*
     *
     */
    public static String formatDMSCoordinates(double latitude, double longitude) {
        String latitudeSuffix = (latitude < 0) ? "S" : "N";
        String latitudeStr = formatDMSCoordinate(latitude).substring(1) + latitudeSuffix;

        String longitudeSuffix = (longitude < 0) ? "O" : "E";
        String longitudeStr = formatDMSCoordinate(longitude).substring(1) + longitudeSuffix;

        return latitudeStr + "  " + longitudeStr;
    }

    /*
     *
     */
    public static String formatLatitude(double latitude) {
        String latitudeStr = formatDecimalCoordinate(latitude) + "°";
        return latitudeStr;
    }

    /*
     *
     */
    public static String formatLongitude(double longitude) {
        String longitudeStr = formatDecimalCoordinate(longitude) + "°";
        return longitudeStr;
    }

    /*
     *
     */
    public static String formatAltitude(double altitude) {
        String altitudeStr = String.format("%.1f", altitude) + "m";
        return altitudeStr;
    }
}
