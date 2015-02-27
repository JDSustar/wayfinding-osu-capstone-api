package utilities;

/**
 * Created by tjf3191 on 2/16/15.
 */
public class Coordinate {

    /**
     *  The max distance for points to be considered as the same point (in miles)
     */
    private static double EPSILON = 0.0002840909;

    /**
     * TYPE is for the constructor to differ which data is the original data.
     */
    public static enum TYPE {
        NAD_27,
        GCS
    };

    /**
     * The private class data fields.
     */
    private double eastling;
    private double northling;
    private double latitude;
    private double longitude;

    /**
     * The default constructor
     */
    public Coordinate() {
        this.eastling = 0.0;
        this.northling = 0.0;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    /**
     * The primary constructor to build a coordinate and calculate the opposing format
     * @param x the x value of the coordinate, either eastling(feet) or longitude(degrees)
     * @param y the y value of the coordinate, either northling(feet) or latitude(degrees)
     * @param type the type of conversion to calculate
     */
    public Coordinate (double x, double y, Coordinate.TYPE type) {

        double[] results;

        if (type == TYPE.NAD_27) {
            this.eastling = x;
            this.northling = y;
            results = Utility.Nad27toGCS(eastling, northling);
            this.latitude = results[1];
            this.longitude = results[0];

        } else if (type == TYPE.GCS) {
            this.latitude = y;
            this.longitude = x;
            results = Utility.GCStoNad27(this.latitude, this.longitude);
            this.eastling = results[1];
            this.northling = results[0];
        }
    }

    /**
     * @return the eastling value
     */
    public double getEastling(){
        return this.eastling;
    }

    /**
     * @return the northling value
     */
    public double getNorthling(){
        return this.northling;
    }

    /**
     * @return the latitude value
     */
    public double getLatitude(){
        return this.latitude;
    }

    /**
     * @return the longitude value
     */
    public double getLongitude(){
        return this.longitude;
    }

    /**
     * Calculates the distance between two points in feet.
     * @param c1 a coordinate
     * @param c2 another coordinate
     * @return the double value of the distance between the two points
     */
    public static double distance (Coordinate c1, Coordinate c2) {
        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output
        double dLat = Math.toRadians(c2.getLatitude()-c1.getLatitude());
        double dLng = Math.toRadians(c2.getLongitude()-c1.getLongitude());
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(c1.getLatitude())) * Math.cos(Math.toRadians(c2.getLatitude()));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        return dist; // output distance, in MILES

    }

    /**
     * Compares two points by distance, and determines if the points are
     *   close enough to be considered as the same point.
     * @param c1 a coordinate
     * @param c2 another coordinate
     * @return true if the points are within EPSILON and false otherwise
     */
    public static boolean isSamePoint (Coordinate c1, Coordinate c2) {
        return distance(c1, c2) < EPSILON;
    }

}
