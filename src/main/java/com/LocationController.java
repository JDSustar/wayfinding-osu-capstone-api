
package com;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import java.sql.SQLException;
import java.util.*;

@RestController
public class LocationController {
    @RequestMapping("/locations")
    public LocationCollection locations() {

        List<Location> locations = new ArrayList<Location>();

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
            String locationsSelectStatement = "SELECT A.ID, A.NAME, T.X, T.Y FROM ROUTENODE A, TABLE(SDO_UTIL.GETVERTICES(A.GEOM)) T";

            ResultSet rs = statement.executeQuery(locationsSelectStatement);
            int i = 0;

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                double spcx = rs.getDouble("X");
                double spcy = rs.getDouble("Y");
                locations.add(new Location(id, name, spcx, spcy));
                i++;
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

        return new LocationCollection(locations);
    }
}