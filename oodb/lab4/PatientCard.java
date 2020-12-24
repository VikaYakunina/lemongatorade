import java.util.List;

public class PatientCard extends Human{
    private List<Diagnoses> diagnoses;

    public PatientCard(String FullName, String Age, String PhoneNumber, String MedicalPolicy, String Address) {
        super(FullName, Age, PhoneNumber, MedicalPolicy, Address);
    }

    private Diagnoses findDiagnoses(String diseaseName) {
        for (Diagnoses diagnoses : diagnoses) {
            if (diagnoses.getDiseaseName() == diseaseName) {
                return diagnoses;
            }
        }
        return null;
    }
}
