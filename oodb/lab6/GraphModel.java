import annotation.OneToOne;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GraphModel {

    private List<EntityNode> entityNodeList = new ArrayList<>();

    private List<Edge> edges = new ArrayList<>();

    public EntityNode getEntityNode(Class <?> c) { // получение EntityNode из класса
        System.out.println(c.getCanonicalName());
        EntityNode entityNode = new EntityNode();
        entityNode.setEntityClass(c);
        entityNode.setEntityName(c.getName());
        //System.out.println("--------------"+entityNode.getEntityName());
        /* Сканируем поля классов - для списка аттрибутов*/
        List<EntityAttribute> atributesMy = new ArrayList<>();;
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            EntityAttribute entityAttribute = new EntityAttribute();
            entityAttribute.setAttributeType(field.getType().getName());
            entityAttribute.setAttributeName(field.getName());
            atributesMy.add(entityAttribute);
        }
        entityNode.setAtributes(atributesMy);
        return entityNode;
    }

    public void addEntity(Class<?> c) {
    }

    public List<EntityNode> getEntityNodeList() {
        return entityNodeList;
    }

    public void setEntityNodeList(List<EntityNode> entityNodeList) {
        this.entityNodeList = entityNodeList;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}