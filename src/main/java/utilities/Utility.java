package utilities;

import java.awt.geom.Point2D;

/**
 * Created by tjf3191 on 2/16/15.
 */

import jhlabs.map.proj.ProjectionFactory;
import jhlabs.map.proj.Projection;

public class Utility {

    // Class variable for calculating the projection
    private static final Projection NAD_27 = ProjectionFactory.getNamedPROJ4CoordinateSystem("nad27:3402");

    // Class variable for converting survey feet to meters
    public static final double SURVEY_2_METER = 1200.0 / 3937.0;

    // Class variable for correcting the latitude after conversion
    public static final double LATITUDE_CORRECTION = 0.00049;

    // Class variable for correcting the longitude after conversion
    public static final double LONGITUDE_CORRECTION = 0.11241;

    /**
     * Class method for converting a nad27 measure of eastling and northling
     * values to GCS coordinates.
     * @param eastling  the X value in feet
     * @param northling the Y value in feet
     * @return          returns a double point, at [0] is longitude and [1] is latitude
     */
    public static final double[] Nad27toGCS (double eastling, double northling) {

        // convert from feet to meters
        eastling = eastling * SURVEY_2_METER;
        northling = northling * SURVEY_2_METER;

        // create the source point
        Point2D.Double source = new Point2D.Double(eastling, northling);

        // create the result point
        Point2D.Double result = new Point2D.Double(0.0, 0.0);

        // use library for conversion
        NAD_27.inverseTransform(source, result);

        // add correction to coordinate conversion
        double latitude = result.getY() + LATITUDE_CORRECTION;
        double longitude = result.getX() + LONGITUDE_CORRECTION;

        return new double[]{longitude, latitude};
    }

    /**
     * Class method for converting a GCS coordinate to a nad27 measure of eastling
     * and northling values.
     * @param latitude  the Y value in degrees
     * @param longitude the X value in degrees
     * @return          a double array, at [0] is the eastling and [1] is the northling
     */
    public static final double[] GCStoNad27 (double latitude, double longitude) {

        // Reverse the correction to the coordinates
        latitude = latitude - LATITUDE_CORRECTION;
        longitude = longitude - LONGITUDE_CORRECTION;

        // create source point
        Point2D.Double src = new Point2D.Double(longitude, latitude);

        // create destination point
        Point2D.Double dst = new Point2D.Double(0.0, 0.0);

        // use library to convert coordinates to nad27
        NAD_27.transform(src, dst);

        return new double[]{dst.getX(), dst.getY()};
    }

    public static void main (String[] args) {

        // Sample coordinates
        double[] database = {
                1825399.99612252,729498.427258271
        };

        // convert database to
        Coordinate c1 = new Coordinate(database[0], database[1], Coordinate.TYPE.NAD_27);

        System.out.println(c1.toString());


        System.out.println();

        Coordinate c2 = new Coordinate(c1.getLongitude(), c1.getLatitude(), Coordinate.TYPE.GCS);

        System.out.println(c2.toString());
    }
}
