package domain;


import annotation.Column;
import annotation.Entity;
import annotation.Id;
import annotation.OneToMany;
import annotation.ManyToOne;

import java.util.List;

@Entity
class Diagnoses {

    @Id
    private Long id;

    @Column
    private String diseaseName;

    @Column
    private String dateDiagnoses;

    @Column
    @ManyToOne
    private PatientCard patientCard;

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
