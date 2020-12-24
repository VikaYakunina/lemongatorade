public class Diagnoses {
    //public static long currentAccountNumber = 1000000000000000L;

    private String diseaseName;
    private String dateDiagnoses;

    public Diagnoses(String diseaseName) {
        this.diseaseName = diseaseName;
        this.dateDiagnoses = dateDiagnoses;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getDateDiagnoses() {
        return dateDiagnoses;
    }

    public void setDateDiagnoses(String dateDiagnoses) {
        this.dateDiagnoses = dateDiagnoses;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
}
