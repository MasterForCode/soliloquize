package top.soliloquize.bean;

/**
 * @author wb
 * @date 2020/7/10
 */
public class Column {
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 数据类型varchar
     */
    private String dataType;
    /**
     * 数据类型varchar(20)
     */
    private String columnType;
    /**
     * 是否非空
     */
    private boolean nullAble;
    /**
     * 是否自增
     */
    private boolean autoIncrement;
    /**
     * 是否为主键
     */
    private boolean primary;
    /**
     * 缺省值
     */
    private Object defaultValue;
    /**
     * 额外信息
     */
    private String extra;
    /**
     * 字符集
     */
    private String charset;
    /**
     * 排序规则
     */
    private String sort;
    /**
     * 注释
     */
    private String comment;

    public String getColumnName() {
        return columnName;
    }

    public Column setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getColumnType() {
        return columnType;
    }

    public String getDataType() {
        return dataType;
    }

    public Column setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public Column setColumnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public boolean isNullAble() {
        return nullAble;
    }

    public Column setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
        return this;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public Column setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
        return this;
    }

    public boolean isPrimary() {
        return primary;
    }

    public Column setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Column setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public Column setExtra(String extra) {
        this.extra = extra;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Column setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public Column setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Column setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
