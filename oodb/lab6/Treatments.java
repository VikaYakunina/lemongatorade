package domain;
import annotation.Column;
import annotation.Entity;


@Entity
public class Treatments {

    @Column
    private Diagnoses diagnoses;

    @Column
    private Doctor doctors;

    @Column
    private PatientCard patientCard;

   @Column
   private String nameTreatments;

   @Column
   private String receptionTreatments;


    public Treatments(Diagnoses diagnoses, Doctor doctors, PatientCard patientCard, double amount,String nameTreatments, String receptionTreatments ) {

        this.diagnoses = diagnoses;
        this.doctors = doctors;
        this.patientCard = patientCard;
        this.nameTreatments = nameTreatments;
        this.receptionTreatments = receptionTreatments;
    }
}