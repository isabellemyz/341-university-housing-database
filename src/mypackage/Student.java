package mypackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
            System.out.println("New student ID: " + generatedId + "\n Please remember this for other operations!");

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

            // input
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

    public static int insertValidRequest(int student_id, int staff_id, int building_id, int room_number, int amenity_id,
            String status,
            java.sql.Timestamp date_submitted, java.sql.Timestamp date_completed, String connectionUrl) {

        String callStoredProc = "{call dbo.insertValidRequest(?,?,?,?,?,?,?,?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setInt(2, student_id);
            prepsStoredProc.setNull(3, java.sql.Types.INTEGER);
            if (room_number == -1) {
                prepsStoredProc.setInt(4, building_id);
                prepsStoredProc.setNull(5, java.sql.Types.INTEGER);
                prepsStoredProc.setInt(6, amenity_id);
            } else if (amenity_id == -1) {
                prepsStoredProc.setInt(4, building_id);
                prepsStoredProc.setInt(5, room_number);
                prepsStoredProc.setNull(6, java.sql.Types.INTEGER);
            }
            prepsStoredProc.setString(7, status);
            prepsStoredProc.setTimestamp(8, date_submitted);
            prepsStoredProc.setNull(9, java.sql.Types.DATE);

            prepsStoredProc.registerOutParameter(1,
                    java.sql.Types.INTEGER);

            prepsStoredProc.execute();

            int generatedId = prepsStoredProc.getInt(1);
            System.out.println("Maintenance Request ID: " + generatedId);

            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
            return generatedId;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static void getRequestsAmenity(int building_id, int amenity_id, String connectionUrl) {

        String callStoredProc = "{call dbo.GetRequestsAmenity(?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setInt(1, building_id);
            prepsStoredProc.setInt(2, amenity_id);

            try (ResultSet rs = prepsStoredProc.executeQuery()) {
                // Print out the query result
                while (rs.next()) {
                    int request_id = rs.getInt("request_id");
                    String student_name = rs.getString("student_name");
                    String staff_name = rs.getString("staff_name");
                    String staff_email = rs.getString("staff_email");
                    String status = rs.getString("status");
                    Timestamp date_submitted = rs.getTimestamp("date_submitted");

                    // Print out request information
                    System.out.println("Request ID: " + request_id);
                    System.out.println("Student Name: " + student_name);
                    System.out.println("Staff Name: " + staff_name);
                    System.out.println("Staff Email: " + staff_email);
                    System.out.println("Status: " + status);
                    System.out.println("Date Submitted: " + date_submitted);
                    System.out.println(); // Print an empty line for readability
                }
              
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    }

    public static int createGroup(int groupSize, boolean coed, String connectionUrl) {
        String callStoredProc = "{call dbo.InsertGroup(?, ?, ?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setInt(2, groupSize);
            prepsStoredProc.setBoolean(3, coed);

            prepsStoredProc.registerOutParameter(1,
                    java.sql.Types.INTEGER);

            prepsStoredProc.execute();

            int generatedId = prepsStoredProc.getInt(1);
            System.out.println("New Group ID: " + generatedId + "/n Please remember this for joining groups!");

            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
            return generatedId;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean isGroupFull(int group_id, String connectionUrl) {
        boolean isFull = false;
        String sql = "{call getGroupCountAndSize(?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement callableStatement = connection.prepareCall(sql);) {
            callableStatement.setInt(1, group_id);
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    int group_count = resultSet.getInt("group_count");
                    int group_size = resultSet.getInt("groupSize");
                    isFull = group_count >= group_size;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isFull;
    }

    public static void joinGroup(int student_id, int group_id, String connectionUrl) {
        String callStoredProc = "{call dbo.assignGroup(?, ?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);
            // from user input
            prepsStoredProc.setInt(1, student_id);
            prepsStoredProc.setInt(2, group_id);
            prepsStoredProc.execute();
            System.out.println("student "+student_id + " joined group " + group_id);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
