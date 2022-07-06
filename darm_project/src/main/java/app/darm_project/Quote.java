package app.darm_project;


import java.sql.Date;

public class Quote {

    int id;
    int user_id;
    String quote;
    String last_name;
    String first_name;
    String second_name;
    String lesson;
    String publication_date;

    public Quote(int id, int user_id, String quote, String last_name, String first_name, String second_name, String lesson, String publication_date) {
        this.id = id;
        this.user_id = user_id;
        this.quote = quote;
        this.last_name = last_name;
        this.first_name = first_name;
        this.second_name = second_name;
        this.lesson = lesson;
        this.publication_date = publication_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }
}
