import java.util.List;

/**
 *  Класс сервисных методов для работы с данными
 */
public class HumanService {

    /**
     * Поиск по имени
     */
    public static Human findHumanByFullName(List<Human> humans, String searchName) {
        Human result = null;

        for (Human person : humans) {
            if (person.getFullName().equals(searchName)) {
                result = person;
            }
        }
        return result;
    }
}