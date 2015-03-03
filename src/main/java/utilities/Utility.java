package utilities;

import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by tjf3191 on 2/16/15.
 */

import jhlabs.map.proj.ProjectionFactory;
import jhlabs.map.proj.Projection;

public class Utility {

    // Class variable for calculating the projection
    private static final Projection nad27 = ProjectionFactory.getNamedPROJ4CoordinateSystem("nad27:3402");

    // Class variable for converting survey feet to meters
    public static final double survey2meter = 1200.0 / 3937.0;

    /**
     * Class method for converting a nad27 measure of eastling and northling
     * values to GCS coordinates.
     *
     * @param eastling  the X value in feet
     * @param northling the Y value in feet
     * @return returns a double point, at [0] is longitude and [1] is latitude
     */
    public static final double[] Nad27toGCS(double eastling, double northling) {

        Point2D.Double result = new Point2D.Double(0.0, 0.0);

        // Using library for conversion
        nad27.inverseTransform(new Point2D.Double(eastling * survey2meter, northling * survey2meter), result);

        // Standard correction to coordinates
        double latitude = result.getY() + 0.00049;
        double longitude = result.getX() + 0.11241;

        return new double[]{longitude, latitude};
    }

    public static final double[] GCStoNad27(double lat, double lng) {

        // Using library for conversion


        return new double[]{};
    }

    public static void main(String[] args) {

        // Sample coordinates
        double[] database = {
                0.0, 0.0,
                Math.sqrt(1.1), Math.sqrt(1.1)
        };

        Coordinate c1 = new Coordinate(database[0], database[1], Coordinate.TYPE.NAD_27);
        Coordinate c2 = new Coordinate(database[2], database[3], Coordinate.TYPE.NAD_27);

        System.out.println(Coordinate.distance(c1, c2));
        System.out.println(Coordinate.isSamePoint(c1, c2));
    }

    public static Connection getConnection() throws SQLException
    {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }

        String URL = "jdbc:oracle:thin:@54.200.238.22:1521:xe";
        String USER = "system";
        String PASS = "Tibs2015";
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
