package th.in.phompang.todobullet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pichai Sivawat on 20/10/2558.
 */
public class Task implements Serializable {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_IMAGE = 2;

    private long id;
    private String title;
    private String descripton;
    private String datetime;
    private ArrayList<TaskList> dataset;
    private int type;

    public Task(long id, String title, String descripton, String datetime, int type) {
        this.id = id;
        this.title = title;
        this.descripton = descripton;
        this.datetime = datetime;
        this.type = type;
    }

    public Task(long id, String title, ArrayList<TaskList> lst, String datetime, int type) {
        this.id = id;
        this.title = title;
        this.dataset = lst;
        this.datetime = datetime;
        this.type = type;
    }

    public long getId() {
        return id;
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

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public ArrayList<TaskList> getDataset() {
        return dataset;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
