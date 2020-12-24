import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SaveDB {

    public static void saveHumanList(List<Human> humans, Connection connection) throws SQLException {

        if (humans != null && humans.size() > 0) {
            Gson gson;
            gson = new Gson();

            String humansAsJson = gson.toJson(humans);

            PreparedStatement statement = connection.
                    prepareStatement("insert into jtest (content, contentb) values ( cast( ? as json) , cast( ? as json) )");
            statement.setString(1, humansAsJson);
            statement.setString(2, humansAsJson);

            int count = statement.executeUpdate();

            System.out.println(count + " records added!");

            statement.close();

        }
    }
}