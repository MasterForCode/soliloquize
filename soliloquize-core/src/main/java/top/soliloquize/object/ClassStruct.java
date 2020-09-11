package top.soliloquize.object;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb
 * @date 2020/7/13
 */
public class ClassStruct {
    private String simpleName;
    private String className;
    private List<FieldStruct> fieldStructList = new ArrayList<>();

    public String getSimpleName() {
        return simpleName;
    }

    public ClassStruct setSimpleName(String simpleName) {
        this.simpleName = simpleName;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public ClassStruct setClassName(String className) {
        this.className = className;
        return this;
    }

    public List<FieldStruct> getFieldStructList() {
        return fieldStructList;
    }

    public ClassStruct setFieldStructList(List<FieldStruct> fieldStructList) {
        this.fieldStructList = fieldStructList;
        return this;
    }
}
