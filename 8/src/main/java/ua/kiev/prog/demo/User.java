package ua.kiev.prog.demo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table (name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String lastName;
    private String age;
    private String season;
    private String gadget;


    public User(String name, String lastName, String age, String season, String gadget) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.season = season;
        this.gadget = gadget;
    }
    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getSeason() {
        return season;
    }

    public String getGadget() {
        return gadget;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setGadget(String gadget) {
        this.gadget = gadget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(age, user.age) && Objects.equals(season, user.season) && Objects.equals(gadget, user.gadget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, age, season, gadget);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", season='" + season + '\'' +
                ", gadget='" + gadget + '\'' +
                '}';
    }
}
