package domain;

import annotation.Column;
import annotation.Entity;
import annotation.Id;
import annotation.OneToMany;

import java.util.List;

@Entity
public class Hospital {

    @Id
    private Long id;

    @Column
    private String hospitalName;

    @Column
    private String hospitalAddress;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}