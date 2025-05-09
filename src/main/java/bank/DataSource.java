package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.text.html.HTMLDocument.RunElement;

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

  // GetCustomer and username will accept
  public static Customer getCustomer(String username) {
    // get the customer
    String sql = "select * from customers where username = ?";
    Customer customer = null;
    // if we have query we have prepared statement to use
    // prepared statement is autoclosable so we only write in try block
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));

      }

    } catch (SQLException e) {
      e.printStackTrace(); // TODO: handle exception
    }

    return customer;

  }

  public static Account getAccount(int accountId) {
    String sqlAccount = "select * from accounts where id = ?";
    Account account = null;
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sqlAccount)) {
      statement.setInt(1, accountId);
      try (ResultSet result = statement.executeQuery()) {
        account = new Account(
            result.getInt("id"),
            result.getString("type"),
            result.getDouble("balance"));
      }

    } catch (Exception e) {
      e.printStackTrace(); // TODO: handle exception
    }
    return account;
  }

  public static void updateAccountBalance(int accountId, double balance){
    String sql = "updaate accounts set balance = ? where id = ?";
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      
          statement.setDouble(1, balance);
          statement.setInt(2, accountId);
          statement.executeUpdate();

      } catch( SQLException e){
          e.printStackTrace();
      }
  }


 

}
