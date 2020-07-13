package top.soliloquize;

import top.soliloquize.bean.Column;
import top.soliloquize.bean.Database;
import top.soliloquize.bean.Table;
import top.soliloquize.date.Dates;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SCHEMATA表：提供了关于数据库的信息。
 * <p>
 * TABLES表：给出了关于数据库中的表的信息。
 * <p>
 * COLUMNS表：给出了表中的列信息。
 * <p>
 * STATISTICS表：给出了关于表索引的信息。
 * <p>
 * USER_PRIVILEGES表：给出了关于全程权限的信息。该信息源自mysql.user授权表。
 * <p>
 * SCHEMA_PRIVILEGES表：给出了关于方案（数据库）权限的信息。该信息来自mysql.db授权表。
 * <p>
 * TABLE_PRIVILEGES表：给出了关于表权限的信息。该信息源自mysql.tables_priv授权表。
 * <p>
 * COLUMN_PRIVILEGES表：给出了关于列权限的信息。该信息源自mysql.columns_priv授权表。
 * <p>
 * CHARACTER_SETS表：提供了关于可用字符集的信息。
 * <p>
 * COLLATIONS表：提供了关于各字符集的对照信息。
 * <p>
 * COLLATION_CHARACTER_SET_APPLICABILITY表：指明了可用于校对的字符集。
 * <p>
 * TABLE_CONSTRAINTS表：描述了存在约束的表。
 * <p>
 * KEY_COLUMN_USAGE表：描述了具有约束的键列。
 * <p>
 * ROUTINES表：提供了关于存储子程序（存储程序和函数）的信息。此时，ROUTINES表不包含自定义函数（UDF）。
 * <p>
 * VIEWS表：给出了关于数据库中的视图的信息。
 * <p>
 * TRIGGERS表：提供了关于触发程序的信息。
 *
 * @author wb
 * @date 2020/7/10
 */
public class Metadata {
    private DatabaseMetaData dbMetaData = null;
    private Connection con = null;
    private String databaseName;
    private String userName;
    private String password;
    private Integer port;
    private String ip;

    public Metadata(String databaseName, String userName, String password, Integer port, String ip) {
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.port = port;
        this.ip = ip;
        this.getDatabaseMetaData();
    }

    public static void main(String[] args) {
        Metadata metadata = new Metadata("test", "root", "wangBin_123", 3306, "localhost");
        try {
            metadata.getDatabaseMetadata();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                metadata.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDatabaseMetaData() {
        try {
            if (dbMetaData == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://" + this.ip + ":" + this.port + "/" + this.databaseName;
                this.con = DriverManager.getConnection(url, this.userName, this.password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabaseMetadata() throws SQLException {
        String sql1 = "select DEFAULT_CHARACTER_SET_NAME, DEFAULT_COLLATION_NAME from INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
        PreparedStatement ps1 = this.con.prepareStatement(sql1);
        ps1.setString(1, this.databaseName);
        ResultSet rs1 = ps1.executeQuery();
        Database database = new Database().setDatabaseName(this.databaseName);
        while (rs1.next()) {
            database.setDefaultCharset(rs1.getString("DEFAULT_CHARACTER_SET_NAME"))
                    .setDefaultSort(rs1.getString("DEFAULT_COLLATION_NAME"));
        }
        String sql2 = "SELECT SUM(DATA_LENGTH)+ SUM(INDEX_LENGTH) AS total FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?";
        PreparedStatement ps2 = this.con.prepareStatement(sql2);
        ps2.setString(1, this.databaseName);
        ResultSet rs2 = ps2.executeQuery();
        while (rs2.next()) {
            database.setDatabaseSize(Math.toIntExact(rs2.getLong("total") / 1024 / 1024));
        }

        List<Table> tableList = this.getAllTableName().stream().map(each -> {
            try {
                return this.getTableMetadata(each);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }).collect(Collectors.toList());
        database.setTableList(tableList);

        ps1.close();
        ps2.close();
        return database;
    }

    public Table getTableMetadata(String tableName) throws SQLException {
        String sql = "SELECT ENGINE, TABLE_COLLATION, TABLE_COMMENT, AUTO_INCREMENT, CREATE_TIME, UPDATE_TIME, TABLE_ROWS FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";

        PreparedStatement ps = this.con.prepareStatement(sql);
        ps.setString(1, this.databaseName);
        ps.setString(2, tableName);

        ResultSet rs = ps.executeQuery();
        Table table = new Table().setTableName(tableName);
        while (rs.next()) {
            table.setEngineName(rs.getString("ENGINE"))
                    .setSort(rs.getString("TABLE_COLLATION"))
                    .setTableComment(rs.getString("TABLE_COMMENT"))
                    .setAutoIncrementValue(rs.getInt("AUTO_INCREMENT"))
                    .setCreateTime(Dates.date2LocalDateTime(rs.getTimestamp("CREATE_TIME")))
                    .setUpdateTime(Dates.date2LocalDateTime(rs.getTimestamp("UPDATE_TIME")))
                    .setTableRows(rs.getInt("TABLE_ROWS"));
        }

        List<Column> columnList = this.getAllColumnName(tableName).stream().map(each -> {
            try {
                return this.getColumnMetadata(tableName, each);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }).collect(Collectors.toList());
        table.setColumnList(columnList);

        ps.close();
        return table;
    }

    public Column getColumnMetadata(String tableName, String columnName) throws SQLException {
        String sql = "SELECT COLUMN_NAME, COLUMN_TYPE, IS_NULLABLE, COLUMN_KEY, EXTRA, COLUMN_DEFAULT, COLUMN_COMMENT, CHARACTER_SET_NAME, COLLATION_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=? AND TABLE_NAME=? AND COLUMN_NAME=?";

        PreparedStatement ps = this.con.prepareStatement(sql);
        ps.setString(1, this.databaseName);
        ps.setString(2, tableName);
        ps.setString(3, columnName);

        ResultSet rs = ps.executeQuery();
        Column column = new Column().setColumnName(columnName);

        while (rs.next()) {
            column.setColumnName(rs.getString("COLUMN_NAME"))
                    .setColumnType(rs.getString("COLUMN_TYPE"))
                    .setNullAble("YES".equalsIgnoreCase(rs.getString("IS_NULLABLE")))
                    .setPrimary("PRI".equalsIgnoreCase(rs.getString("COLUMN_KEY")))
                    .setExtra(rs.getString("EXTRA"))
                    .setDefaultValue(rs.getObject("COLUMN_DEFAULT"))
                    .setComment(rs.getString("COLUMN_COMMENT"))
                    .setCharset(rs.getString("CHARACTER_SET_NAME"))
                    .setSort(rs.getString("COLLATION_NAME"))
                    .setAutoIncrement("auto_increment".equalsIgnoreCase(rs.getString("EXTRA")))
                    .setDataType(rs.getString("DATA_TYPE"));
        }

        ps.close();
        return column;
    }

    public List<String> getAllTableName() throws SQLException {
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=?";

        PreparedStatement ps = this.con.prepareStatement(sql);
        ps.setString(1, this.databaseName);

        ResultSet rs = ps.executeQuery();
        List<String> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getString("TABLE_NAME"));
        }
        ps.close();
        return data;
    }

    public List<String> getAllColumnName(String tableName) throws SQLException {
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=? AND TABLE_NAME = ?";

        PreparedStatement ps = this.con.prepareStatement(sql);
        ps.setString(1, this.databaseName);
        ps.setString(2, tableName);

        ResultSet rs = ps.executeQuery();
        List<String> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getString("COLUMN_NAME"));
        }

        ps.close();
        return data;
    }

}
