import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Entity
public class Doctor extends Humans {

    @Id
    private Long id;

    @Column
    private String position;

    @ManyToOne
    private Hospital hospital;

    public Doctor() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
