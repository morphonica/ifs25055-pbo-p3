package domain.entity;

public class Activity {
    private final int id;
    private String title;
    private String day;
    private String time;

    public Activity(int id, String title, String day, String time) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.time = time;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDay() { return day; }
    public String getTime() { return time; }

    public void setTitle(String title) { this.title = title; }
    public void setDay(String day) { this.day = day; }
    public void setTime(String time) { this.time = time; }
}
