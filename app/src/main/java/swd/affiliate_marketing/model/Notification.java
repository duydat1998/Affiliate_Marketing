package swd.affiliate_marketing.model;

import java.io.Serializable;

public class Notification implements Serializable {
    private String time;
    private String title;
    private String content;

    public Notification() {
    }

    public Notification(String time, String title, String content) {
        this.setTime(time);
        this.setTitle(title);
        this.setContent(content);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
