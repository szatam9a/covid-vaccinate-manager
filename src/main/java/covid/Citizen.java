package covid;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Citizen {
    private String name;
    private String zipCode;
    private int age;
    private String emailAddress;
    private int TAJ;
    @Id
    private long ID;

    public Citizen(String name, String zipCode, int age, String emailAddress, int TAJ) {
        this.name = name;
        this.zipCode = zipCode;
        this.age = age;
        this.emailAddress = emailAddress;
        this.TAJ = TAJ;
    }

    public Citizen(long ID, String name, String zipCode, int age, String emailAddress, int TAJ) {
        this.name = name;
        this.zipCode = zipCode;
        this.age = age;
        this.emailAddress = emailAddress;
        this.TAJ = TAJ;
        this.ID = ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getAge() {
        return age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getTAJ() {
        return TAJ;
    }

    public long getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "name='" + name + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", age=" + age +
                ", emailAddress='" + emailAddress + '\'' +
                ", TAJ=" + TAJ +
                ", ID=" + ID +
                '}';
    }
}
