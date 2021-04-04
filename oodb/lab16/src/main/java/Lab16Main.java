import domain.Diagnoses;
import domain.Humans;

import javax.persistence.EntityManager;

public class Lab16Main {

    public static void main(String[] args) {
        AnotherRun anotherRun = new AnotherRun(1);
        anotherRun.run();

        AnotherRun anotherRun2 = new AnotherRun(2);
        anotherRun2.run();

        /*DbWork db = DbWork.getInstance();
        Humans humans = new Humans();
        humans.setLastName("Иванов");
        humans.setFirstName("Иван");
        humans.setMedicalpolicy("435956");
        humans.setPhoneNumber("+7853570");
        EntityManager entityManager = db.getEmManager();
        entityManager.getTransaction().begin();
        entityManager.persist(humans);
        entityManager.getTransaction().commit();
        db.closeEntityManager();
        DbWork.clear();*/
    }

    static class AnotherRun implements Runnable {
        Integer num;
        public AnotherRun(Integer num){
            this.num=num;
        }
        @Override
        public void run() {
            System.out.println("------------------------------------"+num);
            DbWork db = DbWork.getInstance();
            EntityManager entityManager = db.getEmManager();
            entityManager.getTransaction().begin();
            Humans humans = new Humans();
            //----- всатвка
//            if (this.num==1){
//                humans.setLastName("Иванов");
//                humans.setFirstName("Иван");
//                humans.setMedicalpolicy("435956");
//                humans.setPhoneNumber("+7853570");
//            }else{
//                humans.setLastName("Петрова");
//                humans.setFirstName("Мария");
//                humans.setMedicalpolicy("4629574");
//                humans.setPhoneNumber("+725546495");
//            }
//            entityManager.persist(humans);
            // редактирование
            int d1 = 9;
            Long dg1 = new Long(d1);
            humans = entityManager.find(Humans.class,dg1);
            if (this.num==1){
                humans.setMedicalpolicy("55555555555");
            }else {
                humans.setMedicalpolicy("222222222222");
            }
            entityManager.merge(humans);
            //---------------------------
            entityManager.getTransaction().commit();
            db.closeEntityManager();

            DbWork.clear();
        }
    }
}