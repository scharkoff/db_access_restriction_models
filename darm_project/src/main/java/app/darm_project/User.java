package app.darm_project;

public class User {
    private int id;
    private String login;
    private String password;
    private String studyGroup;
    private String rank;


    public User(String login, String password, String studyGroup) {
        this.login = login;
        this.password = password;
        this.studyGroup = studyGroup;
        this.rank = null;
    }

    public User() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(String studyGroup) {
        this.studyGroup = studyGroup;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
