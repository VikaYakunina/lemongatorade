import Hospital.domain.Hospital;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class HospitalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Hospital> getHospitals() {
        return null;
//        return entityManager.createQuery("select b from Bank b").getResultList();
    }


}
