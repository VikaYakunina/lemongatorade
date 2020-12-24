import annotation.Column;
import annotation.Entity;
import annotation.OneToOne;
import annotation.OneToMany;
import annotation.ManyToOne;
import annotation.ManyToMany;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


public class Main1 {

    public static String PATH_FOR_SCAN = "domain";

    public static void main(String[] args) {

        /* Просканируем пакет PATH_FOR_SCAN для поиска классов (включая вложенные пакеты)  */
        System.out.println("STEP 1: scan package " + PATH_FOR_SCAN+":");

        List<Class<?>> classList = PathScan.find(PATH_FOR_SCAN);
        if (classList != null)
            classList.forEach(c -> System.out.println("\t" + c.getSimpleName().toLowerCase()));

        System.out.println("STEP 2: scan class fields:");
        for (Class<?> cl : classList) {
            /* Сканируем поля классов */
            System.out.println("\tFields of class " + cl.getName());
            Field[] fields = cl.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("\t\t" + field.getName());
            }
        }

        System.out.println("STEP 3: scan class methods:");
        for (Class<?> cl : classList) {
            /* Сканируем методы классов */
            System.out.println("\tMethods of class " + cl.getName());
            Method[] methods = cl.getMethods();
            for (Method method : methods) {
                System.out.println("\t\t" + method.getName());
            }
        }

        System.out.println("STEP 4: scan class annotations:");
        for (Class<?> cl : classList) {
            Annotation[] annotations1 = cl.getAnnotations();
            if (annotations1 != null) {
                for (Annotation a : annotations1) {
                    //System.out.println("----"+a.toString());
                    if (a.annotationType().equals(Entity.class)) {
                        System.out.println("\t"+cl.getName()+" is entity!");
                    }
                }
            }
        }


        System.out.println("STEP 5: scan fields annotations:");
        for (Class<?> cl : classList) {
            System.out.println("--------- "+cl.getName()+" --------");
            Field[] fields = cl.getDeclaredFields();
            for (Field f : fields) {
                Annotation[] fannotations = f.getAnnotations();
                for (Annotation a : fannotations) {
                    if (a.annotationType().equals(Column.class)) {
                        System.out.println(String.format("\tField %s %s is attribute!", f.getType().getName(),f.getName()));
                    }
                    if (a.annotationType().equals(OneToOne.class)) {
                        System.out.println(String.format("\tField %s %s is OneToOne!", f.getType().getName(),f.getName()));
                    }
                    if (a.annotationType().equals(OneToMany.class)) {
                        System.out.println(String.format("\tField %s %s is OneToMany!", f.getType().getName(),f.getName()));
                    }
                    if (a.annotationType().equals(ManyToOne.class)) {
                        System.out.println(String.format("\tField %s %s is ManyToOne!", f.getType().getName(),f.getName()));
                    }
                    if (a.annotationType().equals(ManyToMany.class)) {
                        System.out.println(String.format("\tField %s %s is ManyToMany!", f.getType().getName(),f.getName()));
                    }
                }
            }
        }

        System.out.println("STEP 6: get superclass:");
        for (Class<?> cl : classList) {
            Class superClass = cl.getSuperclass();
            System.out.println("\tSuper class of "+cl.getName()+"  is " + superClass.getName());
        }


    }
}
