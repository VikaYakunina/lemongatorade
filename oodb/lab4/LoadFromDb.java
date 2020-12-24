import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class LoadFromDb {


    public static Hospital load() throws IOException {
        Hospital hospital = null;
        String hospitalStr = "";
        File file = new File("hospital.json");

        if (file.exists()) {
            hospitalStr = new String(Files.readAllBytes(file.toPath()));
        }

        hospital = new Gson().fromJson(hospitalStr, Hospital.class);

        return hospital;
    }

    /**
     * чтение из файла массива JSON объектов
     */
    public static List<Human> loadHumanList(Connection connection) throws JsonSyntaxException, SQLException {
        String hStr = "";

        PreparedStatement statement =
                connection.prepareStatement("select contentb from jtest ");

        ResultSet resultSet = statement.executeQuery();

        System.out.println("------ Загрузили из БД и выводим ---------------------------");
        while (resultSet.next()) {
            hStr = resultSet.getString("contentb");
            System.out.println(hStr);
        }

        statement.close();

        Gson gson = new Gson();

        Human[] hlst = gson.fromJson(hStr, Human[].class);


        statement =
                connection.prepareStatement("select contentb->0 as c from jtest ");

        resultSet = statement.executeQuery();

        System.out.println("------ вывод объекта по заданому порядковому номеру ---------------------------");
        while (resultSet.next()) {
            hStr = resultSet.getString("c");
            System.out.println(hStr);

        }

        return Arrays.asList(hlst);
    }
    
}