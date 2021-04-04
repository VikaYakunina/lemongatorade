package domain;

import javax.persistence.*;


@Entity
public class Diagnoses {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Diagnoses")
    @SequenceGenerator(name = "Diagnoses", sequenceName = "diagnosed_seq", allocationSize = 1)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDateDiagnoses() {
        return dateDiagnoses;
    }

    public void setDateDiagnoses(String dateDiagnoses) {
        this.dateDiagnoses = dateDiagnoses;
    }

    public PatientCard getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(PatientCard patientCard) {
        this.patientCard = patientCard;
    }
}
