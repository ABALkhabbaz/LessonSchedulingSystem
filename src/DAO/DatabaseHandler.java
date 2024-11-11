/*
 * This is the entry point for db connectiona and running sql queries
 */

package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import Actors.Person;
import Actors.Admin;
import Actors.Client;
import Actors.Instructor;
import Actors.User;
import Booking.Booking;
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

  private static boolean isConnected = false;

  // Establish a database connection
  public void connect() throws SQLException {
    if (connection == null || connection.isClosed()) {
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
      if (!isConnected) {
        System.out.println("Connected to the database.");
        isConnected = true;
      }
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

          String insertClientSQL = """
                  INSERT INTO Clients (clientId)
                  VALUES (?)
              """; // Insert the client into the Clients table
          executeUpdate(insertClientSQL, userId);

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
            rs.getString("day"));

        // Create a Location object
        Location location = new Location(
            rs.getString("locationName"),
            rs.getString("locationCity"),
            rs.getString("locationProvince"),
            rs.getString("locationAddress"));

        // Create a Lesson object
        Lesson lesson = new Lesson(
            rs.getLong("lessonId"),
            rs.getString("discipline"),
            instructor,
            schedule,
            location,
            rs.getBoolean("isPrivate"),
            rs.getBoolean("isAvailable"));

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

  public void insertLesson(Lesson lesson) {
    String insertLessonSQL = """
            INSERT INTO Lessons (
                discipline,
                instructorId,
                isPrivate,
                isAvailable,
                startDate,
                endDate,
                startTime,
                endTime,
                day,
                locationName,
                locationCity,
                locationProvince,
                locationAddress
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

    // Prepare statement and bind values
    try {
      executeUpdate(insertLessonSQL,
          lesson.getDiscipline(),
          lesson.getInstructor(),
          lesson.isPrivate(),
          lesson.isAvailable(),
          lesson.getSchedule().getStartDate(),
          lesson.getSchedule().getEndDate(),
          lesson.getSchedule().getStartTime(),
          lesson.getSchedule().getEndTime(),
          lesson.getSchedule().getDay(),
          lesson.getLocation().getName(),
          lesson.getLocation().getCity(),
          lesson.getLocation().getProvince(),
          lesson.getLocation().getAddress());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    System.out.println("Lesson inserted into the database successfully!");
  }

  public ArrayList<Lesson> getAllLessons(Person p) {
    // Check if the person is an Admin
    if (!(p instanceof Admin)) {
      System.out.println("You are not authorized to view all lessons");
      return null;
    }

    ArrayList<Lesson> lessons = new ArrayList<>();
    String query = "SELECT * FROM Lessons";

    try {
      ResultSet resultSet = executeQuery(query);
      while (resultSet.next()) {
        long lessonId = resultSet.getLong("lessonId");
        String discipline = resultSet.getString("discipline");
        long instructorId = resultSet.getLong("instructorId");
        boolean isPrivate = resultSet.getBoolean("isPrivate");
        boolean isAvailable = resultSet.getBoolean("isAvailable");
        Date startDate = resultSet.getDate("startDate");
        Date endDate = resultSet.getDate("endDate");
        Time startTime = resultSet.getTime("startTime");
        Time endTime = resultSet.getTime("endTime");
        String day = resultSet.getString("day");
        String locationName = resultSet.getString("locationName");
        String locationCity = resultSet.getString("locationCity");
        String locationProvince = resultSet.getString("locationProvince");
        String locationAddress = resultSet.getString("locationAddress");

        Instructor instructor = null;
        if (instructorId > 0) {
          instructor = getInstructor(instructorId);
        }

        Schedule schedule = new Schedule(startDate, endDate, startTime, endTime, day);
        Location location = new Location(locationName, locationCity, locationProvince, locationAddress);
        Lesson lesson = new Lesson(lessonId, discipline, instructor, schedule, location, isPrivate, isAvailable);

        lessons.add(lesson);
      }
      System.out.println("Lessons retrieved successfully.");
    } catch (SQLException e) {
      System.err.println("Error retrieving lessons: " + e.getMessage());
    }

    return lessons;
  }

  public void updateLesson(Lesson lesson) {
    String updateLessonSQL = """
            UPDATE Lessons
            SET discipline = ?,
                instructorId = ?,
                isPrivate = ?,
                isAvailable = ?,
                startDate = ?,
                endDate = ?,
                startTime = ?,
                endTime = ?,
                day = ?,
                locationName = ?,
                locationCity = ?,
                locationProvince = ?,
                locationAddress = ?
            WHERE lessonId = ?
        """;

    try {
      executeUpdate(updateLessonSQL,
          lesson.getDiscipline(),
          lesson.getInstructor(),
          lesson.isPrivate(),
          lesson.isAvailable(),
          lesson.getSchedule().getStartDate(),
          lesson.getSchedule().getEndDate(),
          lesson.getSchedule().getStartTime(),
          lesson.getSchedule().getEndTime(),
          lesson.getSchedule().getDay(),
          lesson.getLocation().getName(),
          lesson.getLocation().getCity(),
          lesson.getLocation().getProvince(),
          lesson.getLocation().getAddress(),
          lesson.getLessonId());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    System.out.println("Lesson updated successfully!");
  }

  public ArrayList<Lesson> getAvailableLessonsForInstructor() throws SQLException {
    ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    String getLessonsSQL = """
            SELECT *
            FROM Lessons
            WHERE isAvailable = true and instructorId IS NULL
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
            rs.getString("day"));

        // Create a Location object
        Location location = new Location(
            rs.getString("locationName"),
            rs.getString("locationCity"),
            rs.getString("locationProvince"),
            rs.getString("locationAddress"));

        // Create a Lesson object
        Lesson lesson = new Lesson(
            rs.getLong("lessonId"),
            rs.getString("discipline"),
            instructor,
            schedule,
            location,
            rs.getBoolean("isPrivate"),
            rs.getBoolean("isAvailable"));

        lessons.add(lesson);
      }
    }

    return lessons;
  }

  public Instructor insertInstructor(String name, String phone, LocalDate birthDate, String username, String password,
      String specialization, ArrayList<String> availableCities) {

    // Insert the new instructor into the database
    String insertSQL = """
            INSERT INTO Users (name, phone, birthDate, username, password, userType)
            VALUES (?, ?, ?, ?, ?, 'instructor')
        """;

    try {
      executeUpdate(insertSQL, name, phone, java.sql.Date.valueOf(birthDate), username, password);
      System.out.println("Instructor account created successfully!");

      // Retrieve the generated userId
      String getUserSQL = "SELECT userId FROM Users WHERE username = ?";
      try (ResultSet rs = executeQuery(getUserSQL, username)) {
        if (rs.next()) {
          long userId = rs.getLong("userId");

          // Insert the specialization into the Instructors table
          String insertSpecializationSQL = """
                  INSERT INTO Instructors (instructorId, speciality)
                  VALUES (?, ?)
              """;
          executeUpdate(insertSpecializationSQL, userId, specialization);

          // Insert the available cities into the AvailableCities table
          String insertCitiesSQL = """
                  INSERT INTO AvailableCities (instructorId, city)
                  VALUES (?, ?)
              """;
          for (String city : availableCities) {
            executeUpdate(insertCitiesSQL, userId, city);
          }

          return new Instructor(userId, name, phone, birthDate, username, password, specialization, availableCities);
        }
      }
    } catch (SQLException e) {
      System.err.println("Database error: " + e.getMessage());
      return null;
    }

    return null; // Failed to create account
  }

  public void updateInstructorOfLesson(Lesson lesson, Instructor instructor) {
    // Update the instructorId of the lesson
    String updateLessonSQL = """
            UPDATE Lessons
            SET instructorId = ?
            WHERE lessonId = ?
        """;

    try {
      executeUpdate(updateLessonSQL, instructor.getInstructorId(), lesson.getLessonId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Lesson> getAvailableToBookLessons() throws SQLException {
    ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    String getLessonsSQL = """
            SELECT *
            FROM Lessons
            WHERE isAvailable = true and instructorId IS NOT NULL
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
            rs.getString("day"));

        // Create a Location object
        Location location = new Location(
            rs.getString("locationName"),
            rs.getString("locationCity"),
            rs.getString("locationProvince"),
            rs.getString("locationAddress"));

        // Create a Lesson object
        Lesson lesson = new Lesson(
            rs.getLong("lessonId"),
            rs.getString("discipline"),
            instructor,
            schedule,
            location,
            rs.getBoolean("isPrivate"),
            rs.getBoolean("isAvailable"));

        lessons.add(lesson);
      }
    }

    return lessons;
  }

  public Booking insertNewBooking(Lesson lesson, User user) {

    if (lesson == null || user == null) {
      System.out.println("Invalid lesson or client.");
      return null;
    }

    if (!(user instanceof Client)) {
      System.out.println("Only clients can book lessons.");
      return null;
    }

    // Insert the new booking into the database
    String insertSQL = """
            INSERT INTO Bookings (lessonId, clientId)
            VALUES (?, ?)
        """;

    try {
      executeUpdate(insertSQL, lesson.getLessonId(), user.getUserId());
      System.out.println("Booking created successfully!");

      // Retrieve the generated bookingId
      String getBookingSQL = "SELECT bookingId FROM Bookings WHERE lessonId = ? AND clientId = ?";
      try (ResultSet rs = executeQuery(getBookingSQL, lesson.getLessonId(), user.getUserId())) {
        if (rs.next()) {
          long bookingId = rs.getLong("bookingId");
          
          if(lesson.isPrivate()) {
            updateLessonAvailability(lesson, false);
            lesson.setAvailable(false);
          }
          
          return new Booking(bookingId, lesson, (Client) user);
        }
      }
    } catch (SQLException e) {
      System.err.println("Database error: " + e.getMessage());
      return null;
    }

    return null; // Failed to create booking
  }

  public void updateLessonAvailability(Lesson lesson, boolean b) {
    // Update the availability of the lesson
    String updateLessonSQL = """
            UPDATE Lessons
            SET isAvailable = ?
            WHERE lessonId = ?
        """;

    try {
      executeUpdate(updateLessonSQL, b, lesson.getLessonId());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    System.out.println("Lesson availability updated successfully!");
  }

  public boolean hasLessonOverlap(Lesson lesson) throws SQLException {
    String overlapCheckSQL = """
            SELECT COUNT(*) AS overlapCount
            FROM Lessons
            WHERE day = ?
              AND locationName = ?
              AND locationCity = ?
              AND locationProvince = ?
              AND locationAddress = ?
              AND ((startTime < ? AND endTime > ?) OR (startTime < ? AND endTime > ?))
              AND ((startDate <= ? AND endDate >= ?) OR (startDate >= ? AND endDate <= ?));
        """;

    ResultSet resultSet = executeQuery(overlapCheckSQL,
        lesson.getSchedule().getDay(),
        lesson.getLocation().getName(),
        lesson.getLocation().getCity(),
        lesson.getLocation().getProvince(),
        lesson.getLocation().getAddress(),
        lesson.getSchedule().getEndTime(),
        lesson.getSchedule().getStartTime(),
        lesson.getSchedule().getStartTime(),
        lesson.getSchedule().getEndTime(),
        lesson.getSchedule().getEndDate(),
        lesson.getSchedule().getStartDate(),
        lesson.getSchedule().getStartDate(),
        lesson.getSchedule().getEndDate());

    if (resultSet.next() && resultSet.getInt("overlapCount") > 0) {
      return true;
    }

    return false;
  }

  public ArrayList<Lesson> getLessonsByInstructor(Instructor instructor) {
    ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    String getLessonsSQL = """
            SELECT *
            FROM Lessons
            WHERE instructorId = ?
        """;

    try (ResultSet rs = executeQuery(getLessonsSQL, instructor.getInstructorId())) {
      while (rs.next()) {
        // Create a Schedule object
        Schedule schedule = new Schedule(
            rs.getDate("startDate"),
            rs.getDate("endDate"),
            rs.getTime("startTime"),
            rs.getTime("endTime"),
            rs.getString("day"));

        // Create a Location object
        Location location = new Location(
            rs.getString("locationName"),
            rs.getString("locationCity"),
            rs.getString("locationProvince"),
            rs.getString("locationAddress"));

        // Create a Lesson object
        Lesson lesson = new Lesson(
            rs.getLong("lessonId"),
            rs.getString("discipline"),
            instructor,
            schedule,
            location,
            rs.getBoolean("isPrivate"),
            rs.getBoolean("isAvailable"));

        lessons.add(lesson);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return lessons;
  }

  public ArrayList<Booking> getBookingsByClient(Client client) {

    String getBookingsSQL = """
            SELECT b.bookingId, b.lessonId, l.discipline, l.instructorId, l.isPrivate, l.isAvailable,
                   l.startDate, l.endDate, l.startTime, l.endTime, l.day,
                   l.locationName, l.locationCity, l.locationProvince, l.locationAddress
            FROM Bookings b
            JOIN Lessons l ON b.lessonId = l.lessonId
            WHERE b.clientId = ?
        """; // Query to fetch bookings by client

    ArrayList<Booking> bookings = new ArrayList<Booking>();

    try (ResultSet rs = executeQuery(getBookingsSQL, client.getUserId())) {
      while (rs.next()) {
        long lessonId = rs.getLong("lessonId");
        String discipline = rs.getString("discipline");
        long instructorId = rs.getLong("instructorId");
        boolean isPrivate = rs.getBoolean("isPrivate");
        boolean isAvailable = rs.getBoolean("isAvailable");
        Date startDate = rs.getDate("startDate");
        Date endDate = rs.getDate("endDate");
        Time startTime = rs.getTime("startTime");
        Time endTime = rs.getTime("endTime");
        String day = rs.getString("day");
        String locationName = rs.getString("locationName");
        String locationCity = rs.getString("locationCity");
        String locationProvince = rs.getString("locationProvince");
        String locationAddress = rs.getString("locationAddress");

        Instructor instructor = null;
        if (instructorId > 0) {
          instructor = getInstructor(instructorId);
        }

        Schedule schedule = new Schedule(startDate, endDate, startTime, endTime, day);
        Location location = new Location(locationName, locationCity, locationProvince, locationAddress);
        Lesson lesson = new Lesson(lessonId, discipline, instructor, schedule, location, isPrivate, isAvailable);

        Booking booking = new Booking(rs.getLong("bookingId"), lesson, client);
        bookings.add(booking);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return bookings;
  }

  public void deleteBooking(Booking booking) {
    String deleteBookingSQL = """
            DELETE FROM Bookings
            WHERE bookingId = ?
        """;

    try {
      executeUpdate(deleteBookingSQL, booking.getBookingId());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    System.out.println("Booking deleted successfully!");
  }

}
