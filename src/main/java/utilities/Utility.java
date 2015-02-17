package utilities;

import java.awt.geom.Point2D;

/**
 * Created by tjf3191 on 2/16/15.
 */

import jhlabs.map.proj.ProjectionFactory;
import jhlabs.map.proj.Projection;

public class Utility {

    // Class variable for calculating the projection
    private static final Projection nad27= ProjectionFactory.getNamedPROJ4CoordinateSystem("nad27:3402");

    // Class variable for converting survey feet to meters
    public static final double survey2meter = 1200.0 / 3937.0;

    /**
     * Class method for converting a nad27 measure of eastling and northling
     * values to GCS coordinates.
     * @param eastling  the X value in feet
     * @param northling the Y value in feet
     * @return          returns a double point, at [0] is longitude and [1] is latitude
     */
    public static final double[] Nad27toGCS (double eastling, double northling) {

        Point2D.Double result = new Point2D.Double(0.0, 0.0);

        // Using library for conversion
        nad27.inverseTransform(new Point2D.Double(eastling * survey2meter, northling * survey2meter), result);

        // Standard correction to coordinates
        double latitude = result.getY() + 0.00049;
        double longitude = result.getX() + 0.11241;

        return new double[]{longitude, latitude};
    }

    public static final double[] GCStoNad27 (double lat, double lng) {

        // Using library for conversion


        return new double[]{};
    }
}
