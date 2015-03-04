
package com;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utilities.Coordinate;
import utilities.Utility;

import java.sql.*;
import java.sql.SQLException;
import java.util.*;

@RestController
public class LocationController
{

    /**
     * A static list to cache the locations if the API server has already hit the
     * database for this information.
     */
    private static List<Location> locations = null;

    /**
     * Contructs and returns a collection of Locations (points from the ROUTENODE table
     * of the database that have a name and id). These are the named points from the
     * database that the user will be able to interact with.
     *
     * @return LocationCollection filled with Locations
     */
    @RequestMapping("/locations")
    public LocationCollection locations()
    {
        // If the cached list is not null, it has already been filled
        // and we can skip all the work.
        if (locations != null)
        {
            return new LocationCollection(locations);
        }

        locations = new ArrayList<Location>();

        Statement statement = null;
        Connection connection = null;
        try
        {
            connection = Utility.getConnection();
            statement = connection.createStatement();

            String locationsSelectStatement = "SELECT A.ID, A.NAME, T.X, T.Y FROM ROUTENODE A, TABLE(SDO_UTIL.GETVERTICES(A.GEOM)) T WHERE A.ISDOOR = 1";
            ResultSet rs = statement.executeQuery(locationsSelectStatement);

            while (rs.next())
            {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                double spcx = rs.getDouble("X");
                double spcy = rs.getDouble("Y");
                locations.add(new Location(id, name, new Coordinate(spcx, spcy, Coordinate.TYPE.NAD_27)));
            }

            rs.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //http://www.tutorialspoint.com/jdbc/jdbc-select-records.htm
            try
            {
                if (statement != null)
                    connection.close();
            }
            catch (SQLException se)
            {
            }
            try
            {
                if (connection != null)
                    connection.close();
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            }
        }

        return new LocationCollection(locations);
    }

    @RequestMapping("/location/{id}")
    public Location getLocation(@PathVariable int id)
    {
        LocationCollection lcc = locations();
        for (Location l : lcc.getLocations())
        {
            if (l.getId() == id)
            {
                return l;
            }
        }

        return null;
    }

}