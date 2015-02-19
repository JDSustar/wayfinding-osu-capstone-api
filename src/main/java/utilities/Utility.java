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

    public static void main (String[] args) {

        String[] names = {
                "Stillman Hall",
                "Evans Lab",
                "Smith Lab",
                "Dreese Lab",
                "Prior Lib"
        };

        // Sample coordinates
        double[] database = {
                1825399.99612252,729498.427258271, // Stillman Hall
                1825298.89496999,729843.911311694, // Evans Lab
                1824759.23467167,729830.663313218, // Smith Lab
                1823961.27227782,729648.987292714, // Dreese Lab
                1823568.19746685,726938.919614419  // Prior Lib
        };

        // correct values
        double[] coordinates = {
                40.0017588, -083.0107930,
                40.0027056, -083.0111609,
                40.0026608, -083.0130869,
                40.0021496, -083.0159315,
                39.9947041, -083.0172791
        };

        for (int i = 0; i < names.length; i++) {
            System.out.println("========================================");
            System.out.format("Name: %s\n", names[i]);
            System.out.format("E/N:      %f %f\n", database[(i * 2)], database[(i * 2) + 1]);
            System.out.format("Lat/Long: %7.5f %7.5f\n",coordinates[(i * 2)], coordinates[(i * 2) + 1]);
            Coordinate tmp = new Coordinate(database[(i * 2)], database[(i * 2) + 1], Coordinate.TYPE.NAD_27);
            System.out.format("jhlabs:   %7.5f %7.5f\n", tmp.getLatitude(), tmp.getLongitude());
        }
    }
}
