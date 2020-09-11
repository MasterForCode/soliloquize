package top.soliloquize.object;

/**
 * @author wb
 * @date 2020/7/13
 */
public class ParamStruct {
    private String paramName;
    private String type;

    public String getParamName() {
        return paramName;
    }

    public ParamStruct setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public String getType() {
        return type;
    }

    public ParamStruct setType(String type) {
        this.type = type;
        return this;
    }
}
