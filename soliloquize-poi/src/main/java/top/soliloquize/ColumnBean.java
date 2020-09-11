package top.soliloquize;


import java.lang.reflect.Method;

/**
 * @author wb
 * @date 2020/1/2
 */
public class ColumnBean {
    /**
     * get时是返回值类型
     * set时是参数类型
     */
    String type;
    Integer columnIndex;
    /**
     * get或set方法
     */
    Method method;
    /**
     * 字段名
     */
    String fieldName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
