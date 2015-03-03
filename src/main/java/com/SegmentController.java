package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.spatial.geometry.JGeometry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utilities.Coordinate;

@RestController
public class SegmentController {

    static List<Segment> segments = new ArrayList<Segment>();
    static List<Node> nodes = new ArrayList<Node>();

    @RequestMapping("/segments")
    public SegmentCollection segments()
    {
        if(!segments.isEmpty())
        {
            return new SegmentCollection(segments);
        }

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

            String URL = "jdbc:oracle:thin:@54.200.238.22:1521:xe";
            String USER = "system";
            String PASS = "Tibs2015";
            conn = DriverManager.getConnection(URL, USER, PASS);

            statement = conn.createStatement();
            String edgesSelectStatement = "SELECT A.ID, A.STREETCROSSING, A.DESCRIPTION, A.POTENTIALHAZARD," +
                    " A.ACCESSIBLE, SDO_UTIL.EXTRACT(A.GEOM, 1) AS GEOMETRY FROM ROUTELINE A";

            ResultSet rs = statement.executeQuery(edgesSelectStatement);

            while (rs.next()) {
                String streetCrossing = rs.getString("STREETCROSSING");
                String description = rs.getString("DESCRIPTION");
                String hazard = rs.getString("POTENTIALHAZARD");
                int accessible = rs.getInt("ACCESSIBLE");
                int id = rs.getInt("ID");
                double[] ordinatesArray = JGeometry.load((oracle.sql.STRUCT) rs.getObject("GEOMETRY")).getOrdinatesArray();
                Node startNode = null;
                Node endNode = null;
                List<Node> intermediateNodes = new ArrayList<Node>();

                for(int i = 0; i < ordinatesArray.length; i += 2)
                {
                    Coordinate coordinate = new Coordinate(ordinatesArray[i], ordinatesArray[i+1], Coordinate.TYPE.NAD_27);
                    Node newNode = null;

                    for(Node n : nodes)
                    {
                        if(Coordinate.isSamePoint(n.getCoordinate(), coordinate))
                        {
                            newNode = n;
                            break;
                        }
                    }
                    if(newNode == null)
                    {
                        newNode = new Node(coordinate);
                        nodes.add(newNode);
                    }

                    if(i == 0)
                    {
                        startNode = newNode;
                    }
                    else if(i == (ordinatesArray.length-2))
                    {
                        endNode = newNode;
                    }
                    else
                    {
                        intermediateNodes.add(newNode);
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
