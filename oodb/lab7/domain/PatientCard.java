package domain;


import annotation.*;

import java.util.List;

@Entity
public class PatientCard extends Humans {

    @Id
    private Long id;

    @ManyToOne
    @Column
    private Hospital hospital;

    public PatientCard(String firstName, String lastName, String phoneNumber, String email, String medicalpolicy) {
        super(firstName, lastName, phoneNumber, email, medicalpolicy);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
