package app.darm_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationController {

    @FXML
    private Button goToLoginButton;

    @FXML
    private TextField loginFieldInLogin;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button regButton;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private TextField studyGroupField;

    @FXML
    void initialize() {

        // -- Sign up button
        regButton.setOnAction(actionEvent -> {
            signUpNewUser();

        });

        // -- Button go to log in stage
        goToLoginButton.setOnAction(actionEvent -> {
            goToLoginButton.getScene().getWindow().hide(); // скрыть текущую сцену

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                System.out.println("Что-то пошло не так!");
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Авторизация");
            stage.showAndWait(); // ожидание загрузки сцены
        });
    }

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String login = loginFieldInLogin.getText();
        String password = passwordField.getText();
        String studyGroup = studyGroupField.getText();
        String repeatPassword = repeatPasswordField.getText();

        // -- Empty fields check
        if (!login.equals("") && !password.equals("") && !studyGroup.equals("") && !repeatPassword.equals("")) {
            if (password.equals(repeatPassword)) {
                User user = new User(login, password);
                try {
                    dbHandler.signUpUser(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Пароли не совпадают!");
            }
        } else {
            System.out.println("Нельзя оставлять поля пустыми!");
        }


    }

}
