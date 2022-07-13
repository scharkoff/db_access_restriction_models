package app.darm_project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeUserData {

    @FXML
    private Text alertText;

    @FXML
    private Button cleanButton;

    @FXML
    private TextField newLoginField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private Button saveButton;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Quote quote = null;
    DatabaseHandler db = new DatabaseHandler();

    @FXML
    void clean(MouseEvent event) {
        newLoginField.setText(null);
        newPasswordField.setText(null);
    }

    @FXML
    void save(MouseEvent event) throws SQLException, ClassNotFoundException {
        String login = newLoginField.getText();
        String password = newPasswordField.getText();
        connection = db.getDbConnection();

        if (login.equals("") && !password.equals("")) {
            String query = "UPDATE users SET users.password=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, LoginController.user.getId());
            preparedStatement.execute();
            setAlertText("Пароль успешно изменен!", "green");
        }

        if (password.equals("") && !login.equals("")) {
            String query = "UPDATE users SET login=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, LoginController.user.getId());
            preparedStatement.execute();
            setAlertText("Логин успешно изменен!", "green");
        }

        if (!password.equals("") && !login.equals("")) {
            String query = "UPDATE users SET login=?, users.password=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, LoginController.user.getId());
            preparedStatement.execute();
            setAlertText("Данные успешно изменены!", "green");
        }

        if (password.equals("") && login.equals("")) setAlertText("Ни одно поле не заполнено!", "red");
    }

    // -- Установить уведомление
    public void setAlertText(String text, String color) {
        alertText.setStyle("-fx-fill: " + color);
        alertText.setText(text);
    }

}
