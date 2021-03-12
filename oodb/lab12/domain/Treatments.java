import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class Treatments {

    @Id
    private Long id;

    @ManyToOne
    private Diagnoses diagnoses;

    @OneToOne
    private Doctor doctor;

    @Column
    private String nameTreatments;

    @Column
    private String receptionTreatments;

    public Treatments() {
    }

    public Treatments(Diagnoses diagnoses, Doctor doctor, double amount, String nameTreatments, String receptionTreatments) {
        this.diagnoses = diagnoses;
        this.doctor = doctor;
        this.nameTreatments = nameTreatments;
        this.receptionTreatments = receptionTreatments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
