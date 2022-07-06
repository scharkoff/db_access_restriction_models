package app.darm_project;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, user, password);

        return dbConnection;
    }

    // -- Write user to database
    public void signUpUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USERS_LOGIN + "," + Const.USERS_PASSWORD + ")" +
                "VALUES(?,?)";

        PreparedStatement prepState = getDbConnection().prepareStatement(insert);
        prepState.setString(1, user.getLogin());
        prepState.setString(2, user.getPassword());

        prepState.executeUpdate();

    }

    // -- Read from database
    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USERS_LOGIN + "=? AND " + Const.USERS_PASSWORD + "=?";

        PreparedStatement prepState = getDbConnection().prepareStatement(select);
        prepState.setString(1, user.getLogin());
        prepState.setString(2, user.getPassword());

        resSet = prepState.executeQuery();

        return resSet;
    }
}
