import annotation.*;

import javax.naming.Name;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Програма демонстрирует формирование графа сущностей и связей между ними
 */
public class Main2 {

    // Пакет в котором расположены классы-сущности
    public static String PATH_FOR_SCAN = "domain";

    public static void main(String[] args) {

        GraphModel graph = new GraphModel();

        /* Сканируем пакет PATH_FOR_SCAN для поиска классов-сущностей  */
        List<Class<?>> classList = PathScan.find(PATH_FOR_SCAN);
        if (classList != null)
            //classList.stream().filter(c -> classIsEntity(c)).forEach();

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
            System.out.println("\tFields of class " + cl.getName());
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

                        // добавляем сущность  в список
                        graph.getEntityNodeList().add(graph.getEntityNode(cl));
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

                        //  добавляем в список edges
                        Edge edge = new Edge();
                        edge.setNodeSource(graph.getEntityNode(cl));

                        Class cc = f.getType().getClass();   // как получить тип-класс списка ?

                        System.out.println("------" + f.getType().getCanonicalName());
                        edge.setNodeTarget(graph.getEntityNode(cc));
                        edge.setRelationType(RelationType.OneToMany);
                        graph.getEdges().add(edge);
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
            // добавляем в список edges если суперкласс в domain - OneToOne
            if (superClass.getName().contains("domain")){
                Edge edge = new Edge();
                edge.setNodeTarget(graph.getEntityNode(cl));
                edge.setNodeSource(graph.getEntityNode(superClass));
                edge.setRelationType(RelationType.OneToOne);
                graph.getEdges().add(edge);
            }
        }
        //-------------------------------------------------------------------
        System.out.println("------ EntityNodeList -----");
        for (int i=0; i<graph.getEntityNodeList().size(); i++){
            System.out.println(graph.getEntityNodeList().get(i).getEntityName());
        }
        System.out.println("------ Edges -----");
        for (int i=0; i<graph.getEdges().size(); i++){
            String str;
            str=graph.getEdges().get(i).getNodeSource().getEntityName()+" -  "+
                graph.getEdges().get(i).getRelationType().toString();
            if (graph.getEdges().get(i).getNodeTarget()!=null){
                str=str+" - "+graph.getEdges().get(i).getNodeTarget().getEntityName();
            }
            System.out.println(str);
        }
    }
    private static boolean classIsEntity(Class<?> c) {
        Annotation[] annotations = c.getAnnotations();
        for (Annotation a : annotations) {
            if (a.equals(Entity.class)) {
                return true;
            }
        }
        return false;
    }
    private static void addEntityToGraph(Class<?> c, GraphModel graph) {
    }
}