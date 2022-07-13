package app.darm_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
    private Button editRowsButton;

    @FXML
    private Button addNewButton;

    @FXML
    private Button changeUserDataButton;

    @FXML
    private Text userIdText;

    @FXML
    private Text userLoginText;

    @FXML
    private Text countQuotes;

    @FXML
    private Text userStudyGroupText;

    @FXML
    private Text alertText;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Quote quote = null;
    DatabaseHandler db = new DatabaseHandler();

    public static int currentQuoteId;
    public static int currentQuoteUserId;

    int counter = 0;

    // -- Массив для хранения данных с sql database
    ObservableList<Quote> quotesList = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        // -- Обработка данных для режима гостя
        if (LoginController.user.getRank().equals("guest")) {

            setUserData("Гость", 0, "null");
            addNewButton.setDisable(true);
            deleteRowsButton.setDisable(true);
            editRowsButton.setDisable(true);
            changeUserDataButton.setDisable(true);
            countQuotes.setVisible(false);
        } else setUserData(LoginController.user.getLogin(), LoginController.user.getId(), LoginController.user.getStudyGroup());


        // -- Если пользователь выделил не свою запись, кнопки будут недоступны
        if (LoginController.user.getRank().equals("user")) {
            deleteRowsButton.setDisable(true);
            editRowsButton.setDisable(true);
            teachersQuotesTable.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if ((LoginController.user.getId() == teachersQuotesTable.getSelectionModel().getSelectedItem().user_id)) {
                        deleteRowsButton.setDisable(false);
                        editRowsButton.setDisable(false);
                    } else {
                        deleteRowsButton.setDisable(true);
                        editRowsButton.setDisable(true);
                    }
                }
            });
        }

        loadDate(); // -- загрузить актуальные данные в таблицу

        // -- Обработка событий при нажатии на кнопку "Обновить"
        refreshButton.setOnAction(actionEvent -> {
            try {
                userLoginText.setText(LoginController.user.getLogin());
                refreshTable();
                setAlertText("Все данные обновлены!", "yellow");
                countQuotes.setText(String.valueOf(countQuotes()));
                CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                    setAlertText("", "");
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        // -- Обработка событий при нажатии на кнопку "Выйти"
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();

            Stage stage = new Stage();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("login.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
            stage.setTitle("Авторизация");
            stage.show();
        });
    }

    // -- Обновить данные в таблице
    private void refreshTable() throws SQLException {
        quotesList.clear();

        if (LoginController.user.getRank().equals("guest")) query = "SELECT * FROM teacher_quotes";
        if (LoginController.user.getRank().equals("admin")) query = "SELECT * FROM teacher_quotes";
        if (LoginController.user.getRank().equals("user") || LoginController.user.getRank().equals("headman")) query =
                "SELECT teacher_quotes.id, teacher_quotes.user_id, teacher_quotes.quote, teacher_quotes.last_name, teacher_quotes.first_name, " +
                "teacher_quotes.second_name, teacher_quotes.lesson, teacher_quotes.publication_date " +
                "FROM users " +
                "JOIN teacher_quotes ON (users.id = teacher_quotes.user_id) " +
                "WHERE (users.study_group =?)";

        preparedStatement = connection.prepareStatement(query);
        if (LoginController.user.getRank().equals("user") || LoginController.user.getRank().equals("headman")) preparedStatement.setString(1, LoginController.user.getStudyGroup());

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

    // -- Загрузить данные с базы данных в приложение
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

        // -- Установить количество цитат пользователя
        countQuotes.setText(String.valueOf(countQuotes()));
    }

    @FXML // -- Открыть окно с добавлением цитаты на кнопку "Добавить"
    public void getAddView(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("addquote.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Добавить цитату");
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML // -- Удалить выделенный элемент
    public void deleteRowsFromTable(MouseEvent event) throws SQLException, ClassNotFoundException {
        connection = db.getDbConnection();

        int id = teachersQuotesTable.getSelectionModel().getSelectedItem().id;
        query = "DELETE FROM " + Const.TEACHER_QUOTES_TABLE + " WHERE " + Const.TEACHERS_ID + "=" + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
        refreshTable();
        setAlertText("Запись успешно удалена!", "#33ff00");
        countQuotes.setText(String.valueOf(countQuotes()));
    }

    @FXML // -- Изменить выделенный элемент
    void editCurrentRow(MouseEvent event) throws SQLException, ClassNotFoundException, IOException {
        currentQuoteId = teachersQuotesTable.getSelectionModel().getSelectedItem().id;
        currentQuoteUserId = teachersQuotesTable.getSelectionModel().getSelectedItem().user_id;
        Parent parent = FXMLLoader.load(getClass().getResource("editquote.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Изменить цитату");
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML // -- Открыть окно изменения данных пользователя
    void changeUserData(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("changeuserdata.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Изменить регистрационные данные");
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    // -- Подстановка личных данных пользователя
    public void setUserData(String login, int id, String group) {
        userLoginText.setText(login);
        userIdText.setText(String.valueOf(id));
        userStudyGroupText.setText(group);
    }

    // -- Установить уведомление
    public void setAlertText(String text, String color) {
        alertText.setStyle("-fx-fill: " + color);
        alertText.setText(text);
    }

    // -- Посчитать количество цитат
    public int countQuotes() {
        counter = 0;
       for (int i = 0; i < teachersQuotesTable.getItems().size(); i++) {
           if (LoginController.user.getId() == teachersQuotesTable.getItems().get(i).user_id) {
               counter++;
           }
       }
       return counter;
   }



}

