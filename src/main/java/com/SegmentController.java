package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.spatial.geometry.JGeometry;

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
            String edgesSelectStatement = "SELECT A.ID, A.STREETCROSSING, A.DESCRIPTION, A.POTENTIALHAZARD," +
                    " A.ACCESSIBLE, SDO_UTIL.EXTRACT(A.GEOM, 1) AS GEOMETRY FROM ROUTELINE A";

            ResultSet rs = statement.executeQuery(edgesSelectStatement);

            LocationController lc = new LocationController();
            LocationCollection locationCollection = lc.locations();
            List<Location> locations = locationCollection.getLocations();

            while (rs.next()) {
                String streetCrossing = rs.getString("STREETCROSSING");
                String description = rs.getString("DESCRIPTION");
                String hazard = rs.getString("POTENTIALHAZARD");
                //int weight = rs.getInt("");
                int accessible = rs.getInt("ACCESSIBLE");
                int id = rs.getInt("ID");
                double[] coord = JGeometry.load((oracle.sql.STRUCT) rs.getObject("GEOMETRY")).getOrdinatesArray();
                Location startNode = new Location(id,"NOT INITIALIZED",0,0);
                Location endNode = new Location(id,"NOT INITIALIZED",0,0);
                List<Location> intermediateNodes = new ArrayList<Location>();
                for (int index = 0; index < coord.length; index+=2) {
                    for (Location loc : locations) {
                        double lat = loc.getLatitude();
                        double longit = loc.getLongitude();
                        if (coord[index] == lat && coord[index+1] == longit)
                        {
                            if(index == 0)
                            {
                                startNode = loc;
                            }
                            else if (index+1 == coord.length-1)
                            {
                                endNode = loc;
                            }
                            else
                            {
                                intermediateNodes.add(loc);
                            }
                        }
                        else
                        {
                            if(index == 0)
                            {
                                startNode = new Location(id,"StartNode",lat,longit);
                            }
                            else if (index+1 == coord.length-1)
                            {
                                endNode = new Location(id,"EndNode",lat,longit);
                            }
                            else
                            {
                                intermediateNodes.add(new Location(id,"IntermediateNode",lat,longit));
                            }
                        }
                    }
                }
                segments.add(new Segment(1, accessible, streetCrossing, description, hazard, startNode, endNode, intermediateNodes));
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
