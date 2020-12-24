package domain;
import annotation.Column;
import annotation.Entity;

@Entity
public class Doctor extends Human {

    @Column
    private Long id;

    @Column
    private String position;


    public Doctor(String firstName, String lastName, String phoneNumber, String email, String position, String medicalpolicy) {
        super(firstName, lastName, phoneNumber, email , medicalpolicy);
        this.position = position;;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}