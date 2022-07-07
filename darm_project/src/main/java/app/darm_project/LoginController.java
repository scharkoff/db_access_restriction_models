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
    private Button guestButton;

    @FXML
    void initialize() {

        // -- Guest button
        guestButton.setOnAction(actionEvent -> {
            try {
                goToScene("home", "Главная страница (доступ уровня: Гость)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Нельзя оставлять поля пустыми!");
            }
        });


        // -- Button go to the register stage
        signUpButton.setOnAction(actionEvent -> {
            try {
                goToScene("register", "Регистрация");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException, IOException {
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

    public void goToScene(String fileName, String sceneName) throws IOException {
        signUpButton.getScene().getWindow().hide(); // скрыть текущую сцену
        Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource(fileName + ".fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle(sceneName);
        stage.show(); // ожидание загрузки сцены

    }

}
