package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
  private static final String URL = "jdbc:mysql://localhost:3306/your_database";
  private static final String USER = "your_username";
  private static final String PASSWORD = "your_password";

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

  // Execute an INSERT, UPDATE, or DELETE statement
  public int executeUpdate(String sql, Object... params) throws SQLException {
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      setParameters(stmt, params);
      return stmt.executeUpdate();
    }
  }

  // Execute a SELECT query and return the result set
  public ResultSet executeQuery(String sql, Object... params) throws SQLException {
    PreparedStatement stmt = connection.prepareStatement(sql);
    setParameters(stmt, params);
    return stmt.executeQuery();
  }

  // Utility method to set parameters in the prepared statement
  private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
    for (int i = 0; i < params.length; i++) {
      stmt.setObject(i + 1, params[i]);
    }
  }
}
