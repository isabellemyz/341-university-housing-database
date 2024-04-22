package mypackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Staff {
    public static void getSubmittedRequests(String connectionUrl) {
        String callStoredProc = "{call dbo.getSubmittedRequests()}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            try (ResultSet rs = prepsStoredProc.executeQuery()) {
                // Print out the query result
                while (rs.next()) {
                    int request_id = rs.getInt("request_id");
                    String student_name = rs.getString("student_name");
                    String staff_name = rs.getString("staff_name");
                    int building_id = rs.getInt("building_id");
                    int room_number = rs.getInt("room_number");
                    int amenity_id = rs.getInt("amenity_id");
                    String status = rs.getString("status");
                    Timestamp date_submitted = rs.getTimestamp("date_submitted");

                    // Print out request information
                    System.out.println("Request ID: " + request_id);
                    System.out.println("Student Name: " + student_name);
                    System.out.println("Staff Name: " + staff_name);
                    System.out.println("Building ID: " + building_id);
                    System.out.println("Room Number: " + room_number);
                    System.out.println("Amenity ID: " + amenity_id);
                    System.out.println("Status: " + status);
                    System.out.println("Date Submitted: " + date_submitted);
                    System.out.println(); // Print an empty line for readability
                }
            }
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void assignStaffToRequest(int request_id, int staff_id, String connectionUrl) {
        String callStoredProc = "{call dbo.assignStaffToRequest(?,?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setInt(1, request_id);
            prepsStoredProc.setInt(2, staff_id);

            prepsStoredProc.execute();

            System.out.println("Request ID: " + request_id + " Has successfully been updated with " + staff_id
                    + " as the staff. \n Please remember the request_id for completion for the request!");

            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        } catch (SQLException e) {
            e.printStackTrace();

    }
}

    public static void changeRequestStatusToCompleted(int request_id, int staff_id, String connectionUrl) {
        String callStoredProc = "{call dbo.changeRequestStatusToCompleted(?,?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setInt(1, request_id);
            prepsStoredProc.setInt(2, staff_id);

            prepsStoredProc.execute();

            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
            System.out.println("Request ID: " + request_id + " Has successfully been marked as completed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getStaffMaintenanceRequests(int staff_id, String connectionUrl) {
        String callStoredProc = "{call dbo.getStaffMaintenanceRequests(?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            prepsStoredProc.setInt(1, staff_id);
            try (ResultSet rs = prepsStoredProc.executeQuery()) {
                // Print out the query result
                while (rs.next()) {
                    int request_id = rs.getInt("request_id");
                    String student_name = rs.getString("student_name");
                    int building_id = rs.getInt("building_id");
                    int room_number = rs.getInt("room_number");
                    int amenity_id = rs.getInt("amenity_id");
                    String status = rs.getString("status");
                    Timestamp date_submitted = rs.getTimestamp("date_submitted");
                    Timestamp date_completed = rs.getTimestamp("date_completed");

                    // Print out request information
                    System.out.println("Request ID: " + request_id);
                    System.out.println("Student Name: " + student_name);
                    System.out.println("Building ID: " + building_id);
                    System.out.println("Room Number: " + room_number);
                    System.out.println("Amenity ID: " + amenity_id);
                    System.out.println("Status: " + status);
                    System.out.println("Date Submitted: " + date_submitted);
                    System.out.println("Date Completed: " + date_completed);
                    System.out.println(); // Print an empty line for readability
                }
            }
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
