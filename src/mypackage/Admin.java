package mypackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Admin {
    // method for updating room's status (available vs. unavailable)
    public static int updateRoomStatus(String buildingName, int roomNumber, String availability, String connectionUrl) {
        String callStoredProc = "{call dbo.updateRoomStatus(?,?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
        CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setString(1, buildingName);
            prepsStoredProc.setInt(2, roomNumber);
            prepsStoredProc.setString(3, availability);

            prepsStoredProc.execute();

            System.out.println(buildingName + ", Room #" + roomNumber + " is now " + availability);
            
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // method for adding a new amenity
    public static int addAmenity(String amenityName, String category, Timestamp startTime, Timestamp endTime, String description, int cost,
        String buildingName, int amenityCount, String connectionUrl) {
            String callStoredProc = "{call dbo.addAmenity(?,?,?,?,?,?,?,?)}";

            try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
                connection.setAutoCommit(false);

                // from user input
                prepsStoredProc.setString(1, amenityName);
                prepsStoredProc.setString(2, category);
                prepsStoredProc.setTimestamp(3, startTime);
                prepsStoredProc.setTimestamp(4, endTime);
                prepsStoredProc.setString(5, description);
                prepsStoredProc.setInt(6, cost);
                prepsStoredProc.setString(7, buildingName);
                prepsStoredProc.setInt(8, amenityCount);

                prepsStoredProc.execute();
                
                System.out.println(buildingName + " now has " + amenityCount + " of this " + category + " amenity: " + amenityName);
                
                connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return -1;
    }
}
