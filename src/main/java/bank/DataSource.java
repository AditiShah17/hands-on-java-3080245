package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

  // static method connect which will return the Connection
  public static Connection connect() {
    // path of database
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We're connected");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return connection;

  }

  public static void main(String[] args) {
    connect();
  }

}
