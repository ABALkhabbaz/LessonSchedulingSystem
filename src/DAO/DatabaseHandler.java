/*
 * This is the entry point for db connectiona and running sql queries
 */
 


package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

  // testing db connection
  public static void main(String args[]){

    DatabaseHandler dbHandler = new DatabaseHandler();
    
    try {
      dbHandler.connect();
    } catch(SQLException e) {
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

}
