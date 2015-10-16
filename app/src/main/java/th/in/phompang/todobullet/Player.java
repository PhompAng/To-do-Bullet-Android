package th.in.phompang.todobullet;

/**
 * Created by Pichai Sivawat on 17/10/2558.
 */
public class Player {
    private String name;
    private String club;

    public Player(String name, String club) {
        this.name = name;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public String getClub() {
        return club;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClub(String club) {
        this.club = club;
    }
}
