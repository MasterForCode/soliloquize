package top.soliloquize.object.common;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author wb
 * @date 2020/8/14
 */
public class ClassStruct {
    private String simpleName;
    private String fullyQualifiedName;
    private Type type;
    private List<FieldStruct> fieldStructList;
    private List<MethodStruct> methodStructList;

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<FieldStruct> getFieldStructList() {
        return fieldStructList;
    }

    public void setFieldStructList(List<FieldStruct> fieldStructList) {
        this.fieldStructList = fieldStructList;
    }

    public List<MethodStruct> getMethodStructList() {
        return methodStructList;
    }

    public void setMethodStructList(List<MethodStruct> methodStructList) {
        this.methodStructList = methodStructList;
    }
}
