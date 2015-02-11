
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
            String locationsSelectStatement = "SELECT UNIQUE name FROM ROUTENODE";

            ResultSet rs = statement.executeQuery(locationsSelectStatement);
            int i = 0;

            while (rs.next()) {
                String name = rs.getString("name");
                locations.add(new Location(i, name));
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