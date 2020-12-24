import java.util.List;

public class Hospital {

    private String hospitalName;
    private String hospitalAddress;

    public static List<Doctor> doctors;
    private List<Diagnoses> diagnoses;
    private List<PatientCard> patientCards;
    private List<Treatments> treatments;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAdress() {
        return hospitalAddress;
    }

    public void setHospitalAdress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }
}