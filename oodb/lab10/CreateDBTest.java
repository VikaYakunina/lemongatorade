import domain.Diagnoses;
import domain.Hospital;
import domain.PatientCard;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.tools.Diagnostic;
import java.util.zip.DeflaterInputStream;

public class CreateDBTest {

    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("lab10");

        EntityManager entityManager =  emf.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Hospital hospital = new Hospital();
        int h = 1;
        Long hp = new Long(h);

        hospital.setId(hp);
        hospital.setHospitalAddress("Kremlevskaya, 24");
        hospital.setHospitalName("7");
        //entityManager.persist(hospital);

        //---------human1---------------
        PatientCard patientCard = new PatientCard();
        patientCard.setFirstName("Alexander");
        patientCard.setLastName("Pushkin");
        patientCard.setEmail("pushkin@mail.ru");
        patientCard.setMedicalpolicy("33736385");
        patientCard.setPhoneNumber("+73643375");
        patientCard.setHospital(hospital);
        //entityManager.persist(patientCard);

        Diagnoses diagnoses = new Diagnoses();
        diagnoses.setDiseaseName("flu");
        diagnoses.setDateDiagnoses("10.12.2020");
        diagnoses.setPatientCard(patientCard);
        //entityManager.persist(diagnoses);

        //---------human2---------------

        PatientCard patientCard2 = new PatientCard();
        patientCard2.setFirstName("Michail");
        patientCard2.setLastName("Lermontov");
        patientCard2.setEmail("Lermontov@mail.ru");
        patientCard2.setMedicalpolicy("33736385");
        patientCard2.setPhoneNumber("+73643375");
        patientCard2.setHospital(hospital);
        //entityManager.persist(patientCard2);

        Diagnoses diagnoses2 = new Diagnoses();
        diagnoses2.setDiseaseName("грипп");
        diagnoses2.setDateDiagnoses("11.11.2020");
        diagnoses2.setPatientCard(patientCard2);
        //entityManager.persist(diagnoses2);

        //---------------------- Изменение --------------------------------------
        Hospital hospital1 = new Hospital();
        int h1 = 1;
        Long hp1 = new Long(h1);

        hospital1.setId(hp1);
        hospital1.setHospitalAddress("Dekabristov, 24");
        hospital1.setHospitalName("RCB");
        //entityManager.merge(hospital1);

        // ---- Удаление диагноза
        int d1 = 1;
        Long dg1 = new Long(d1);
        Diagnoses diagnoses_del = entityManager.find(Diagnoses.class,dg1);
        entityManager.remove(diagnoses_del);

        transaction.commit();
        entityManager.close();
        emf.close();
    }
}