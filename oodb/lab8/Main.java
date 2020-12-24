import domain.Hospital;
import domain.Humans;

import java.util.Properties;

public class Main {

    //select * from humans
    //select * from hospital

    public static void main(String[] args) throws Exception {

        Properties dbProperties = new Properties();
        dbProperties.setProperty("url","jdbc:postgresql://localhost:5432/oodb8");
        dbProperties.setProperty("username","postgres");
        dbProperties.setProperty("password","999999");

        EntityManagerImpl emi = new EntityManagerImpl(dbProperties);

        System.out.println("---------- вставка hospital ---------------------------------------------------------");
        //--- вставка
        Hospital hospitalIns = new Hospital();
        hospitalIns.setHospitalName("Поликлиника 7");
        hospitalIns.setHospitalAddress("Казань, ул.Чуйкова, 10");
        //emi.persist(hospitalIns);

        System.out.println("---------- вставка humans ---------------------------------------------------------");
        //--- вставка
        Humans humansIns = new Humans();
        humansIns.setFirstName("Александр");
        humansIns.setLastName("Пушкин");
        humansIns.setPhoneNumber("89173332244");
        humansIns.setEmail("pushkin@mail.ru");
        humansIns.setMedicalpolicy("333-445-222-22222");
        //emi.persist(humansIns);

        System.out.println("---------- удаление ---------------------------------------------------------");
        // --- удаление
        Hospital hospitalDel = new Hospital();
        hospitalDel.setHospitalName("Поликлиника 7");
        hospitalDel.setHospitalAddress("Казань, ул.Чуйкова, 10");
        //emi.remove(hospitalDel);

        System.out.println("--------- поиск select  ---------------------------------------------------------");
        // --- поиск
        Hospital hospitalFind = new Hospital();
        hospitalFind.setHospitalName("Поликлиника 17");
        hospitalFind.setHospitalAddress("Казань, ул.Декабристов, 10");
        Hospital hospitalRes = new Hospital();
        //hospitalRes = emi.find(Hospital.class, hospitalFind);
         System.out.println("---------- изменение  ---------------------------------------------------------");
        // --- изменение
        Hospital hospitalMerge = new Hospital();
        hospitalMerge.setId(Long.valueOf(2));  // изменяем строку с id=8
        hospitalMerge.setHospitalName("Поликлиника 17");
        hospitalMerge.setHospitalAddress("Казань, ул.Декабристов, 10");
        //hospitalMerge = emi.merge(hospitalMerge);

        emi.close();
        //-----------------------------------------
        System.out.println("-------список сущностных классов, которые были сохранены, обновлены, прочитаны -----------");
        for (int i=0; i<emi.entityList.size(); i++){
            System.out.println(emi.entityList.get(i).getClass().getName().toString());
        }

        //--------------- используя EntityManagerFactory -------------------------
        System.out.println("---------- используя EntityManagerFactory  ---------------------------------------------------------");
        EntityManagerFactory emf= new EntityManagerFactory(dbProperties);
        EntityManager emfi = emf.createEntityManager();
        //--- вставка
        Hospital hospitalIns2 = new Hospital();
        hospitalIns2.setHospitalName("РКБ");
        hospitalIns2.setHospitalAddress("Казань, Оренбургский тракт, 1");
        //emfi.persist(hospitalIns2);

        //----------------- Проверка на совпадение модели и таблиц в БД----------------------------------------
        if (emf.scanModel()){
            System.out.println("Модель и таблицы в БД совпадают");
        }else{
            System.out.println("Модель и таблицы в БД не совпадают");
        }
    }

}