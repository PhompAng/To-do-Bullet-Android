package th.in.phompang.todobullet;

/**
 * Created by Pichai Sivawat on 20/10/2558.
 */
public class Task {
    private String title;
    private int type;

    public Task(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
