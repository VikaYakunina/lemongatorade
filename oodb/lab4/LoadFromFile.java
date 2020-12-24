import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class LoadFromFile {

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
     * Пример чтения из файла массива JSON объектов
     */
    public static List<Human> loadHumanList() throws IOException, JsonSyntaxException {
        String hStr = "";
        File file = new File("humans.json");

        if (file.exists()) {
            hStr = new String(Files.readAllBytes(file.toPath()));
        } else {
            System.out.println("File humans.json not found!");
        }

        Gson gson = new Gson();

        Human[] hlst = gson.fromJson(hStr, Human[].class);

        return Arrays.asList(hlst);
    }
}