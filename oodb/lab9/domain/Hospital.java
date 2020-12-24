package domain;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;


@Entity
public class Hospital {

    @Id
    private Long id;

    @Column
    private String hospitalName;

    @Column
    private String hospitalAddress;

    public Hospital() {
    }

    public Hospital(String hospitalName, String hospitalAddress) {
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
