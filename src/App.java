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
            System.out
                    .println("Welcome to the University Housing Database!\nI'm a:\n\t0:Student\n\t1:Staff\n\t2:Admin");
            int temp = myObj.nextInt();
            if (temp == 0) {
                System.out.println("Please enter an action:\n\t0:Enroll a student\n\t1:Create a new group");
                temp = myObj.nextInt();
            } else if (temp == 1) {
                System.out.println("im one!");
            } else if (temp == 2) {
                System.out.println("im admin");
            } else {
                throw new IllegalArgumentException("Please enter a value between 0 to 2");
            }
        //}
    }

}