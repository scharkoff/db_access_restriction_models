package app.darm_project;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;


public class AddQuote {

    @FXML
    private Button cleanButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField lessonField;

    @FXML
    private TextField quoteField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField secondNameField;

    @FXML
    private Text alertText;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Quote quote = null;
    DatabaseHandler db = new DatabaseHandler();
    int quoteId;
    int quoteUserId;
    private boolean update;


    // -- Очистить поля
    @FXML
    void clean(MouseEvent event) {
        quoteField.setText(null);
        lastNameField.setText(null);
        firstNameField.setText(null);
        secondNameField.setText(null);
        lessonField.setText(null);
    }

    // -- Добавить запись в таблицу
    @FXML
    void save(MouseEvent event) throws SQLException, ClassNotFoundException {
        String quote = quoteField.getText();
        String last_name = lastNameField.getText();
        String first_name = firstNameField.getText();
        String second_name = secondNameField.getText();
        String lesson = lessonField.getText();

        // -- Дополнительная проверка на ошибки с заполнениями полей
        try {
            Date date = Date.valueOf(dateField.getValue());
            if (quote.isEmpty() || last_name.isEmpty() || first_name.isEmpty() || second_name.isEmpty() || lesson.isEmpty() || date.equals("")) {
                setAlertText("Все поля должны быть заполнены!", "red");
            } else {
                getQuery();
                insert();
                setAlertText("Запись успешно добавлена!", "green");
            }
        } catch (Exception e) {
            setAlertText("Дата записана в неправильном формате!", "red");
        }

    }

    // -- Добавить запись в sql database
    private void insert() throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, LoginController.user.getId());
        preparedStatement.setString(2, quoteField.getText());
        preparedStatement.setString(3, lastNameField.getText());
        preparedStatement.setString(4, firstNameField.getText());
        preparedStatement.setString(5, secondNameField.getText());
        preparedStatement.setString(6, lessonField.getText());

        // -- Перевести дату в нужный sql формат
        java.util.Date date =
                java.util.Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        preparedStatement.setDate(7, sqlDate);
        preparedStatement.execute();
    }

    // -- Заполнение sql запроса
    private void getQuery() throws SQLException, ClassNotFoundException {
        connection = db.getDbConnection();
        query = "INSERT INTO " + Const.TEACHER_QUOTES_TABLE + " (" + Const.TEACHERS_USERID + ", " + Const.TEACHERS_QUOTE + ", " + Const.TEACHERS_LAST_NAME + ", " +
                Const.TEACHERS_FIRST_NAME + ", " + Const.TEACHERS_SECOND_NAME + ", " + Const.TEACHERS_LESSON
                + ", " + Const.TEACHERS_DATE + ") VALUES(?,?,?,?,?,?,?)";
    }

    // -- Установить уведомление
    public void setAlertText(String text, String color) {
        alertText.setStyle("-fx-fill: " + color);
        alertText.setText(text);
    }

}


