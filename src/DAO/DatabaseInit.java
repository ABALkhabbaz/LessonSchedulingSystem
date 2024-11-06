/*
 * Will automatically connect to db and create necessary tables
 */

package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseInit {

  // Run this code to create necessary tables
  public static void main(String args[]) {

    DatabaseHandler dbHandler = new DatabaseHandler();

    try {
      dbHandler.connect();
      createTables(dbHandler);
      addAdminUser(dbHandler);
    } catch (SQLException e) {
      System.out.println(e);
    }

    dbHandler.disconnect();
  }

  private static void createTables(DatabaseHandler dbHandler) throws SQLException {
    createUsersTable(dbHandler);
    createClientsTable(dbHandler);
    createAdminTable(dbHandler);
    createInstructorsTable(dbHandler);
    createAvailableCitiesTable(dbHandler);
    createLessonsTable(dbHandler);
    createBookingsTable(dbHandler);
  }

  private static void createUsersTable(DatabaseHandler dbHandler) throws SQLException {
    String createUsersTableSQL = """
            CREATE TABLE IF NOT EXISTS Users (
                userId BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                phone VARCHAR(15),
                birthDate DATE,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(100) NOT NULL,
                userType ENUM('client', 'admin', 'instructor') NOT NULL
            );
        """;

    dbHandler.executeUpdate(createUsersTableSQL);
    System.out.println("Table 'Users' has been created or already exists.");
  }

  private static void createClientsTable(DatabaseHandler dbHandler) throws SQLException {
    String createClientsTableSQL = """
            CREATE TABLE IF NOT EXISTS Clients (
                clientId BIGINT PRIMARY KEY,
                FOREIGN KEY (clientId) REFERENCES Users(userId) ON DELETE CASCADE
            );
        """;

    dbHandler.executeUpdate(createClientsTableSQL);
    System.out.println("Table 'Clients' has been created or already exists.");
  }

  private static void createAdminTable(DatabaseHandler dbHandler) throws SQLException {
    String createAdminsTableSQL = """
            CREATE TABLE IF NOT EXISTS Admins (
                adminId BIGINT PRIMARY KEY,
                FOREIGN KEY (adminId) REFERENCES Users(userId) ON DELETE CASCADE
            );
        """;

    dbHandler.executeUpdate(createAdminsTableSQL);
    System.out.println("Table 'Admins' has been created or already exists.");
  }

  private static void createInstructorsTable(DatabaseHandler dbHandler) throws SQLException {
    String createInstructorsTableSQL = """
            CREATE TABLE IF NOT EXISTS Instructors (
                instructorId BIGINT PRIMARY KEY,
                speciality VARCHAR(100) NOT NULL,
                FOREIGN KEY (instructorId) REFERENCES Users(userId) ON DELETE CASCADE
            );
        """;

    dbHandler.executeUpdate(createInstructorsTableSQL);
    System.out.println("Table 'Instructor' has been created or already exists.");
  }

  private static void createAvailableCitiesTable(DatabaseHandler dbHandler) throws SQLException {
    String createAvailableCitiesTableSQL = """
            CREATE TABLE IF NOT EXISTS AvailableCities (
                instructorId BIGINT,
                city VARCHAR(100) NOT NULL,
                FOREIGN KEY (instructorId) REFERENCES Instructors(instructorId) ON DELETE CASCADE,
                PRIMARY KEY (instructorId, city)
            );
        """;

    dbHandler.executeUpdate(createAvailableCitiesTableSQL);
    System.out.println("Table 'AvailableCities' has been created or already exists.");
  }

  private static void createLessonsTable(DatabaseHandler dbHandler) throws SQLException {
    String createLessonsTableSQL = """
            CREATE TABLE IF NOT EXISTS Lessons (
                lessonId BIGINT AUTO_INCREMENT PRIMARY KEY,
                discipline VARCHAR(100) NOT NULL,
                instructorId BIGINT NULL, -- Allow NULL values
                isPrivate BOOLEAN NOT NULL,
                isAvailable BOOLEAN NOT NULL,

                -- Schedule fields
                startDate DATE NOT NULL,
                endDate DATE NOT NULL,
                startTime TIME NOT NULL,
                endTime TIME NOT NULL,
                day ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') NOT NULL,

                -- Location fields
                locationName VARCHAR(100) NOT NULL,
                locationCity VARCHAR(100) NOT NULL,
                locationProvince VARCHAR(100) NOT NULL,
                locationAddress VARCHAR(255) NOT NULL,

                -- Foreign key constraint with SET NULL on delete
                FOREIGN KEY (instructorId) REFERENCES Instructors(instructorId) ON DELETE SET NULL
            );
        """;

    dbHandler.executeUpdate(createLessonsTableSQL);
    System.out.println("Table 'Lessons' has been created or already exists.");
  }

  private static void createBookingsTable(DatabaseHandler dbHandler) throws SQLException {
    String createBookingsTableSQL = """
            CREATE TABLE IF NOT EXISTS Bookings (
                bookingId BIGINT AUTO_INCREMENT PRIMARY KEY,
                lessonId BIGINT NOT NULL,
                clientId BIGINT NOT NULL,

                -- Foreign key constraint to link the booking to a lesson
                FOREIGN KEY (lessonId) REFERENCES Lessons(lessonId) ON DELETE CASCADE,

                -- Foreign key constraint to link the booking to a client
                FOREIGN KEY (clientId) REFERENCES Clients(clientId) ON DELETE CASCADE,

                -- Unique constraint to prevent duplicate bookings for the same client and lesson
                UNIQUE (lessonId, clientId)
            );
        """;

    dbHandler.executeUpdate(createBookingsTableSQL);
    System.out.println("Table 'Bookings' has been created or already exists.");
  }

  private static void addAdminUser(DatabaseHandler dbHandler) throws SQLException {
    String addAdminUserSQL = """
            INSERT INTO Users (name, phone, birthDate, username, password, userType)
            VALUES ('Admin', '1234567890', '1990-01-01', 'admin', 'admin', 'admin');
        """;

    dbHandler.executeUpdate(addAdminUserSQL);
    System.out.println("Admin user has been added to 'Users'.");

    // Retrieve the userId of the newly added admin
    String getAdminUserIdSQL = "SELECT userId FROM Users WHERE username = 'admin'";
    ResultSet resultSet = dbHandler.executeQuery(getAdminUserIdSQL);

    if (resultSet.next()) {
        long adminId = resultSet.getLong("userId");

        // Insert into the Admins table
        String addAdminToAdminsTableSQL = """
                INSERT INTO Admins (adminId)
                VALUES (?);
            """;

        dbHandler.executeUpdate(addAdminToAdminsTableSQL, adminId);
        System.out.println("Admin user has been added to 'Admins'.");
    } else {
        System.out.println("Failed to retrieve admin userId.");
    }
}


}
