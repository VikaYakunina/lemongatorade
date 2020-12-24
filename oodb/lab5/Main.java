import java.io.IOException;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        try {

            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://localhost:5432/oodb";
            Connection connection =
                    DriverManager.getConnection(dbURL, "postgres", "999999");
            long startTime = System.currentTimeMillis();

            //-- вставка объекта целиком --------------------------------------
            Human human1 = new Human();
            human1.setValue("Михаил,Лермонтов,Юрьевич,445556546,89072336688,27");

            PreparedStatement statement1 = connection.
                    prepareStatement("insert into human_list (person) values ( ROW"+human1.getValue()+" )");
            statement1.executeUpdate();
            System.out.println("Вставили строку в таблицу - сведения о человеке "+human1.getValue());

            //--- вставка некоторых сведений о человеке  -------------
            Human human2 = new Human();
            human2.setValue("Александр,Пушкин,Сергеевич,45357546,89072467568,37");

            PreparedStatement statement2 = connection.
                    prepareStatement("INSERT INTO human_list ( person.lastname, person.firstname, person.age) VALUES  ( '"+human2.getLastname()+"','"+human2.getFirstname()+"','"+human2.getAge()+"' ) ");
            statement2.executeUpdate();
            System.out.println("Вставили строку в таблицу - с некоторыми сведениями о человеке "+human2.getLastname()+"','"+human2.getFirstname()+"','"+human2.getAge() );

            // ---- изменение данных о человеке

            PreparedStatement statement3 = connection.
                    prepareStatement("UPDATE human_list set person.age = '25' where " + " (person).lastname like 'Лермонтов'; ");
            statement3.executeUpdate();
            System.out.println("Изменили свдения о возрасте" );

            // ---- поиск человеке по параметру - фамилия ИВАНОВ
            PreparedStatement statement4 = connection.
                    prepareStatement("select (person).firstname, (person).lastname, (person).age from human_list where " + " (person).lastname like 'ИВАНОВ'; ");
            ResultSet resultSet=statement4.executeQuery();
            System.out.println("Выполнили поиск по фамилии" );
            while(resultSet.next()){
                  System.out.println(resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3));
            }


            //-------------------------------------------------------------------
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
