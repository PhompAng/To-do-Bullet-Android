package th.in.phompang.todobullet;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pichai Sivawat on 20/10/2558.
 */
public class TaskList implements Parcelable {
    private String name;

    public TaskList(String name) {
        this.name = name;
    }

    public TaskList(Parcel in) {
        this.name = in.readString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static final Parcelable.Creator<TaskList> CREATOR
            = new Parcelable.Creator<TaskList>()
    {
        public TaskList createFromParcel(Parcel in)
        {
            return new TaskList(in);
        }

        public TaskList[] newArray (int size)
        {
            return new TaskList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
