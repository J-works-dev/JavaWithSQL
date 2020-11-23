package at1act4database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author 30024165 Sang Joon Lee
 * AT1 – Activity 3
 * 20/10/2020
 */
public class AT1Act4Database {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean pass = true;
        int choice;
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "";

        Connection con = null; // JDBC connection
        Statement stmt = null; // SQL statement object
        String query; // SQL query string
        ResultSet result = null; // results after SQL execution
        
        try {
            con = DriverManager.getConnection(url, user, password); // connect to MySQL
            stmt = con.createStatement();
            
            // Set up Defalt
            query = "DROP DATABASE IF EXISTS TestResult;";
            stmt.executeUpdate(query);
            System.out.println("If TestResult exists, it deleted");
            query = "CREATE DATABASE TestResult;";
            stmt.executeUpdate(query);
            System.out.println("TestResult created");
            query = "USE TestResult;";
            stmt.executeUpdate(query);
            System.out.println("now TestResult Database is on ✔");
            query = "CREATE TABLE Results (ResultID INTEGER NOT NULL AUTO_INCREMENT, Subject VARCHAR(32), Score INTEGER, PRIMARY KEY(ResultID));";
            stmt.executeUpdate(query);
            query = "INSERT INTO Results (Subject, Score)\n VALUES (\"English\", 95), (\"Math\", 98), (\"Science\", 89);";
            stmt.executeUpdate(query);
            // =========
            
            
            query = "SELECT * FROM Results;";
            result = stmt.executeQuery(query);

            System.out.printf("%8s %-11s %-7s\n",
                    "ID", "Subject", "Score");

            while (result.next()) { // loop until the end of the results
                int ResultID = result.getInt("ResultID");
                String Subject = result.getString("Subject");
                int Score = result.getInt("Score");

                System.out.printf("%7d  %-8s %7d\n",
                        ResultID, Subject, Score);
            }
            
            while (pass) {
                try {
                    System.out.print("Creat new Record(Press 1), Delete Record(Press 2), Quit(Press 0) : ");
                    choice = sc.nextInt();
                    
                    switch (choice) {
                        case 1:
                            System.out.print("Enter Subject: ");
                            String newSubject = sc.next();
                            System.out.print("Enter Score: ");
                            int newScore = sc.nextInt();
                            query = "INSERT INTO Results (Subject, Score)\n VALUES (\"" + newSubject + "\", " + newScore + ");";
                            stmt.executeUpdate(query);
                            break;
                            
                        case 2:
                            System.out.print("Enter Subject that you want to delet: ");
                            String delSubject = sc.next();
                            query = "DELETE FROM Results WHERE Subject=\"" + delSubject + "\";";
                            stmt.executeUpdate(query);
                            break;
                        
                        default :
                            pass = false;
                            break;
                    }
                    query = "SELECT * FROM Results;";
                    result = stmt.executeQuery(query);

                    System.out.printf("%8s %-11s %-7s\n",
                            "ID", "Subject", "Score");

                    while (result.next()) { // loop until the end of the results
                        int ResultID = result.getInt("ResultID");
                        String Subject = result.getString("Subject");
                        int Score = result.getInt("Score");

                        System.out.printf("%7d  %-8s %7d\n",
                                ResultID, Subject, Score);
                    }
                } catch (Exception ex) {
                    System.out.println("Invalid Input: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println("SQLException caught: " + ex.getMessage());
        } finally {
            // Close all database objects nicely
            try {
                if (result != null) {
                    result.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException caught: " + ex.getMessage());
            }
        }
    }
}
