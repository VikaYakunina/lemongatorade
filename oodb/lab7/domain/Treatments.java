package domain;

import annotation.*;

@Entity
public class Treatments {

    @Id
    private Long id;

    @ManyToOne
    @Column
    private Diagnoses diagnoses;

    @OneToOne
    @Column
    private Doctor doctor;

    @Column
    private String nameTreatments;

    @Column
    private String receptionTreatments;

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