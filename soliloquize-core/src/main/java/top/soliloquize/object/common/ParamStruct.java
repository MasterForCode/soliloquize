package top.soliloquize.object.common;

import java.lang.reflect.Type;

/**
 * @author wb
 * @date 2020/8/14
 */
public class ParamStruct {
    private String name;
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
