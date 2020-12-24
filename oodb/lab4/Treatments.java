import java.util.Date;

public class Treatments {

    private Diagnoses diagnoses;
    private Doctor doctors;
    private Date transactionDate;
    private PatientCard patientCrad;
    private String nameTreatments;
    private String receptionTreatments;


    public Treatments(String nameTreatments, String receptionTreatments) {
        this.nameTreatments = nameTreatments;
        this.receptionTreatments = receptionTreatments;
    }

    public Treatments() {
    }

    public void setNameTreatments(String nameTreatments) {
        this.nameTreatments = nameTreatments;
    }

    public String getNameTreatments() {
        return nameTreatments;
    }

    public void setReceptionTreatments(String receptionTreatments) {
        this.receptionTreatments = receptionTreatments;
    }

    public String getReceptionTreatments() {
        return receptionTreatments;
    }
}