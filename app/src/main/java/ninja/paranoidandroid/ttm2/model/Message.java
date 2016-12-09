package ninja.paranoidandroid.ttm2.model;

/**
 * Created by anton on 09.12.16.
 */

public class Message {

    private String autorName;
    private String text;
    private String timeStamp;

    public Message() {
    }

    public Message(String autorName, String text, String timeStamp) {
        this.autorName = autorName;
        this.text = text;
        this.timeStamp = timeStamp;
    }

    public String getAutorName() {
        return autorName;
    }

    public void setAutorName(String autorName) {
        this.autorName = autorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
