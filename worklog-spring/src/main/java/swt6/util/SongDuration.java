package swt6.util;

import java.io.Serializable;

public class SongDuration implements Serializable {

    private int minutes;
    private int seconds;

    public SongDuration() {
    }

    public SongDuration(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "SongDuration{" +
                "minutes=" + minutes +
                ", seconds=" + seconds +
                '}';
    }
}
