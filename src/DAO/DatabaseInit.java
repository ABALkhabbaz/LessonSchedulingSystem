/*
 * Will automatically connect to db and create necessary tables
 */

package DAO;

import java.sql.SQLException;

public class DatabaseInit {

  // Run this code to create necessary tables
  public static void main(String args[]) {

    DatabaseHandler dbHandler = new DatabaseHandler();

    try {
      dbHandler.connect();
      createTables(dbHandler);
    } catch (SQLException e) {
      System.out.println(e);
    }

    dbHandler.disconnect();
  }

  
  public static void createTables(DatabaseHandler dbHandler) throws SQLException {
    createUserTable(dbHandler);
    createClientTable(dbHandler);
    createAdminTable(dbHandler);
    createInstructorTable(dbHandler);
    createAvailableCitiesTable(dbHandler);
  }

  private static void createUserTable(DatabaseHandler dbHandler) throws SQLException {
    String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS User (
                userId BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                phone VARCHAR(15),
                birthDate DATE,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(100) NOT NULL,
                userType ENUM('client', 'admin', 'instructor') NOT NULL
            );
        """;

    dbHandler.executeUpdate(createUserTableSQL);
    System.out.println("Table 'User' has been created or already exists.");
  }

  private static void createClientTable(DatabaseHandler dbHandler) throws SQLException {
    String createClientTableSQL = """
            CREATE TABLE IF NOT EXISTS Client (
                clientId BIGINT PRIMARY KEY,
                FOREIGN KEY (clientId) REFERENCES User(userId) ON DELETE CASCADE
            );
        """;

    dbHandler.executeUpdate(createClientTableSQL);
    System.out.println("Table 'Client' has been created or already exists.");
  }

  private static void createAdminTable(DatabaseHandler dbHandler) throws SQLException {
    String createAdminTableSQL = """
            CREATE TABLE IF NOT EXISTS Admin (
                adminId BIGINT PRIMARY KEY,
                FOREIGN KEY (adminId) REFERENCES User(userId) ON DELETE CASCADE
            );
        """;

    dbHandler.executeUpdate(createAdminTableSQL);
    System.out.println("Table 'Admin' has been created or already exists.");
  }

  private static void createInstructorTable(DatabaseHandler dbHandler) throws SQLException {
    String createInstructorTableSQL = """
            CREATE TABLE IF NOT EXISTS Instructor (
                instructorId BIGINT PRIMARY KEY,
                speciality VARCHAR(100) NOT NULL,
                FOREIGN KEY (instructorId) REFERENCES User(userId) ON DELETE CASCADE
            );
        """;

    dbHandler.executeUpdate(createInstructorTableSQL);
    System.out.println("Table 'Instructor' has been created or already exists.");
  }

  private static void createAvailableCitiesTable(DatabaseHandler dbHandler) throws SQLException {
    String createAvailableCitiesTableSQL = """
            CREATE TABLE IF NOT EXISTS AvailableCities (
                instructorId BIGINT,
                city VARCHAR(100) NOT NULL,
                FOREIGN KEY (instructorId) REFERENCES Instructor(instructorId) ON DELETE CASCADE,
                PRIMARY KEY (instructorId, city)
            );
        """;

    dbHandler.executeUpdate(createAvailableCitiesTableSQL);
    System.out.println("Table 'AvailableCities' has been created or already exists.");
  }
}
