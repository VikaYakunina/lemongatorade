package domain;
import annotation.Column;
import annotation.Entity;
import annotation.OneToMany;

import java.util.List;

@Entity
public class PatientCard extends Human {

    @Column
    //@ManyToOne
    @OneToMany
    private List<Diagnoses> diagnoses;

    public PatientCard(String firstName, String lastName, String phoneNumber, String email, String medicalpolicy) {
        super(firstName, lastName, phoneNumber, email, medicalpolicy);
    }

}