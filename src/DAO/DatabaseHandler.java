/*
 * This is the entry point for db connectiona and running sql queries
 */

package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Actors.Admin;
import Actors.Client;
import Actors.Instructor;
import Actors.User;
import LocationAndSchedule.Schedule;
import LocationAndSchedule.Location;
import Offerings.Lesson;

public class DatabaseHandler {

  // testing db connection
  public static void main(String args[]) {

    DatabaseHandler dbHandler = new DatabaseHandler();

    try {
      dbHandler.connect();
    } catch (SQLException e) {
      System.out.println(e);
    }

    dbHandler.disconnect();
  }

  private static final String URL = "jdbc:mysql://localhost:3306/soen342_project_db";
  private static final String USER = "soen342_user";
  private static final String PASSWORD = "soen342_password";

  private Connection connection;

  // Establish a database connection
  public void connect() throws SQLException {
    if (connection == null || connection.isClosed()) {
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("Connected to the database.");
    }
  }

  // Close the database connection
  public void disconnect() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
        System.out.println("Disconnected from the database.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Executes an SQL statement for INSERT, UPDATE, or DELETE operations.
   * This method modifies the database (writing data) and can be used with
   * statements that do not return a result set.
   *
   * @param sql    The SQL query with placeholders (e.g., "INSERT INTO users
   *               (name, email) VALUES (?, ?)")
   * @param params The parameters to replace the placeholders in the SQL query
   * @return The number of rows affected by the query
   * @throws SQLException if a database access error occurs
   *
   *                      Example usage:
   *                      String insertSQL = "INSERT INTO users (name, email)
   *                      VALUES (?, ?)";
   *                      dbHandler.executeUpdate(insertSQL, "Alice",
   *                      "alice@example.com");
   *
   *                      String updateSQL = "UPDATE users SET email = ? WHERE
   *                      name = ?";
   *                      dbHandler.executeUpdate(updateSQL,
   *                      "alice.new@example.com", "Alice");
   *
   *                      String deleteSQL = "DELETE FROM users WHERE name = ?";
   *                      dbHandler.executeUpdate(deleteSQL, "Alice");
   */
  public int executeUpdate(String sql, Object... params) throws SQLException {
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }
      return stmt.executeUpdate();
    }
  }

  /**
   * Executes an SQL SELECT statement and returns the result set.
   * This method retrieves data from the database (reading data) and should be
   * used
   * with SELECT queries that return data to be processed.
   *
   * @param sql    The SQL query with placeholders (e.g., "SELECT * FROM users
   *               WHERE name = ?")
   * @param params The parameters to replace the placeholders in the SQL query
   * @return The ResultSet containing the data returned by the query
   * @throws SQLException if a database access error occurs
   *
   *                      Example usage:
   *                      String selectSQL = "SELECT * FROM users";
   *                      ResultSet rs = dbHandler.executeQuery(selectSQL);
   *                      while (rs.next()) {
   *                      int id = rs.getInt("id");
   *                      String name = rs.getString("name");
   *                      String email = rs.getString("email");
   *                      System.out.println("ID: " + id + ", Name: " + name + ",
   *                      Email: " + email);
   *                      }
   *
   *                      Example with parameters:
   *                      String selectSQLWithParam = "SELECT * FROM users WHERE
   *                      name = ?";
   *                      ResultSet rs =
   *                      dbHandler.executeQuery(selectSQLWithParam, "Alice");
   *                      if (rs.next()) {
   *                      System.out.println("User found: " + rs.getString("name")
   *                      + ", " + rs.getString("email"));
   *                      }
   */
  public ResultSet executeQuery(String sql, Object... params) throws SQLException {
    PreparedStatement stmt = connection.prepareStatement(sql);
    for (int i = 0; i < params.length; i++) {
      stmt.setObject(i + 1, params[i]);
    }
    return stmt.executeQuery();
  }

