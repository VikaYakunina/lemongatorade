import javax.persistence.*;

@Entity
public class Humans {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Humans")
    @SequenceGenerator(name = "Humans", sequenceName = "humans_seq", allocationSize = 1)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String medicalpolicy;

    public Humans() {}

    Humans(String firstName, String lastName, String phoneNumber, String email, String medicalpolicy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.medicalpolicy= medicalpolicy;
    }

    @Override
    public String toString() {
        return "pkg.Human{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", medicalpolicy='" + medicalpolicy + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMedicalpolicy() {
        return medicalpolicy;
    }

    public void setMedicalpolicy(String medicalpolicy) {
        this.medicalpolicy = medicalpolicy;
    }
}
