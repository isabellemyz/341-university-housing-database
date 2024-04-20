package mypackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Student {
    public static int insertStudent(String firstName, String lastName, int year, boolean isRA, String email,
            String phoneNumber, String connectionUrl) {

        String callStoredProc = "{call dbo.insertStudent(?,?,?,?,?,?,?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setString(2, firstName);
            prepsStoredProc.setString(3, lastName);
            prepsStoredProc.setNull(4, java.sql.Types.INTEGER);
            prepsStoredProc.setInt(5, year);
            prepsStoredProc.setBoolean(6, isRA);
            prepsStoredProc.setString(7, email);
            prepsStoredProc.setString(8, phoneNumber);
    
            prepsStoredProc.registerOutParameter(1,
                    java.sql.Types.INTEGER);
            
            prepsStoredProc.execute();

            int generatedId = prepsStoredProc.getInt(1);
            System.out.println("New student ID: " + generatedId);
            
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
            return generatedId;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
