
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
public class BuildingController {
    /**
     * A static list to cache the buildings if the API server has already hit the
     * database for this information.
     */
    private static List<Building> buildings = null;

    /**
     * Constructs and returns a collection of Buildings (points from the ROUTENODE table
     * of the database that have a name and id). These are the named points from the
     * database that the user will be able to interact with.
     *
     * @return BuildingCollection filled with Buildings
     */
    @RequestMapping("/buildings")
    public BuildingCollection buildings()
    {
        // If the cached list is not null, it has already been filled
        // and we can skip all the work.
        if (buildings != null)
        {
            return new BuildingCollection(buildings);
        }

        buildings = new ArrayList<Building>();

        Statement statement = null;
        Statement doorStatement = null;
        Connection connection = null;
        try
        {
            connection = Utility.getConnection();
            statement = connection.createStatement();

            String buildingsSelectStatement = "SELECT A.ID, A.NAME, A.BUILDINGID FROM ROUTENODE A WHERE A.ISLOCATION = 1 AND " +
                    "NAME IS NOT NULL AND LOCATIONGUID IS NOT NULL AND BUILDINGID IS NOT NULL";
            ResultSet rs = statement.executeQuery(buildingsSelectStatement);

            while (rs.next())
            {
                List<Location> doors = new ArrayList<Location>();
                doorStatement = connection.createStatement();
                String doorsSelectStatement = "SELECT A.ID, A.NAME, A.BUILDINGID, T.X, T.Y  FROM ROUTENODE A, TABLE(SDO_UTIL.GETVERTICES(A.GEOM)) T " +
                        "WHERE A.ISDOOR = 1 AND A.BUILDINGID = " + rs.getString("BUILDINGID");
                ResultSet doorsRs = doorStatement.executeQuery(doorsSelectStatement);
                while (doorsRs.next())
                {
                    doors.add(new Location(doorsRs.getInt("ID"), doorsRs.getString("NAME"),
                            new Coordinate(doorsRs.getDouble("X"),doorsRs.getDouble("Y"),Coordinate.TYPE.NAD_27)));
                }
                doorsRs.close();
                int id = rs.getInt("ID");
                int buildingId = Integer.parseInt(rs.getString("BUILDINGID"));
                String name = rs.getString("NAME");
                if (doors.size() > 0) {
                    buildings.add(new Building(doors, name, id, buildingId));
                }
            }
            rs.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
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

        return new BuildingCollection(buildings);
    }

    @RequestMapping("/building/{buildingId}")
    public Building getBuilding(@PathVariable int buildingId)
    {
        BuildingCollection lcc = buildings();
        for (Building b : lcc.getBuildings())
        {
            if (b.getBuildingId() == buildingId)
            {
                return b;
            }
        }

        return null;
    }

}
