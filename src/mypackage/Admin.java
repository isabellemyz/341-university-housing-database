package mypackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Admin {
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
}
