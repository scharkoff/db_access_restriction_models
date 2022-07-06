package app.darm_project;

public class User {
    private String login;
    private String password;
    private String studyGroup;
    private String rank;


    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.studyGroup = null;
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
}
