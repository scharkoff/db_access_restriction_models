package app.darm_project;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Quote quote = null;
    DatabaseHandler db = new DatabaseHandler();
    int quoteId;
    int quoteUserId;
    private boolean update;

    @FXML
    void clean(MouseEvent event) {
        quoteField.setText(null);
        lastNameField.setText(null);
        firstNameField.setText(null);
        secondNameField.setText(null);
        lessonField.setText(null);
    }

    @FXML
    void save(MouseEvent event) throws SQLException, ClassNotFoundException {
        String quote = quoteField.getText();
        String last_name = lastNameField.getText();
        String first_name = firstNameField.getText();
        String second_name = secondNameField.getText();
        String lesson = lessonField.getText();
        Date date = Date.valueOf(dateField.getValue());

        if (quote.isEmpty() || last_name.isEmpty() || first_name.isEmpty() || second_name.isEmpty() || lesson.isEmpty() || date.equals("")) {
            System.out.println("Все поля должны быть заполнены!");
        } else {
            getQuery();
            insert();
            System.out.println("Запись добавлена!");
        }

    }

    private void insert() throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 7);
        preparedStatement.setString(2, quoteField.getText());
        preparedStatement.setString(3, lastNameField.getText());
        preparedStatement.setString(4, firstNameField.getText());
        preparedStatement.setString(5, secondNameField.getText());
        preparedStatement.setString(6, lessonField.getText());

        java.util.Date date =
                java.util.Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        preparedStatement.setDate(7, sqlDate);
        preparedStatement.execute();
    }

    private void getQuery() throws SQLException, ClassNotFoundException {
        connection = db.getDbConnection();
        query = "INSERT INTO " + Const.TEACHER_QUOTES_TABLE + " (" + Const.TEACHERS_USERID + ", " + Const.TEACHERS_QUOTE + ", " + Const.TEACHERS_LAST_NAME + ", " +
                Const.TEACHERS_FIRST_NAME + ", " + Const.TEACHERS_SECOND_NAME + ", "  + Const.TEACHERS_LESSON
                + ", " + Const.TEACHERS_DATE + ") VALUES(?,?,?,?,?,?,?)";
    }

    void setTextField(int id, int user_id, String quote, String last_name, String first_name, String second_name, String lesson, LocalDate publication_date) {
        quoteId = id;
        quoteUserId = user_id;
        quoteField.setText(quote);
        lastNameField.setText(last_name);
        firstNameField.setText(first_name);
        secondNameField.setText(second_name);
        lessonField.setText(lesson);
        dateField.setValue(publication_date);


    }

    void setUpdate(boolean b) {
        this.update = b;

    }

}
