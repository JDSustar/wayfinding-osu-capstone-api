package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

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
            String edgesSelectStatement = "SELECT A.ID, SDO_UTIL.EXTRACT(A.GEOM, 1) FROM ROUTELINE A";

            ResultSet rs = statement.executeQuery(edgesSelectStatement);

            while (rs.next()) {
                int id = rs.getInt("ID");
                double[] coord = JGeometry.load((oracle.sql.STRUCT) rs.getObject(2)).getOrdinatesArray();
                Location node1= new Location(id, "NA", coord[0], coord[1]);
                LocationCollection lc = new LocationCollection();
                int lccount=0;

                for(int i=2; i<coord.length-1; i++){
                    Location node_temp = new Location(id, "NA", coord[i], coord[i+1]);
                    lc.add(lccount, node_temp);
                    lccount++;
                    i++;
                }

                for(Location l : lc.getLocations()) {
                    segments.add(new Segment(id, node1, l));
                }
            }

            rs.close();
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
