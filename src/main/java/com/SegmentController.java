package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 2/13/2015.
 */
public class SegmentController {

    public SegmentCollection segments()
    {
        List<Segment> segments = new ArrayList<Segment>();

        Statement statement = null;
        Connection conn = null;
        try {

            try {
                Class.forName("oracle.jdbc.OracleDriver");
            }
            catch(ClassNotFoundException ex) {
                System.out.println("Error: unable to load driver class!");
                System.exit(1);
            }

            String URL = "jdbc:oracle:thin:@cseosuwintest.cloudapp.net:1521:xe";
            String USER = "system";
            String PASS = "Tibs2015";
            conn = DriverManager.getConnection(URL, USER, PASS);

            statement = conn.createStatement();
            String edgesSelectStatement = "SELECT UNIQUE id FROM ROUTELINE";

            ResultSet rs = statement.executeQuery(edgesSelectStatement);
            int i = 0;

            while (rs.next()) {
                String streetCrossing = rs.getString("streetcrossing");
                String description = rs.getString("description");
                String hazard = rs.getString("potentialhazard");
                //int weight = rs.getInt("");
                int accessible = rs.getInt("accessible");
                segments.add(new Segment(1,accessible,streetCrossing,description,hazard));
                i++;
            }

            rs.close();

            //This SQL statement gets the X and Y coordinates from the GEOM column
            /*SELECT c.ID, t.X, t.Y
            FROM ROUTELINE c,
                    TABLE(SDO_UTIL.GETVERTICES(c.GEOM)) t;*/
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //http://www.tutorialspoint.com/jdbc/jdbc-select-records.htm
            try {
                if (statement != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
        return new SegmentCollection(segments);
    }
}