  public User getUser(String username, String password) throws SQLException {
    // Query to find the user by username
    User foundUser = null;
    String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
    try (ResultSet rs = executeQuery(sql, username, password)) {
      if (rs.next()) {
        // Create User object from result set data
        String userType = rs.getString("userType");

        if ("admin".equalsIgnoreCase(userType)) {
          foundUser = new Admin(
              rs.getLong("userId"),
              rs.getString("name"),
              rs.getString("phone"),
              rs.getDate("birthDate").toLocalDate(),
              rs.getString("username"),
              rs.getString("password"));
        } else if ("instructor".equalsIgnoreCase(userType)) {
          // Query to fetch specialization and cities for an instructor
          foundUser = getInstructor(rs.getLong("userId"));

        } else if ("client".equalsIgnoreCase(userType)) {
          // Default to a regular User if userType is 'client' or any other value
          foundUser = new Client(
              rs.getLong("userId"),
              rs.getString("name"),
              rs.getString("phone"),
              rs.getDate("birthDate").toLocalDate(),
              rs.getString("username"),
              rs.getString("password"));
        } else {
          System.err.println("Invalid user type: " + userType);
          return null;
        }

        return foundUser; // Successfully authenticated
      } else {
        System.err.println("Invalid username or password.");
        return null;
      }
    }
  }

  public Client insertClient(String name, String phone, LocalDate birthDate, String username, String password) {
    // Insert the new client into the database
    String insertSQL = """
            INSERT INTO Users (name, phone, birthDate, username, password, userType)
            VALUES (?, ?, ?, ?, ?, 'client')
        """;

    try {
      executeUpdate(insertSQL, name, phone, java.sql.Date.valueOf(birthDate), username, password);
      System.out.println("Account created successfully!");

      // Retrieve the generated userId
      String getUserSQL = "SELECT userId FROM Users WHERE username = ?";
      try (ResultSet rs = executeQuery(getUserSQL, username)) {
        if (rs.next()) {
          long userId = rs.getLong("userId");
          return new Client(userId, name, phone, birthDate, username, password); // Sccessfully created account
        }
      }
    } catch (SQLException e) {
      System.err.println("Database error: " + e.getMessage());
      return null;
    }
    return null; // Failed to create account
  }

  public ArrayList<Lesson> getLessons() throws SQLException {
    ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    String getLessonsSQL = """
            SELECT l.lessonId, l.discipline, l.instructorId, l.isPrivate, l.isAvailable,
                   l.startDate, l.endDate, l.startTime, l.endTime, l.day,
                   l.locationName, l.locationCity, l.locationProvince, l.locationAddress
            FROM Lessons l
        """;

    try (ResultSet rs = executeQuery(getLessonsSQL)) {
        while (rs.next()) {
            long instructorId = rs.getLong("instructorId");
            Instructor instructor = null;

            // Use getInstructor method if instructorId is not null
            if (instructorId != 0) {
                instructor = getInstructor(instructorId);
            }

            // Create a Schedule object
            Schedule schedule = new Schedule(
                rs.getDate("startDate"),
                rs.getDate("endDate"),
                rs.getTime("startTime"),
                rs.getTime("endTime"),
                rs.getString("day")
            );

            // Create a Location object
            Location location = new Location(
                rs.getString("locationName"),
                rs.getString("locationCity"),
                rs.getString("locationProvince"),
                rs.getString("locationAddress")
            );

            // Create a Lesson object
            Lesson lesson = new Lesson(
                rs.getLong("lessonId"),
                rs.getString("discipline"),
                instructor,
                schedule,
                location,
                rs.getBoolean("isPrivate"),
                rs.getBoolean("isAvailable")
            );

            lessons.add(lesson);
        }
    }

    return lessons;
}

  public Instructor getInstructor(long instructorId) throws SQLException {
    String instructorQuery = """
            SELECT u.userId, u.name, u.phone, u.birthDate, u.username, u.password,
                   i.speciality, ac.city
            FROM Users u
            JOIN Instructors i ON u.userId = i.instructorId
            LEFT JOIN AvailableCities ac ON i.instructorId = ac.instructorId
            WHERE u.userId = ?
        """;

    Instructor foundInstructor = null;
    ArrayList<String> availableCities = new ArrayList<>();
    String specialization = null;

    try (ResultSet instructorRs = executeQuery(instructorQuery, instructorId)) {
      boolean firstRow = true; // Flag to check if it's the first row

      while (instructorRs.next()) {
        if (firstRow) {
          specialization = instructorRs.getString("speciality");
          foundInstructor = new Instructor(
              instructorRs.getLong("userId"),
              instructorRs.getString("name"),
              instructorRs.getString("phone"),
              instructorRs.getDate("birthDate").toLocalDate(),
              instructorRs.getString("username"),
              instructorRs.getString("password"),
              specialization,
              availableCities);
          firstRow = false; // Reset the flag after processing the first row
        }

        // Collect available cities for the instructor
        String city = instructorRs.getString("city");
        if (city != null && !availableCities.contains(city)) {
          availableCities.add(city);
        }
      }
    }

    return foundInstructor;
  }

}
