import annotation.ManyToOne;
import annotation.OneToOne;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class EntityManagerFactory {

    /**
     * Подключение к БД. Это подключение передается в класс реализацию EntityManager
     * при его создании.
     */
    private Connection connection;

    /**
     * Параметры подключения к базе данных:
     * <p> url - путь к БД для JDBC драйвера (например "jdbc:postgresql://localhost:5432/lab8")
     * <p> username - имя пользователя СУБД
     * <p> password - пароль пользователя СУБД
     */
    private Properties dbProperties;

    /**
     * Структура для хранения сведений о базе данных (таблицы, их поля)
     */
    private HashMap<String, HashSet<String>> tables = new HashMap<>();

    /**
     * Конструктор класса
     * @param dbProperties - параметры подключения к базе данных
     */
    public EntityManagerFactory(Properties dbProperties) {
        this.dbProperties = dbProperties;
    }

    public EntityManagerFactory() {
        Properties myProperties = new Properties();
        myProperties.setProperty("url","jdbc:postgresql://localhost:5432/oodb8");
        myProperties.setProperty("username","postgres");
        myProperties.setProperty("password","999999");
        this.dbProperties=myProperties;
    }

    /**
     * Метод создает, инициализирует экземпляр класса, реализующего EntityManager
     */
    public EntityManager createEntityManager() {

        EntityManagerImpl emi = new EntityManagerImpl(this.dbProperties);  // ----
        return emi;
    }

    /**
     * Метод создает (если еще не создано) и возвращает подключение к БД
     */
    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(this.dbProperties.getProperty("url"),
                        this.dbProperties.getProperty("username"), this.dbProperties.getProperty("password"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Метод сканирует структуру классов модели, структуру базы данных,
     * сравнивает их и возвращает булевский результат сравнения
     */
    public boolean scanModel() {
        boolean result=true;
        String PATH_FOR_SCAN = "domain";
        try(Connection connection = getConnection()) {
            //--- сканируем классы
            List<Class<?>> classList = PathScan.find(PATH_FOR_SCAN);
            for (Class<?> cl : classList) {
                System.out.println("----------------------------");
                /* Сканируем поля классов */
                System.out.print("Класс: " + cl.getSimpleName().toLowerCase());
                // --  проверка существования таблицы в Бд
                if(isExistTable(connection, cl.getSimpleName().toLowerCase())){
                    System.out.println(" - такая таблица существует");
                }else {
                        System.out.println(" - такой таблицы нет");
                        result = false;  // модель и БД не совпадают
                }
                System.out.println("\tАтрибуты класса: ");
                Field[] fields = cl.getDeclaredFields();
                for (Field field : fields) {
                    System.out.print("\t" + field.getName());
                    // -- есть ли аннотация у поля?
                    Annotation[] fannotations = field.getAnnotations();
                    String fieldId=field.getName().toLowerCase();
                    for (Annotation a : fannotations) {
                        if (a.annotationType().equals(ManyToOne.class)|| a.annotationType().equals(OneToOne.class)) {
                            fieldId= fieldId + "_id";
                        }
                    }
                    // -- проверка существования поля в таблице в БД
                    if(isExistField(connection, cl.getSimpleName().toLowerCase(),fieldId)){
                        System.out.println(" - в таблице поле " + fieldId);
                    }else{
                        System.out.println(" - такого поля нет");
                        result=false;  // модель и БД не совпадают
                    }
                }
            }
            List<String> tbls = getTables(connection);
            for (String table : tbls) {
                List<String> fields = getFields(connection, table);
                HashSet<String> hashSetFields = new HashSet<>();
                fields.forEach(f->{
                    hashSetFields.add(f);
                });
                tables.put(table, hashSetFields);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result) {
            return true;
        }else{
            return false;
        }
    }

    public static List<String> getTables(Connection connection) throws SQLException {
        List<String> lst = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_type = 'BASE TABLE' AND" +
                        " table_schema NOT IN ('pg_catalog', 'information_schema')");
        ResultSet resultSet = st.executeQuery();
        while (resultSet.next()) {
            String s = resultSet.getString("table_name");
            lst.add(s);
        }
        st.close();
        return lst;
    }

    public static List<String> getFields(Connection connection, String tableName) throws SQLException {
        List<String> lst = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT a.attname " +
                        "FROM pg_catalog.pg_attribute a " +
                        "WHERE a.attrelid = (SELECT c.oid FROM pg_catalog.pg_class c " +
                        "LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace " +
                        " WHERE pg_catalog.pg_table_is_visible(c.oid) AND c.relname = ? )" +
                        " AND a.attnum > 0 AND NOT a.attisdropped");
        st.setString(1, tableName);
        ResultSet resultSet = st.executeQuery();
        while (resultSet.next()) {
            String s = resultSet.getString("attname");
            lst.add(s);
        }
        st.close();
        return lst;
    }

    public static boolean  isExistTable(Connection connection, String tableName) throws SQLException {
        boolean result=false;
        PreparedStatement st = connection.prepareStatement(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_type = 'BASE TABLE' AND  table_name = ? AND  " +
                        " table_schema NOT IN ('pg_catalog', 'information_schema')");
        st.setString(1, tableName);
        ResultSet resultSet = st.executeQuery();
        while (resultSet.next()) {
            String s = resultSet.getString("table_name");
            result = true;
        }
        st.close();
        return result;
    }

    public static boolean  isExistField(Connection connection, String tableName, String fieldName) throws SQLException {
        boolean result=false;
        PreparedStatement st = connection.prepareStatement(
                "SELECT a.attname " +
                        "FROM pg_catalog.pg_attribute a " +
                        "WHERE a.attrelid = (SELECT c.oid FROM pg_catalog.pg_class c " +
                        "LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace " +
                        " WHERE pg_catalog.pg_table_is_visible(c.oid) AND c.relname = ? )" +
                        " AND a.attnum > 0 AND NOT a.attisdropped AND a.attname = ? ");
        st.setString(1, tableName);
        st.setString(2, fieldName);
        ResultSet resultSet = st.executeQuery();
        while (resultSet.next()) {
            String s = resultSet.getString("attname");
            result = true;
        }
        st.close();
        return result;
    }


}
