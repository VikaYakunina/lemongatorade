import domain.Hospital;
import domain.Humans;

import java.util.Properties;

public class Main2 {

    //select * from humans
    //select * from hospital

    public static void main(String[] args) throws Exception {

        //--------------- используя EntityManagerFactory -------------------------
        System.out.println("---------- используя EntityManagerFactory  ---------------------------------------------------------");
        EntityManagerFactory emf= new EntityManagerFactory();
        EntityManager emfi = emf.createEntityManager();
        //--- вставка
        Hospital hospitalIns = new Hospital();
        hospitalIns.setHospitalName("РКБ");
        hospitalIns.setHospitalAddress("Казань, Оренбургский тракт, 111");
        //emfi.persist(hospitalIns);

        System.out.println("---------- вставка humans ---------------------------------------------------------");
        //--- вставка
        Humans humansIns = new Humans();
        humansIns.setFirstName("Александр");
        humansIns.setLastName("Сергеевич");
        humansIns.setPhoneNumber("89173332244");
        humansIns.setEmail("pushkin@mail.ru");
        humansIns.setMedicalpolicy("333-445-222-22222");
        //emfi.persist(humansIns);

        System.out.println("---------- удаление ------------------------------------------------------");
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

        //----------------- Проверка на совпадение модели и таблиц в БД----------------------------------------
        if (emf.scanModel()){
            System.out.println("Модель и таблицы в БД совпадают");
        }else{
            System.out.println("Модель и таблицы в БД не совпадают");
        }
        System.out.println("-------список сущностных классов, которые были сохранены, обновлены, прочитаны -----------");
        emfi.showChange();

    }

}