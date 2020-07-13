package top.soliloquize.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb
 * @date 2020/7/13
 */
public class Database {
    /**
     * 库名
     */
    private String databaseName;
    /**
     * 字符集
     */
    private String defaultCharset;
    /**
     * 排序规则
     */
    private String defaultSort;
    /**
     * 大小,单位MB
     */
    private Integer databaseSize;
    /**
     * 表信息
     */
    private List<Table> tableList = new ArrayList<>();

    public String getDatabaseName() {
        return databaseName;
    }

    public Database setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public String getDefaultCharset() {
        return defaultCharset;
    }

    public Database setDefaultCharset(String defaultCharset) {
        this.defaultCharset = defaultCharset;
        return this;
    }

    public String getDefaultSort() {
        return defaultSort;
    }

    public Database setDefaultSort(String defaultSort) {
        this.defaultSort = defaultSort;
        return this;
    }

    public Integer getDatabaseSize() {
        return databaseSize;
    }

    public Database setDatabaseSize(Integer databaseSize) {
        this.databaseSize = databaseSize;
        return this;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public Database setTableList(List<Table> tableList) {
        this.tableList = tableList;
        return this;
    }
}
