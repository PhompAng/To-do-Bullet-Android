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

    private String title;
    private String descripton;
    private ArrayList<TaskList> dataset;
    private int type;

    public Task(String title, String descripton, int type) {
        this.title = title;
        this.descripton = descripton;
        this.type = type;
    }

    public Task(String title, ArrayList<TaskList> lst, int type) {
        this.title = title;
        this.dataset = lst;
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

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public ArrayList<TaskList> getDataset() {
        return dataset;
    }
}
