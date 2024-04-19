import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    private static final String connectionUrl = "jdbc:sqlserver://cxp-sql-03\\txl790;" // PUT YOUR INFO HERE
            + "database=UniversityHousing;"
            + "user=dbuser;" // MAKE SURE YOU HAVE dbuser AND CORRECT PASSWORD ON YOUR DB
            + "password=csds341143sdsc;"
            + "encrypt=true;"
            + "trustServerCertificate=true;"
            + "loginTimeout=15;";

    public static void main(String args[]) {

        Scanner myObj = new Scanner(System.in);
        // while (1) {
        System.out.println("Welcome to the University Housing Database!\nI'm a:\n\t0:Student\n\t1:Staff\n\t2:Admin");
        int userType = myObj.nextInt();
        myObj.nextLine();

        if (userType == 0) { // student
            System.out.println(
                    "Please enter an action:\n\t0:Enroll a student\n\t1:Edit my profile\n\t2:Create a new group\n\t3:View housing options\n\t4:Match me\n\t5:Maintenance");
            int studentAction = myObj.nextInt();
            myObj.nextLine();

            switch (studentAction) {
                case 0:
                    // insert student
                    System.out.println("Enter student's first name, then press enter: ");
                    String inpFirstName = myObj.nextLine();
                    
                    System.out.println("Enter student's last name, then press enter: ");
                    String inpLastName = myObj.nextLine();

                    System.out.println("Enter student's year, then press enter: ");
                    int inpYear = myObj.nextInt();
                    myObj.nextLine();
                    
                    System.out.println("Enter if student is RA (TRUE or FALSE), then press enter: ");
                    boolean inpIsRA = myObj.nextBoolean();
                    myObj.nextLine();

                    System.out.println("Enter student's email, then press enter: ");
                    String inpEmail = myObj.nextLine();
                    
                    System.out.println("Enter student's phone number (XXX-XXX-XXXX), then press enter: ");
                    String inpPhoneNumber = myObj.nextLine();

                    myObj.close();

                    App.insertStudent(inpFirstName, inpLastName, inpYear, inpIsRA, inpEmail, inpPhoneNumber);

                    break;
                case 1:
                    // update student
                    break;
                case 2:
                    // insertGroup
                    break;
                case 3:
                    // query room
                    break;
                case 4:
                    // query student preference
                    break;
                case 5:
                    System.out.println(
                            "Please enter an action:\n\t0:Submit a new request\n\t1:Check my maintenance progress");
                    int maintenanceAction = myObj.nextInt();
                    if (maintenanceAction == 0) {
                        // insertMaintenance
                    } else if (maintenanceAction == 1) {
                        // query maintenance
                    }
                    break;
                default:
                    System.out.println("Invalid action.");
            }

        } else if (userType == 1) { // maintenance staff
            System.out.println(
                "Please enter an action:\n\t0:"

            ); // change this


        } else if (userType == 2) { // admin
            System.out.println(
                "Please enter an action:\n\t0:Update availability information\n\t1:Update amenities\n\t2:Assign groups"
            );

            int adminAction = myObj.nextInt();
            myObj.nextLine();
            
            switch (adminAction) {
                case 0:
                    // update availability
                    System.out.println("Enter the building of the room, then press enter: ");
                    String inpBuildingName = myObj.nextLine();

                    System.out.println("Enter the room number of the room, then press enter: ");
                    int inpRoomNumber = myObj.nextInt();
                    myObj.nextLine();

                    System.out.println("Enter the updated availability (available or unavailable), then press enter: ");
                    String inpAvailability = myObj.nextLine();

                    App.updateRoomStatus(inpBuildingName, inpRoomNumber, inpAvailability);
                    break;
                
                case 1:
                    // update amenities
                    break;
                
                case 2:
                    // assign groups
                    break;
                
                default:
                    System.out.println("Invalid action.");
            }
            
        } else {
            throw new IllegalArgumentException("Please enter a value between 0 to 2");
        }
        // }
    }

    // methods for procedures

    // insertStudents WORKS!
    public static int insertStudent(String firstName, String lastName, int year, boolean isRA, String email,
            String phoneNumber) {

        String callStoredProc = "{call dbo.insertStudent(?,?,?,?,?,?,?,?)}";

        try (
                Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);

            // from user input
            prepsStoredProc.setString(2, firstName);
            prepsStoredProc.setString(3, lastName);
            prepsStoredProc.setInt(5, year);
            prepsStoredProc.setNull(4, java.sql.Types.INTEGER);
            prepsStoredProc.setBoolean(6, isRA);
            prepsStoredProc.setString(7, email);
            prepsStoredProc.setString(8, phoneNumber);

            prepsStoredProc.registerOutParameter(1,
                    java.sql.Types.INTEGER);
            
            prepsStoredProc.execute();
            int generatedId = prepsStoredProc.getInt(1);
            System.out.println("New student ID: " +
                    generatedId);
            
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
            return generatedId;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return -1;

    }

    // updateRoomStatus is done
    public static int updateRoomStatus(String buildingName, int roomNumber, String availability) {
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



    public static int insertAmenity(String name, String category, String startTime, String endTime, String accessibility, int cost) {
        return -1;
    }
}
