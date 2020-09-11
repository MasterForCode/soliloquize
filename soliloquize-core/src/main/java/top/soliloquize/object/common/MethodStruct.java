package top.soliloquize.object.common;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author wb
 * @date 2020/8/14
 */
public class MethodStruct {
    private Type returnType;
    private String name;
    List<ParamStruct> paramStructList;

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParamStruct> getParamStructList() {
        return paramStructList;
    }

    public void setParamStructList(List<ParamStruct> paramStructList) {
        this.paramStructList = paramStructList;
    }
}
