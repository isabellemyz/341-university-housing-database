
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
import mypackage.Staff;
import mypackage.Student;

public class App {
    private static final String connectionUrl = "jdbc:sqlserver://cxp-sql-02\\ixz33;"
            + "database=UniversityHousing;"
            + "user=dbuser;"
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
                    "Please enter an action:\n\t0:Enroll a student\n\t1:Edit my profile\n\t2:Group\n\t3:View housing options\n\t4:Find matching roommate\n\t5:Maintenance");
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

                    System.out.println("Please fill out your preference for housing. Press enter after each response");
                    System.out
                            .println("Do you smoke, or do you not mind living with someone who smokes?(TRUE or FALSE)");
                    boolean inpSmoke = myObj.nextBoolean();
                    myObj.nextLine();

                    System.out.println(
                            "What kind of music do you listen to?('hip hop', 'classical', 'pop', 'rock', 'jazz')");
                    String inpMusic = myObj.nextLine().toLowerCase();

                    System.out.println(
                            "How do you want your space to be utilized?('studying', 'socializing', 'relaxing')");
                    String inpSpace = myObj.nextLine().toLowerCase();

                    System.out.println("When do you usually go to bed?(enter a number, ex. 8 = 8pm, 1 = 1 am)");
                    int inpSleep = myObj.nextInt();
                    myObj.nextLine();

                    System.out.println(
                            "How do you prefer your living space to be organized?('messy', 'clean', 'lived-in')");
                    String inpLiving = myObj.nextLine().toLowerCase();

                    System.out.println(
                            "How many roommates are you comfortable living with?(enter a number between 1 to 9)");
                    int inpRoommate = myObj.nextInt();
                    myObj.nextLine();

                    myObj.close();

                    int inpId = Student.insertStudent(inpFirstName, inpLastName, inpYear, inpIsRA, inpEmail,
                            inpPhoneNumber,
                            connectionUrl);
                    Student.insertPreference(inpId, inpSmoke, inpMusic, inpSpace, inpSleep, inpLiving, inpRoommate,
                            connectionUrl);

                    break;
                case 1:
                    // update student
                    break;
                case 2:
                    System.out.println(
                            "Please enter an action:\n\t0:Create a new Group\n\t1:Join an existing group");
                    int groupAction = myObj.nextInt();
                    if (groupAction == 0){
                        System.out.println("What is the size of this group? "); 
                        int groupSize = myObj.nextInt(); 
                        myObj.nextLine(); 

                        System.out.println("Is this a coed group? (TRUE or FALSE): "); 
                        boolean coed = myObj.nextBoolean(); 
                        myObj.nextLine(); 

                        myObj.close();


                       Student.createGroup(groupSize, coed, connectionUrl); 

                    } else if(groupAction == 1) {
                        System.out.println("What is your student id? "); 
                        int groupStudentId = myObj.nextInt(); 
                        myObj.nextLine(); 

                        System.out.println("What is the id of the group you want to join? ");
                        int joinGroupId = myObj.nextInt(); 
                        myObj.nextLine(); 

                        myObj.close();
                        
                        if (Student.isGroupFull(joinGroupId, connectionUrl)){
                            System.out.println("Group is full!"); 
                        }else{
                            Student.joinGroup(groupStudentId, joinGroupId, connectionUrl);
                        }

                    }

                    break;
                case 3:
                    // query room
                    break;
                case 4:
                    System.out.println(
                            "Please enter your student id, then press enter: ");
                    int matchId = myObj.nextInt();

                    myObj.close();

                    Student.matchStudent(matchId, connectionUrl);
                    break;
                case 5:
                    System.out.println(
                            "Please enter an action:\n\t0:Submit a new request\n\t1:Check maintenance progress on amenity");
                    int maintenanceAction = myObj.nextInt();
                    if (maintenanceAction == 0) {
                        // insert maintenance request
                        System.out.println("Enter your student ID, then press enter: ");
                        int inpSID = myObj.nextInt();

                        int inpStaffID = -1;
                        int inpBuildingID = -1;
                        int inpAmenityID = -1;
                        int inpRoomNum = -1;

                        System.out.println(
                                "Please enter where your maintenance request will be for:\n\t0: Your Room\n\t1: Building-wide Amenity");

                        int maintenanceLocation = myObj.nextInt();
                        if (maintenanceLocation == 0) {
                            System.out.println("Enter your building's ID, then press enter: ");
                            inpBuildingID = myObj.nextInt();
                            System.out.println("Enter your room number, then press enter: ");
                            inpRoomNum = myObj.nextInt();
                        } else if (maintenanceLocation == 1) {
                            System.out.println("Enter your building's ID, then press enter: ");
                            inpBuildingID = myObj.nextInt();
                            System.out.println("Enter the ID of the amenity in your building, then press enter: ");
                            inpAmenityID = myObj.nextInt();
                        }

                        String inpStatus = "submitted";
                        Timestamp inpDateSub = new Timestamp(System.currentTimeMillis());
                        Timestamp inpDateComp = null;

                        myObj.nextLine();

                        myObj.close();

                        Student.insertValidRequest(inpSID, inpStaffID, inpBuildingID, inpRoomNum, inpAmenityID,
                                inpStatus, inpDateSub, inpDateComp, connectionUrl);

                    } else if (maintenanceAction == 1) {
                        // query database for all requests related to this building_id and
                        // amenity_id/room_number and 'submitted' or 'in progress' and show who made the
                        // request (name)
                        System.out.println("Enter the building ID of the amenity, then press enter: ");
                        int inpBuildingID = myObj.nextInt();
                        System.out.println("Enter the amenity ID of the amenity, then press enter: ");
                        int inpAmenityID = myObj.nextInt();

                        myObj.nextLine();

                        myObj.close();

                        Student.getRequestsAmenity(inpBuildingID, inpAmenityID, connectionUrl);

                    }
                    break;
                default:
                    System.out.println("Invalid action.");
            }

        } else if (userType == 1) { // maintenance staff

            // This is meant to represent/simulate a login procedure for a staff member
            System.out.println("Enter your staff_id, this ID will be used in all following commands: ");
            int inpStaffID = myObj.nextInt();

            System.out.println(
                    "Please enter an action:\n\t0: See submitted requests\n\t1: Assign staff to a request \n\t2: Mark request as completed \n\t3: See requests for certain staff member");
            int staffAction = myObj.nextInt();
            myObj.nextLine();

            switch (staffAction) {
                case 0: // see all available requests that aren't done yet
                    myObj.close();

                    Staff.getSubmittedRequests(connectionUrl);
                    break;

                case 1: // assign yourself to a request

                    System.out.println("Enter the request_id of you'd like to be assigned to, then press enter: ");
                    int inpRequestID = myObj.nextInt();

                    Staff.assignStaffToRequest(inpRequestID, inpStaffID, connectionUrl);
                    break;

                    // Less happens here in the user input because there is a trigger in place in
                    // SQL
                case 2: // mark a request as completed
                    System.out.println("Enter the request_id of you'd like to mark as completed, then press enter: ");
                    inpRequestID = myObj.nextInt();

                    myObj.close();

                    Staff.changeRequestStatusToCompleted(inpRequestID, inpStaffID, connectionUrl);
                    break;

                case 3: // view all maintenance requests for specific staff_id
                    myObj.close();
                    Staff.getStaffMaintenanceRequests(inpStaffID, connectionUrl);
                    break;
            }

            ;

        } else if (userType == 2) { // admin
            System.out.println(
                    "Please enter an action:\n\t0:View database information\n\t1:Update availability information\n\t2:Update amenities");

            int adminAction = myObj.nextInt();
            myObj.nextLine();

            int inpAmenityID = -1;
            String inpAmenityName = "";
            int inpAmenityCount = -1;
            int inpBuildingID = -1;

            switch (adminAction) {
                case 0: // view database information
                    System.out.println("Please enter an action:\n\t0:View student information\n\t1:View student groups");
                    int viewInformationChoice = myObj.nextInt();
                    myObj.nextLine();

                    switch (viewInformationChoice) {
                        case 0: // view student information
                            Admin.viewAllStudents(connectionUrl);
                            break;
                        
                        case 1: // view student groups
                            Admin.viewAllStudentGroups(connectionUrl);
                            break;
                    }

                    break;

                case 1: // update availability
                    System.out.println("Enter the ID of the building of the room, then press enter: ");
                    inpBuildingID = myObj.nextInt();
                    myObj.nextLine();

                    System.out.println("Enter the room number of the room, then press enter: ");
                    int inpRoomNumber = myObj.nextInt();
                    myObj.nextLine();

                    System.out.println("Enter the updated availability (available or unavailable), then press enter: ");
                    String inpAvailability = myObj.nextLine();

                    Admin.updateRoomStatus(inpBuildingID, inpRoomNumber, inpAvailability, connectionUrl);
                    break;

                case 2: // update amenities
                    System.out.println("Please enter an action:\n\t0:Add a new amenity\n\t1:Add an amenity to a building\n\t2:Delete an amenity from a building\n\t3:Delete an amenity permanently");
                    
                    int updateAmenityChoice = myObj.nextInt();
                    myObj.nextLine();
                    

                    switch (updateAmenityChoice) {
                        case 0: // add new amenity
                            System.out.println("Enter the new amenity name, then press enter: ");
                            inpAmenityName = myObj.nextLine();

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

                            Timestamp inpStartTime = null;
                            Timestamp inpEndTime = null;

                            try {
                                inpStartTime = App.parseTimestamp(startTimeStr);
                                inpEndTime = App.parseTimestamp(endTimeStr);
                            } catch (ParseException e) {
                                System.err.println(
                                        "Error: Invalid timestamp format. Please enter timestamp in the format yyyy-MM-dd HH:mm:ss");
                            }

                            myObj.close();

                            Admin.addAmenity(inpAmenityName, inpCategory, inpStartTime, inpEndTime, inpDescription, inpCost, connectionUrl);

                            break;

                        case 1: // add amenity TO A BUILDING
                            System.out.println("Enter the ID of the building you want to add the amenity to, then press enter: ");
                            inpBuildingID = myObj.nextInt();
                            myObj.nextLine();

                            System.out.println("Enter the ID of the amenity that you want to add, then press enter: ");
                            inpAmenityID = myObj.nextInt();
                            myObj.nextLine();

                            System.out.println("Enter how many of this amenity you would like to add to the building, then press enter: ");
                            inpAmenityCount = myObj.nextInt();
                            myObj.nextLine();

                            myObj.close();

                            Admin.addAmenityToBuilding(inpBuildingID, inpAmenityID, inpAmenityCount, connectionUrl);
                            break;
                
                        case 2: // delete amenity FROM BUILDING
                                System.out.println("Enter the ID of the amenity that will be deleted, then press enter: ");
                                inpAmenityID = myObj.nextInt();
                                myObj.nextLine();

                                System.out.println("Enter the ID of the building that houses the amenity, then press enter: ");
                                inpBuildingID = myObj.nextInt();
                                myObj.nextLine();

                                Admin.deleteAmenityFromBuilding(inpAmenityID, inpBuildingID, connectionUrl);

                                System.out.println("\nWould you like to remove this amenity entirely (yes or no)?");
                                String deleteAmenityChoice = myObj.nextLine();

                                switch (deleteAmenityChoice) {
                                    case("yes"):
                                        Admin.deleteAmenity(inpAmenityID, connectionUrl);
                                        break;
                                    
                                    case("no"):
                                        myObj.close();
                                        break;
                                    
                                    default:
                                        System.out.println("Invalid action");
                                }
                                
                                break;
                    
                        case 3:
                            System.out.println("Enter the ID of the amenity that you want to delete entirely, then press enter: ");
                            inpAmenityID = myObj.nextInt();
                            myObj.nextLine();

                            myObj.close();

                            Admin.deleteAmenity(inpAmenityID, connectionUrl);
                            break;

                        default:
                            System.out.println("Invalid action.");
                            break;
                    }
                    
                    break;  
                
            }

        } else {
            throw new IllegalArgumentException("Please enter a value between 0 to 2");
        }
        // }
    }

    // helper method to parse timestamps
    private static Timestamp parseTimestamp(String timestampStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Timestamp(dateFormat.parse(timestampStr).getTime());
    }
}
