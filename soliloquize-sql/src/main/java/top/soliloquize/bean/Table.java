package top.soliloquize.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wb
 * @date 2020/7/13
 */
public class Table {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 引擎
     */
    private String engineName;
    /**
     * 自增
     */
    private Integer autoIncrementValue;
    /**
     * 字符集
     */
    private String charset;
    /**
     * 排序规则
     */
    private String sort;
    /**
     * 描述
     */
    private String tableComment;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 表数据条数
     */
    private Integer tableRows;
    /**
     * 列信息
     */
    private List<Column> columnList = new ArrayList<>();

    public String getTableName() {
        return tableName;
    }

    public Table setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getEngineName() {
        return engineName;
    }

    public Table setEngineName(String engineName) {
        this.engineName = engineName;
        return this;
    }

    public Integer getAutoIncrementValue() {
        return autoIncrementValue;
    }

    public Table setAutoIncrementValue(Integer autoIncrementValue) {
        this.autoIncrementValue = autoIncrementValue;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Table setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public Table setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public String getTableComment() {
        return tableComment;
    }

    public Table setTableComment(String tableComment) {
        this.tableComment = tableComment;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Table setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public Table setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getTableRows() {
        return tableRows;
    }

    public Table setTableRows(Integer tableRows) {
        this.tableRows = tableRows;
        return this;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public Table setColumnList(List<Column> columnList) {
        this.columnList = columnList;
        return this;
    }
}
