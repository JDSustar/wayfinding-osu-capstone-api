package utilities;

/**
 * Created by tjf3191 on 2/16/15.
 */
public class Coordinate {

    /**
     * TYPE is for the constructor to differ which data is the original data.
     */
    public static enum TYPE {
        NAD_27_TO_GCS,
        GCS_TO_NAD_27
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

        if (type == TYPE.NAD_27_TO_GCS) {
            this.eastling = x;
            this.northling = y;
            results = Utility.Nad27toGCS(eastling, northling);
            this.latitude = results[1];
            this.longitude = results[0];

        } else if (type == TYPE.GCS_TO_NAD_27) {
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

}
