import annotation.Column;
import annotation.Id;
import annotation.Entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.lang.Integer.parseInt;


public class EntityManagerImpl implements EntityManager {

    public List<Object> entityList = new ArrayList<Object>();

    private Connection connection;

    private Properties dbProperties;

    public EntityManagerImpl(Properties dbProperties) {
        this.dbProperties = dbProperties;
        getConnection();
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(dbProperties.getProperty("url"),
                        dbProperties.getProperty("username"), dbProperties.getProperty("password"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void close() {
        closeConnection();
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод сохраняет экземпляр класса в базу данных
     * @param var1
     */
    @Override
    public void persist(Object var1) throws Exception {
        entityList.add(var1);  // добавляем в List сущностный класс -- в случае сохранения
        // Задача этого метода сформировать и выполнить запрос
        // INSERT INTO ...
        // Для этого мы должны получить имя таблицы, имена полей и их значения
        // 1. Проверяем наличие аннотации @Entity
        Annotation entity = var1.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var1.getClass().getCanonicalName() + " is not Entity!");
        // 1. Получаем имя таблицы
        String tableName = var1.getClass().getSimpleName().toLowerCase();
        String strSql = "INSERT INTO "+tableName+" ("; // начало запроса
        String strValues=""; // для списка значений для insert
        // Получаем список членов класса
        Field[] fields = var1.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            String value="";
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class)) {
                    try {
                        Method method = var1.getClass().getMethod(
                                "get"+field.getName().substring(0,1).toUpperCase()+
                                        field.getName().substring(1),null);
                        //System.out.println(method.getName());
                        value = (String) method.invoke(var1);
                        //System.out.println(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(field.getName());
                    //--- формируем список полей и значений
                    if (value!=null){
                        if(!strValues.equals("")){
                            strSql=strSql+","+field.getName().toLowerCase();  // поля
                            strValues=strValues+",'"+value+"'";               // значения
                        }else{
                            strSql=strSql+field.getName().toLowerCase();      // поля
                            strValues="'"+value+"'";                          // значения
                        }
                    }
                }
            }
        }
        //-- если есть значения для вставки, то формируем запрос
        if(!strValues.equals("")){
            strSql=strSql+") VALUES ("+strValues+")"; // итоговый запрос
            System.out.println(strSql);
            //----------- и выполняем запрос
            PreparedStatement statement = connection.prepareStatement(strSql);
            statement.executeUpdate();
        }
    }

