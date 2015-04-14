package com;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utilities.Coordinate;
import utilities.Utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TourController
{
    private static List<Tour> tours;

    @RequestMapping("/tours")
    public TourCollection tours()
    {
        if(tours != null)
        {
            return new TourCollection(tours);
        }

        tours = new ArrayList<Tour>();

        Statement statement = null;
        Statement doorStatement = null;
        Connection connection = null;
        try
        {
            connection = Utility.getConnection();
            statement = connection.createStatement();

            String tourSelectStatement = "SELECT * FROM TOURS";
            ResultSet rs = statement.executeQuery(tourSelectStatement);

            while(rs.next())
            {
                int id = rs.getInt("ID");
                String tourName = rs.getString("NAME");

                List<Coordinate> tourCoordinates = new ArrayList<Coordinate>();

                Statement tourNodesStatement = connection.createStatement();
                String tourNodesSelectStatement = "SELECT * FROM TOURNODES WHERE TOURID = " + id + " ORDER BY NODEORDER";
                ResultSet nodesRs = tourNodesStatement.executeQuery(tourNodesSelectStatement);

                while(nodesRs.next())
                {
                    tourCoordinates.add(new Coordinate(nodesRs.getFloat("LATITUDE"), nodesRs.getFloat("LONGITUDE"), Coordinate.TYPE.GCS));
                }

                tours.add(new Tour(rs.getString("NAME"), rs.getInt("ID"), new Route(tourCoordinates, null, null)));

                nodesRs.close();
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

        return new TourCollection(tours);
    }

    @RequestMapping("/tourRoute")
    public Route getRouteForTour(@RequestParam(value = "id") int id)
    {
        Tour t = getTour(id);

        return t.getRoute();
    }

    @RequestMapping("/tour/{id}")
    public Tour getTour(@PathVariable int id)
    {
        for(Tour t : this.tours().getTours())
        {
            if(t.getId() == id)
            {
                return t;
            }
        }

        return null;
    }
}
