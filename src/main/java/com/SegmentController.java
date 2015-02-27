package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.spatial.geometry.JGeometry;
import utilities.Coordinate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegmentController {

    @RequestMapping("/segments")
    public SegmentCollection segments(LocationCollection locations)
    {
        List<Segment> segments = new ArrayList<Segment>();
        boolean locfound = false;
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

//            LocationController lc = new LocationController();
//            LocationCollection locationCollection = lc.locations();
//            List<Location> locations = locationCollection.getLocations();

            while (rs.next()) {
                String streetCrossing = rs.getString("STREETCROSSING");
                String description = rs.getString("DESCRIPTION");
                String hazard = rs.getString("POTENTIALHAZARD");
                //int weight = rs.getInt("");
                int accessible = rs.getInt("ACCESSIBLE");
                int id = rs.getInt("ID");
                double[] coord = JGeometry.load((oracle.sql.STRUCT) rs.getObject("GEOMETRY")).getOrdinatesArray();
                Location startNode = new Location(id,"NOT INITIALIZED", new Coordinate());
                Location endNode = new Location(id,"NOT INITIALIZED", new Coordinate());
                List<Location> intermediateNodes = new ArrayList<Location>();
                for (int index = 0; index < coord.length; index+=2) {
                    Coordinate c = new Coordinate(coord[index], coord[index+1], Coordinate.TYPE.NAD_27);
                    for (Location loc : locations.getLocations()) {
                        if (Coordinate.isSamePoint(loc.getCoordinate(), c))
                        {
                            if(index == 0)
                            {
                                locfound=true;
                                startNode = loc;
                            }
                            else if (index+1 == coord.length-1)
                            {
                                locfound=true;
                                endNode = loc;
                            }
                        }
                        else
                        {
                            if(index == 0)
                            {
                                locfound=true;
                                startNode = new Location(id,"StartNode", c);
                            }
                            else if (index+1 == coord.length-1)
                            {
                                locfound=true;
                                endNode = new Location(id,"EndNode", c);
                            }
                        }
                    }
                    if(!locfound){
                        intermediateNodes.add(new Location(id, "INTERMEDIATENODE", c));
                    } else {
                        locfound=false;
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
