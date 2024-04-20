import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import mypackage.Admin;
import mypackage.Student;

public class App {
    // want to rework this to prompt user for username (ex: ixz33), database name, user (dbuser), and password
    // then generate connectionUrl dynamically
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

                    Student.insertStudent(inpFirstName, inpLastName, inpYear, inpIsRA, inpEmail, inpPhoneNumber, connectionUrl);

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

                    Admin.updateRoomStatus(inpBuildingName, inpRoomNumber, inpAvailability, connectionUrl);
                    break;
                
                case 1:
                    // update amenities
                    System.out.println("Enter the new amenity name, then press enter: ");
                    String inpAmenityName = myObj.nextLine();

                    System.out.println("Enter the category (indoor or outdoor), then press enter: ");
                    String inpCategory = myObj.nextLine();

                    System.out.println("Enter the start time of usage (yyyy-mm-dd hh:mm:ss), then press enter: ");
                    String startTimeStr = myObj.nextLine();

                    System.out.println("Enter the end time of usage (yyyy-mm-dd hh:mm:ss), then press enter: ");
                    String endTimeStr = myObj.nextLine();

                    System.out.println("Enter the amenity's description, then press enter: ");
                    String inpDescription = myObj.nextLine();

                    System.out.println("Enter the amenity's cost, then press enter: ");
                    int inpCost = myObj.nextInt();
                    myObj.nextLine();

                    System.out.println("Enter the name of the building the amenity will be added to, then press enter: ");
                    String inpAmenityBuildingName = myObj.nextLine();

                    System.out.println("Enter how many amenities will be added, then press enter: ");
                    int inpAmenityCount = myObj.nextInt();
                    myObj.nextLine();

                    myObj.close();

                    Timestamp inpStartTime = null;
                    Timestamp inpEndTime = null;

                    try { 
                        inpStartTime = App.parseTimestamp(startTimeStr);
                        inpEndTime = App.parseTimestamp(endTimeStr);
                    } catch (ParseException e) {
                        System.err.println("Error: Invalid timestamp format. Please enter timestamp in the format yyyy-MM-dd HH:mm:ss");
                    }

                    Admin.addAmenity(inpAmenityName, inpCategory, inpStartTime, inpEndTime, inpDescription, inpCost, inpAmenityBuildingName,
                        inpAmenityCount, connectionUrl);
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
}
