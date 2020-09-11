package top.soliloquize.object;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wb
 * @date 2020/7/13
 */
public class FieldStruct {
    private String filedName;
    private String type;
    private Method getMethod;
    private Method setMethod;
    private List<ParamStruct> setMethodParams;

    public String getFiledName() {
        return filedName;
    }

    public FieldStruct setFiledName(String filedName) {
        this.filedName = filedName;
        return this;
    }

    public String getType() {
        return type;
    }

    public FieldStruct setType(String type) {
        this.type = type;
        return this;
    }

    public Method getGetMethod() {
        return getMethod;
    }

    public FieldStruct setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
        return this;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    public FieldStruct setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
        return this;
    }

    public List<ParamStruct> getSetMethodParams() {
        return setMethodParams;
    }

    public FieldStruct setSetMethodParams(List<ParamStruct> setMethodParams) {
        this.setMethodParams = setMethodParams;
        return this;
    }

    public static void main(String[] args) {
        for (int i = 0; i < new FieldStruct().getClass().getFields().length; i++) {
            System.out.println(new FieldStruct().getClass().getFields()[i].getName());
        }
        System.out.println("----------------");
        for (int i = 0; i < new FieldStruct().getClass().getDeclaredFields().length; i++) {
            System.out.println(new FieldStruct().getClass().getDeclaredFields()[i].getName());
        }
    }
}
