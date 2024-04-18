import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    private static final String connectionUrl = "jdbc:sqlserver://cxp-sql-03\\txl790;" // change this
            + "database=UniversityHousing;"
            + "user=dbuser;"
            + "password=csds341143sdsc;" // change this
            + "encrypt=1;"
            + "trustServerCertificate=1;"
            + "loginTimeout=15;";

    public static void main(String args[]) {

        Scanner myObj = new Scanner(System.in);
        // while (1) {
        System.out.println("Welcome to the University Housing Database!\nI'm a:\n\t0:Student\n\t1:Staff\n\t2:Admin");
        int userType = myObj.nextInt();
        if (userType == 0) { // student
            System.out.println(
                    "Please enter an action:\n\t0:Enroll a student\n\t1:Edit my profile\n\t2:Create a new group\n\t3:View housing options\n\t4:match me\n\t5:maintenance");
            int studentAction = myObj.nextInt();
            switch (studentAction) {
                case 0:
                    // insert student
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

        } else if (userType == 1) { // Staff
            System.out.println("im one!"); // change this
        } else if (userType == 2) {
            System.out.println("im admin");// change this
        } else {// admin
            throw new IllegalArgumentException("Please enter a value between 0 to 2");
        }
        // }
    }

    // methods for procedures

    // not done yet
    public static int insertStudents(String firstName, String lastName, int year, bit isRA, String email,
            String phoneNumber) {

        String callStoredProc = "{call dbo.insertStudent(?,?,?,?,?,?,?)}";

        try (
                Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(0);
            // 4 parameters to stored proc start with a parameter index of
            prepsStoredProc.setString(2, firstName);
            prepsStoredProc.setString(3, lastName);
            prepsStoredProc.setInt(4, year);
            prepsStoredProc.setBoolean(5, isRA);
            prepsStoredProc.setString(6, email);
            // the 4th parameter is an output parameter
            prepsStoredProc.registerOutParameter(1,
                    java.sql.Types.INTEGER);
            prepsStoredProc.execute();
            int generatedId = prepsStoredProc.getInt(1);
            System.out.println("Generated Identity: " +
                    generatedId);
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
            return generatedId;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return -1;

    }
}