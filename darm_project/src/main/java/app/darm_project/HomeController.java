package app.darm_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.sql.*;

public class HomeController {

    @FXML
    private TableColumn<Quote, String> dateColumn;

    @FXML
    private TableColumn<Quote, String> firstNameColumn;

    @FXML
    private TableColumn<Quote, String> idColumn;

    @FXML
    private TableColumn<Quote, String> userIdColumn;

    @FXML
    private TableColumn<Quote, String> lastNameColumn;

    @FXML
    private TableColumn<Quote, String> lessonColumn;

    @FXML
    private TableColumn<Quote, String> quoteColumn;

    @FXML
    private TableColumn<Quote, String> secondNameColumn;

    @FXML
    private TableView<Quote> teachersQuotesTable;

    @FXML
    private Button refreshButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button deleteRowsButton;

    @FXML
    private Button addNewButton;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Quote quote = null;
    DatabaseHandler db = new DatabaseHandler();

    ObservableList<Quote> quotesList = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        loadDate();

        refreshButton.setOnAction(actionEvent -> {
            try {
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide(); // скрыть текущую сцену

            Stage stage = new Stage();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("login.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
            stage.setTitle("Авторизация");
            stage.show(); // ожидание загрузки сцены
        });
    }

    private void refreshTable() throws SQLException {
        quotesList.clear();

        query = "SELECT * FROM " + Const.TEACHER_QUOTES_TABLE;
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            quotesList.add(new Quote(
                    resultSet.getInt(Const.TEACHERS_ID),
                    resultSet.getInt(Const.TEACHERS_USERID),
                    resultSet.getString(Const.TEACHERS_QUOTE),
                    resultSet.getString(Const.TEACHERS_LAST_NAME),
                    resultSet.getString(Const.TEACHERS_FIRST_NAME),
                    resultSet.getString(Const.TEACHERS_SECOND_NAME),
                    resultSet.getString(Const.TEACHERS_LESSON),
                    resultSet.getDate(Const.TEACHERS_DATE)
                    )
            );


            teachersQuotesTable.setItems(quotesList);
        }

    }

    private void loadDate() throws SQLException, ClassNotFoundException {

        connection = db.getDbConnection();
        refreshTable();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        quoteColumn.setCellValueFactory(new PropertyValueFactory<>("quote"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        lessonColumn.setCellValueFactory(new PropertyValueFactory<>("lesson"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("publication_date"));


    }


    @FXML
    public void getAddView(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("addquote.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Добавить цитату");
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    public void deleteRowsFromTable(MouseEvent event) throws SQLException, ClassNotFoundException {
        connection = db.getDbConnection();

        int id = teachersQuotesTable.getSelectionModel().getSelectedItem().id;
        query = "DELETE FROM " + Const.TEACHER_QUOTES_TABLE + " WHERE " + Const.TEACHERS_ID + "=" + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

    }


}

