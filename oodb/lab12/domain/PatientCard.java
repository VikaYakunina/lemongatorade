import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PatientCard extends Humans {

    //@Id
   //private Long id;

    @ManyToOne
    private Hospital hospital;

    public PatientCard(){}

    public PatientCard(String firstName, String lastName, String phoneNumber, String email, String medicalpolicy) {
        super(firstName, lastName, phoneNumber, email, medicalpolicy);
    }

    //public Long getId() {
    //    return id;
    //}

    //public void setId(Long id) {
     //   this.id = id;
    //}

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
