package ninja.paranoidandroid.ttm2.model;

/**
 * Created by anton on 08.12.16.
 */

public class User {

    private String name;
    private String email;
    private String profession;

    public User() {
    }

    public User(String name, String email, String profession) {
        this.name = name;
        this.email = email;
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfession() {
        return profession;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
