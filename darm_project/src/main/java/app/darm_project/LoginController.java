package app.darm_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
    private Text alertMessage;

    public static User user = new User();

    @FXML
    void initialize() {

        // -- Обработка событий для кнопки "Войти гостем"
        guestButton.setOnAction(actionEvent -> {
            try {
                user.setRank("guest");
                goToScene("home", "Главная страница (уровень доступа: Гость)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // -- Обработка событий для кнопки "Войти"
        loginButton.setOnAction(actionEvent -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passwordField.getText().trim();

            // -- Проверка на пустые строки
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
                setAlertMessage("Нельзя оставлять поля пустыми!");
            }
        });


        // -- Обработка событий для кнопки "Зарегистрироваться"
        signUpButton.setOnAction(actionEvent -> {
            try {
                goToScene("register", "Регистрация");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // -- Авторизация пользователя
    private void loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException, IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);


        if (result.next()) {
            do {
                this.user = user;
                user.setId(result.getInt(1));
                user.setStudyGroup(result.getString(4));
                user.setRank(result.getString(5));

                if (user.getRank().equals("admin")) {
                    goToScene("home", "Главная страница (уровень доступа: Администратор)");
                } else if (user.getRank().equals("user")) {
                    goToScene("home", "Главная страница (уровень доступа: Обычный пользователь)");
                } else if (user.getRank().equals("headman")) {
                    goToScene("home", "Главная страница (уровень доступа: Староста)");
                }
            } while(result.next());
        } else {
            setAlertMessage("Пользователь не найден!");
        }
    }

    // -- Перейти на другое окно
    public void goToScene(String fileName, String sceneName) throws IOException {
        signUpButton.getScene().getWindow().hide();
        Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource(fileName + ".fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle(sceneName);
        stage.show();
    }

    // -- Установить уведомление
    public void setAlertMessage(String text) {
        alertMessage.setText(text);
    }



}
