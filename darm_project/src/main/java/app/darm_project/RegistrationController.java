package app.darm_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
    private Text alertMessage;

    @FXML
    void initialize() {

        // -- Обработка событий для кнопки "Зарегистрироваться"
        regButton.setOnAction(actionEvent -> {
            signUpNewUser();
        });

        // -- Обработка событий для кнопки "Войти"
        goToLoginButton.setOnAction(actionEvent -> {
            goToLoginButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                System.out.println("Что-то пошло не так!");
                setAlertMessage("Что-то пошло не так!", "red");
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Авторизация");
            stage.showAndWait();
        });
    }

    // -- Регистрация пользователя
    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        // -- Получение введенных данных с полей
        String login = loginFieldInLogin.getText();
        String password = passwordField.getText();
        String studyGroup = studyGroupField.getText();
        String repeatPassword = repeatPasswordField.getText();

        // -- Проверка на пустые строки
        if (!login.equals("") && !password.equals("") && !studyGroup.equals("") && !repeatPassword.equals("")) {
            if (password.equals(repeatPassword)) {
                User user = new User(login, password, studyGroup);
                setAlertMessage("Вы успешно зарегистрировались!", "#33ff00");
                try {
                    dbHandler.signUpUser(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                    setAlertMessage("Данный логин уже используется!", "red");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    setAlertMessage("Что-то пошло не так!", "red");
                }
            } else {
                setAlertMessage("Пароли не совпадают!", "red");
            }
        } else {
           setAlertMessage("Нельзя оставлять поля пустыми!", "red");
        }


    }

    // -- Установить уведомление
    public void setAlertMessage(String text, String color) {
        alertMessage.setStyle("-fx-fill: " + color);
        alertMessage.setText(text);
    }

}
