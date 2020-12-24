package domain;
import annotation.Column;
import annotation.Entity;

@Entity
public class Diagnoses {

    //public static long currentAccountNumber = 1000000000000000L;

    @Column
    //private long accountNumber;
    private String diseaseName;

    @Column
    //private double balance;
    private String dateDiagnoses;

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


}