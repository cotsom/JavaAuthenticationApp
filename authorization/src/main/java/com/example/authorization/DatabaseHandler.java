package com.example.authorization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpUser(User user) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_LOGIN + "," + Const.USER_PASS + ")" + "VALUES(?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(query);
        prSt.setString(1, user.getLogin());
        prSt.setString(2, user.getPassword());

        prSt.executeUpdate();
    }

    public ResultSet signInUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String query = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=? AND " + Const.USER_PASS + "=?";

        PreparedStatement prSt = getDbConnection().prepareStatement(query);
        prSt.setString(1, user.getLogin());
        prSt.setString(2, user.getPassword());

        resSet = prSt.executeQuery();

        return resSet;
    }

    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String query = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=?";

        PreparedStatement prSt = getDbConnection().prepareStatement(query);
        prSt.setString(1, user.getLogin());

        resSet = prSt.executeQuery();

        return resSet;
    }
}