    @Override
    public <T> T merge(T var1) throws Exception {
        entityList.add(var1);  // добавляем в List сущностный класс -- в случае изменения
        // Задача этого метода сформировать и выполнить запрос
        // UPDATE ...  SET ...  WHERE id=...
        // 1. Проверяем наличие аннотации @Entity
        Annotation entity = var1.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var1.getClass().getCanonicalName() + " is not Entity!");
        // 1. Получаем имя таблицы
        String tableName = var1.getClass().getSimpleName().toLowerCase();
        String strSql = ""; // начало запроса
        String strWhere = ""; // условие для запроса
        // Получаем список членов класса -- для формирования запроса
        Field[] fields = var1.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            String value="";
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class)) {
                    try {
                        Method method = var1.getClass().getMethod(
                                "get"+field.getName().substring(0,1).toUpperCase()+
                                        field.getName().substring(1),null);
                        //System.out.println(method.getName());
                        value = (String) method.invoke(var1);
                        //System.out.println(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(field.getName());
                    //---  на что меняем
                    if (value!=null){
                        if(!strSql.equals("")){
                            strSql=strSql+" , "+field.getName().toLowerCase()+"='"+value+"'";
                        }else{
                            strSql=strSql+ field.getName().toLowerCase()+"='"+value+"'";
                        }
                    }
                }
                Long valueId=Long.valueOf(0); //
                if (a.annotationType().equals(Id.class)) {
                    try {
                        Method method = var1.getClass().getMethod(
                                "get"+field.getName().substring(0,1).toUpperCase()+
                                        field.getName().substring(1),null);
                        //System.out.println(method.getName());
                        valueId = (Long)method.invoke(var1);
                        //System.out.println(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println(field.getName());
                    //---  условие для запроса
                    if (valueId!=0){
                        if(!strWhere.equals("")){
                            strWhere=strWhere+" and "+field.getName().toLowerCase()+"="+valueId;
                        }else{
                            strWhere=strWhere+ field.getName().toLowerCase()+"="+valueId;
                        }
                    }
                }
            }
        }
        //--  формируем запрос
        if(!strSql.equals("")){
            strSql="UPDATE "+tableName+" SET "+strSql+" WHERE "+strWhere; // итоговый запрос
            System.out.println(strSql);
            //----------- и выполняем запрос
            PreparedStatement statement = connection.prepareStatement(strSql);
            statement.executeUpdate();
        }
        return var1;   // return null;
    }

    @Override
    public void remove(Object var1) throws Exception {
        entityList.add(var1);  // добавляем в List сущностный класс -- в случае удаления
        // Задача этого метода сформировать и выполнить запрос
        // DELETE FROM ...
        // 1. Проверяем наличие аннотации @Entity
        Annotation entity = var1.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var1.getClass().getCanonicalName() + " is not Entity!");
        // 1. Получаем имя таблицы
        String tableName = var1.getClass().getSimpleName().toLowerCase();
        String strSql = ""; // начало запроса
        // Получаем список членов класса
        Field[] fields = var1.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            String value="";
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class)) {
                    try {
                        Method method = var1.getClass().getMethod(
                                "get"+field.getName().substring(0,1).toUpperCase()+
                                        field.getName().substring(1),null);
                        //System.out.println(method.getName());
                        value = (String) method.invoke(var1);
                        //System.out.println(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(field.getName());
                    //---  условие для удаления
                    if (value!=null){
                        if(!strSql.equals("")){
                            strSql=strSql+" and "+field.getName().toLowerCase()+"='"+value+"'";
                        }else{
                            strSql=strSql+field.getName().toLowerCase()+"='"+value+"'";
                        }
                    }
                }
            }
        }
        //-- если есть условие для удаления, то формируем запрос
        if(!strSql.equals("")){
            strSql="DELETE FROM "+tableName+" WHERE "+strSql; // итоговый запрос
            System.out.println(strSql);
            //----------- и выполняем запрос
            PreparedStatement statement = connection.prepareStatement(strSql);
            statement.executeUpdate();
        }
    }

    @Override
    public <T> T find(Class<T> var1, Object var2) throws Exception {
        System.out.println("--------------  "+var1);
        entityList.add(var2);  // добавляем в List сущностный класс -- в случае потска
        //Class<T> res = new   ????
        // Задача этого метода сформировать и выполнить запрос
        // SELECT ... FROM ...
        // 1. Проверяем наличие аннотации @Entity
        Annotation entity = var2.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var2.getClass().getCanonicalName() + " is not Entity!");
        // 1. Получаем имя таблицы
        String tableName = var2.getClass().getSimpleName().toLowerCase();
        String strSql = ""; // начало запроса
        // Получаем список членов класса -- для формирования запроса
        Field[] fields = var2.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            String value="";
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class)) {
                    try {
                        Method method = var2.getClass().getMethod(
                                "get"+field.getName().substring(0,1).toUpperCase()+
                                        field.getName().substring(1),null);
                        //System.out.println(method.getName());
                        value = (String) method.invoke(var2);
                        //System.out.println(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(field.getName());
                    //---  условие для запроса
                    if (value!=null){
                        if(!strSql.equals("")){
                            strSql=strSql+" and "+field.getName().toLowerCase()+"='"+value+"'";
                        }else{
                            strSql=strSql+field.getName().toLowerCase()+"='"+value+"'";
                        }
                    }
                }
            }
        }
        //-- если есть условие для запроса, то формируем запрос
        if(!strSql.equals("")){
            strSql="SELECT * FROM "+tableName+" WHERE "+strSql; // итоговый запрос
            System.out.println(strSql);
            //----------- и выполняем запрос
            PreparedStatement statement = connection.prepareStatement(strSql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = parseInt(resultSet.getString("id"));
                String hospitalName = resultSet.getString("hospitalname");
                String hospitalAddress = resultSet.getString("hospitaladdress");
                //-- надо вставить результат в объект класса var1  ------
                Field[] fieldsRes = var1.getDeclaredFields();
                for (Field field : fieldsRes) {
                    System.out.print(field.getName()+" - ");
                    System.out.println(resultSet.getString(resultSet.findColumn(field.getName().toLowerCase())));
                    // как вставить значения в атрибуты класса ???????
                    Method method = var1.getMethod(
                            "set"+field.getName().substring(0,1).toUpperCase()+
                                    field.getName().substring(1),field.getType());
                    //System.out.println(method.getName());
                    Annotation[] annotations = field.getAnnotations();
                    for (Annotation a : annotations) {
                        if (a.annotationType().equals(Column.class)) {
                        //    method.invoke(var1, resultSet.getString(resultSet.findColumn(field.getName().toLowerCase())));
                        }
                        if (a.annotationType().equals(Id.class)) {
                        //    method.invoke(var1, parseInt(resultSet.getString(resultSet.findColumn(field.getName().toLowerCase()))));
                        }
                    }
                }
                break; // если нашли, то выходим из цикла
            }
            statement.close();
        }
        return null;
    }

    @Override
    public void refresh(Object var1) {

    }

    public void showChange(){
        for (int i=0; i<entityList.size(); i++){
             System.out.println(entityList.get(i).getClass().getName().toString());
         }
    }
}