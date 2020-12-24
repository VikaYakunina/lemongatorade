package domain;
import annotation.Column;
import annotation.Entity;
import  annotation.OneToMany;
import java.util.List;

@Entity
public class Hospital {

    @Column
    private String hospitalName;

    @Column
    private String hospitalAddress;

    @Column
    @OneToMany
    public static List<Doctor> doctors;

    @Column
    @OneToMany
    public static List<PatientCard> patientCards;

    @Column
    @OneToMany
    public static List<Treatments> treatments;

    public String gethospitalName() {
        return hospitalName;
    }

    public void sethospitalName(String name) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}