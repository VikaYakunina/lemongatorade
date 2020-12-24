package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;


@Entity
class Diagnoses {

    @Id
    private Long id;

    @Column
    private String diseaseName;

    @Column
    private String dateDiagnoses;

    @ManyToOne
    private PatientCard patientCard;

    public Diagnoses() {
    }

    Diagnoses(String diseaseName, String dateDiagnoses ) {
        this.diseaseName = diseaseName;

        this.dateDiagnoses = dateDiagnoses;
    }

    String getdiseaseName() {
        return diseaseName;
    }

    String getdateDiagnoses() {
        return dateDiagnoses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
