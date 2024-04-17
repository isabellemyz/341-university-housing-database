import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    public static void main(String args[]) {
        Scanner myObj = new Scanner(System.in);
        //while (true) {
            System.out.println("Welcome to the University Housing Database!\nI'm a:\n\t0:Student\n\t1:Staff\n\t2:Admin");
            int userType = myObj.nextInt();
            if (userType == 0) { //student
                System.out.println("Please enter an action:\n\t0:Enroll a student\n\t1:Edit my profile\n\t2:Create a new group\n\t3:View housing options\n\t4:match me\n\t5:maintenance");
                int studentAction = myObj.nextInt();
                switch (studentAction) {
                    case 0:
                        //insert student
                    case 1:
                        //update student
                    case 2:
                        //insertGroup
                    case 3:
                        //query room
                    case 4:
                        //query student preference 
                    case 5:
                    System.out.println("Please enter an action:\n\t0:Submit a new request\n\t1:Check my maintenance progress");
                        int maintenanceAction = myObj.nextInt();
                        if(maintenanceAction == 0){
                            //insertMaintenance
                        }else if (maintenanceAction == 1){
                            //query maintenance
                        }
                    default:
                        System.out.println("Invalid action.");
                }

            } else if (userType == 1) { //Staff
                System.out.println("im one!"); //change this
            } else if (userType == 2) {
                System.out.println("im admin");//change this 
            } else {//admin
                throw new IllegalArgumentException("Please enter a value between 0 to 2");
            }
        //}
    }

}