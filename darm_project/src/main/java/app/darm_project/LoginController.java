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
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;
    @FXML
    void initialize() {

        // -- Button log in
        loginButton.setOnAction(actionEvent -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passwordField.getText().trim();

            // -- Empty fields check
            if (!loginText.equals("") && !loginPassword.equals("")) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Нельзя оставлять поля пустыми!");
            }
        });


        // -- Button go to the register stage
        signUpButton.setOnAction(actionEvent -> {
            goToScene("register", "Регистрация");
        });
    }

    private void loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;

        while(result.next()) {
            counter++;
        }

        if (counter >= 1) {
            System.out.println("Пользователь найден! Авторизация прошла успешно!");
            goToScene("home", "Главная страница");
        } else {
            System.out.println("Пользователь не найден!");
        }
    }

    public void goToScene(String fileName, String sceneName) {
        signUpButton.getScene().getWindow().hide(); // скрыть текущую сцену

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fileName + ".fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            System.out.println("Что-то пошло не так!");
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(sceneName);
        stage.showAndWait(); // ожидание загрузки сцены
    }

}
