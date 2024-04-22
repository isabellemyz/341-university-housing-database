package mypackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Admin {

    // method for viewing all students
    public static int viewAllStudents(String connectionUrl) {
        String callStoredProc = "{call dbo.viewAllStudents()}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            try (ResultSet rs = prepsStoredProc.executeQuery()) {
                // Print out the query result
                while (rs.next()) {
                    int student_id = rs.getInt("student_id");
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");
                    int group_id = rs.getInt("group_id");
                    int year = rs.getInt("year");
                    boolean is_ra = rs.getBoolean("is_ra");
                    String email = rs.getString("email");
                    String phone_number = rs.getString("phone_number");
                  
                    // Print out request information
                    System.out.println("Student ID: " + student_id);
                    System.out.println("First name: " + first_name);
                    System.out.println("Last name: " + last_name);
                    System.out.println("Group ID: " + group_id);
                    System.out.println("Year: " + year);
                    System.out.println("Is RA: " + is_ra);
                    System.out.println("Email: " + email);
                    System.out.println("Phone number: " + phone_number);
                    System.out.println(); // Print an empty line for readability
                }
            }
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // method for viewing all student groups
    public static int viewAllStudentGroups(String connectionUrl) {
        String callStoredProc = "{call dbo.viewAllStudentGroups()}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);
            
            try (ResultSet rs = prepsStoredProc.executeQuery()) {
                // Print out the query result
                while (rs.next()) {
                    int groupID = rs.getInt("group_id");
                    int studentID = rs.getInt("student_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    int year = rs.getInt("year");
                    boolean isRA = rs.getBoolean("is_ra");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phone_number");
                    int groupSize = rs.getInt("group_size");
                    boolean coed = rs.getBoolean("coed");
                  
                    // Print out request information
                    System.out.println("Group ID: " + groupID);
                    System.out.println("Student ID: " + studentID);
                    System.out.println("First name: " + firstName);
                    System.out.println("Last name: " + lastName);
                    System.out.println("Year: " + year);
                    System.out.println("RA status: " + isRA);
                    System.out.println("Student email: " + email);
                    System.out.println("Student phone number: " + phoneNumber);
                    System.out.println("Group size: " + groupSize);
                    System.out.println("Coed: " + coed);
                    System.out.println(); // Print an empty line for readability
                }
            }
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // method for updating room's status (available vs. unavailable)
    public static int updateRoomStatus(int buildingID, int roomNumber, String availability, String connectionUrl) {
        String callStoredProc = "{call dbo.updateRoomStatus(?,?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
        CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setInt(1, buildingID);
            prepsStoredProc.setInt(2, roomNumber);
            prepsStoredProc.setString(3, availability);

            prepsStoredProc.execute();

            String buildingName = Admin.getBuildingNameFromID(buildingID, connectionUrl);

            System.out.println(buildingName + ", Room #" + roomNumber + " is now " + availability);
            
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // helper method
    private static String getBuildingNameFromID(int buildingID, String connectionUrl) {
        String callStoredProc = "{call dbo.getBuildingNameFromID(?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
        CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            prepsStoredProc.setInt(2, buildingID);

            prepsStoredProc.registerOutParameter(1, java.sql.Types.VARCHAR);

            prepsStoredProc.execute();

            String buildingName = prepsStoredProc.getString(1);

            connection.commit();

            return buildingName;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    // method for adding a new amenity
    public static int addAmenity(String amenityName, String category, Timestamp startTime, Timestamp endTime, String description, int cost,
                                String connectionUrl) {
            String callStoredProc = "{call dbo.addAmenity(?,?,?,?,?,?,?)}";

            try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
                connection.setAutoCommit(false);

                // from user input
                prepsStoredProc.setString(2, amenityName);
                prepsStoredProc.setString(3, category);
                prepsStoredProc.setTimestamp(4, startTime);
                prepsStoredProc.setTimestamp(5, endTime);
                prepsStoredProc.setString(6, description);
                prepsStoredProc.setInt(7, cost);

                prepsStoredProc.registerOutParameter(1, java.sql.Types.INTEGER);

                prepsStoredProc.execute();

                int amenityID = prepsStoredProc.getInt(1);
                System.out.println(amenityName + " with amenity ID of " + amenityID + " has been created");
                
                connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return -1;
    }

    // method for adding amenity to a building
    public static int addAmenityToBuilding(int buildingID, int amenityID, int amenityCount, String connectionUrl) {
        String callStoredProc = "{call dbo.addAmenityToBuilding(?,?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
                connection.setAutoCommit(false);

                // from user input
                prepsStoredProc.setInt(1, buildingID);
                prepsStoredProc.setInt(2, amenityID);
                prepsStoredProc.setInt(3, amenityCount);

                prepsStoredProc.execute();

                String amenityName = Admin.getAmenityNameFromID(amenityID, connectionUrl);
                String buildingName = Admin.getBuildingNameFromID(buildingID, connectionUrl);

                System.out.println(amenityCount + " of amenity " + amenityName + " has been added to " + buildingName);
                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        return -1;
    }


    // method for deleting an amenity from a building
    public static int deleteAmenityFromBuilding(int amenityID, int buildingID, String connectionUrl) {
        String callStoredProc = "{call dbo.deleteAmenityFromBuilding(?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
        CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setInt(1, amenityID);
            prepsStoredProc.setInt(2, buildingID);

            prepsStoredProc.execute();

            String buildingName = Admin.getBuildingNameFromID(buildingID, connectionUrl);

            System.out.print("Amenity " + amenityID + " has been removed from building " + buildingName);

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // method for deleting amenity entirely
    public static int deleteAmenity(int amenityID, String connectionUrl) {
        String callStoredProc = "{call dbo.deleteAmenity(?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
        CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            String amenityName = Admin.getAmenityNameFromID(amenityID, connectionUrl);

            // from user input
            prepsStoredProc.setInt(1, amenityID);

            prepsStoredProc.execute();

            System.out.println(amenityName + " has been removed entirely");

            connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        return -1;
    }

    // helper method for getting amenity name based on ID
    private static String getAmenityNameFromID(int amenityID, String connectionUrl) {
        String callStoredProc = "{call dbo.getAmenityNameFromID(?,?)}";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
        CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            prepsStoredProc.setInt(2, amenityID);

            prepsStoredProc.registerOutParameter(1, java.sql.Types.VARCHAR);

            prepsStoredProc.execute();

            String amenityName = prepsStoredProc.getString(1);

            connection.commit();

            return amenityName;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

}
