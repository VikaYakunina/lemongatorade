import annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class ScanDB {

    public static Connection connection;

    public static void main(String[] args) {

        String PATH_FOR_SCAN = "domain";
        // Структура для хранения имен таблиц и полей (в HashSet)
        HashMap<String, HashSet<String>> tables = new HashMap<>();

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
       } catch (ClassNotFoundException e) {
            e.printStackTrace();
      }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        Class.forName("org.postgresql.Driver");
        String dbURL = "jdbc:postgresql://localhost:5432/oodb";;
        connection = DriverManager.getConnection(dbURL, "postgres", "999999");

        return connection;
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