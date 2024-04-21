package mypackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

    public static void insertPreference(int id, boolean smoke, String music, String space, int sleep_time,
            String living_style, int roommate_count, String connectionUrl) {
        String callStoredProc = "{call dbo.insertPreference(?,?,?,?,?,?,?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setInt(1, id);
            prepsStoredProc.setBoolean(2, smoke);
            prepsStoredProc.setString(3, music);
            prepsStoredProc.setString(4, space);
            prepsStoredProc.setInt(5, sleep_time);
            prepsStoredProc.setString(6, living_style);
            prepsStoredProc.setInt(7, roommate_count);

            prepsStoredProc.execute();

            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public static void matchStudent(int studentId, String connectionUrl) {

        String callStoredProc = "{call dbo.AnalyzeStudentPreferences(?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
                    connection.setAutoCommit(false);

                    //input
                    prepsStoredProc.setInt(1, studentId);
                    try (ResultSet rs = prepsStoredProc.executeQuery()) {
                    // Print out the query result
                    while (rs.next()) {
                        int student_Id = rs.getInt("student_id");
                        String fullName = rs.getString("full_name");
                        String phoneNumber = rs.getString("phone_number");
                        String email = rs.getString("email");
                        int year = rs.getInt("year");
                        double similarityScore = rs.getDouble("similarity_score");
                        
                        // Print out the student information
                        System.out.println("Student ID: " + student_Id);
                        System.out.println("Full Name: " + fullName);
                        System.out.println("Phone Number: " + phoneNumber);
                        System.out.println("Email: " + email);
                        System.out.println("Year: " + year);
                        System.out.println("Similarity Score: " + similarityScore);
                        System.out.println();
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
