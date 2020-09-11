package top.soliloquize.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import top.soliloquize.User;
import top.soliloquize.collection.Collections;
import top.soliloquize.lang.ChainingList;
import top.soliloquize.object.ClassStruct;
import top.soliloquize.object.FieldStruct;
import top.soliloquize.object.ObjectParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class AbstractExcels<T> {
    private Workbook workbook;
    private String filePath;
    private int sheetNum = 0;
    private Sheet currentSheet;
    private Row currentRow;
    private Class<T> clz;
    private ClassStruct classStruct;
    private boolean destroyWhenCreatedExcel;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getSheetNum() {
        return sheetNum;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public Row getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(Row currentRow) {
        this.currentRow = currentRow;
    }

    public Class<T> getClz() {
        return clz;
    }

    public void setClz(Class<T> clz) {
        this.clz = clz;
    }

    public ClassStruct getClassStruct() {
        return classStruct;
    }

    public void setClassStruct(ClassStruct classStruct) {
        this.classStruct = classStruct;
    }

    public boolean isDestroyWhenCreatedExcel() {
        return destroyWhenCreatedExcel;
    }

    public void setDestroyWhenCreatedExcel(boolean destroyWhenCreatedExcel) {
        this.destroyWhenCreatedExcel = destroyWhenCreatedExcel;
    }

    public AbstractExcels(Workbook workbook, String filePath) {
        this.workbook = workbook;
        this.filePath = filePath;
    }

    public AbstractExcels(Workbook workbook, String filePath, Class<T> clz) {
        this.workbook = workbook;
        this.filePath = filePath;
        this.clz = clz;
        this.classStruct = ObjectParser.parse(this.clz);
    }

    public static void main(String[] args) {
//        AbstractExcels abstractExcels = new AbstractExcels(new HSSFWorkbook(), "test", User.class);
//        abstractExcels.createSheet("test");
//        abstractExcels.setHeader(false, new ChainingList().add("姓名").add("年龄").getList());
//        abstractExcels.fillData(new ChainingList<User>().add(new User().setName("张三").setAge(18d)).add(new User().setName("张三").setAge(18d)).add(new User().setName("张三").setAge(18d)).getList());
//        abstractExcels.createExcel();
    }

    /**
     * 创建sheet
     *
     * @param sheetName sheet名
     */
    public void createSheet(String... sheetName) {
        for (String s : sheetName) {
            Sheet sheet = this.workbook.getSheet(s);
            if (sheet != null) {
                // 不能创建同名sheet
                return;
            }
            this.currentSheet = this.workbook.createSheet(s);
            this.sheetNum++;
        }
    }

    /**
     * 填充数据
     * @param data 数据
     */
    public void fillData(List<T> data) {
        data.forEach(this::fillRowData);
    }

    /**
     * 向row中填充数据
     *
     * @param data 数据
     */
    public void fillRowData(T data) {
        this.currentRow = this.currentSheet.createRow(this.currentRow.getRowNum() + 1);
        for (int i = 0; i < this.classStruct.getFieldStructList().size(); i++) {
            try {
                FieldStruct fieldStruct = this.classStruct.getFieldStructList().get(i);
                this.createCellAndSetCellValue(i, fieldStruct.getGetMethod().invoke(data), this.dataType2CellType(fieldStruct.getType()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                // 一般不会发生异常
                e.printStackTrace();
            }
        }
    }

    /**
     * 向row中填充数据
     *
     * @param values 数据
     */
    public void fillRowData(List<Object> values) {
        this.currentRow = this.currentSheet.createRow(this.currentRow.getRowNum() + 1);
        for (int i = 0; i < values.size(); i++) {
            this.createCellAndSetCellValue(i, values.get(i), CellType.STRING);
        }
    }

    /**
     * 设置表头
     *
     * @param allSheet    是否给所有sheet都设置表头
     * @param columnNames 表头
     */
    public void setHeader(boolean allSheet, List<String> columnNames) {
        if (allSheet) {
            for (int i = 0; i < this.sheetNum; i++) {
                this.currentSheet = this.workbook.getSheetAt(i);
                this.currentRow = this.currentSheet.createRow(0);
                Collections.loop(columnNames, (j, columnName) -> this.createCellAndSetCellValue(j, columnName, CellType.STRING));
            }
        } else {
            this.currentRow = this.currentSheet.createRow(0);
            Collections.loop(columnNames, (j, columnName) -> this.createCellAndSetCellValue(j, columnName, CellType.STRING));
        }
    }

    /**
     * 创建cell,并设置值
     *
     * @param columnIndex cell所在的列
     * @param value       cell值
     * @param type        值类型
     */
    public void createCellAndSetCellValue(int columnIndex, Object value, CellType type) {
        Cell cell = this.currentRow.createCell(columnIndex);
        this.setCellValue(cell, value, type);
    }

    /**
     * 给cell设置值
     *
     * @param cell      cell
     * @param cellValue cellValue
     * @param type      值的类型
     */
    public void setCellValue(Cell cell, Object cellValue, CellType type) {
        if (cellValue == null) {
            cellValue = "";
            type = CellType.STRING;
        }
        switch (type) {
            case BOOLEAN:
                cell.setCellValue((boolean) cellValue);
                break;
            case DATE:
                cell.setCellValue((Date) cellValue);
                break;
            case DOUBLE:
                cell.setCellValue((Double) cellValue);
                break;
            case CALENDAR:
                cell.setCellValue((Calendar) cellValue);
                break;
            case LOCAL_DATE:
                cell.setCellValue((LocalDate) cellValue);
                break;
            case LOCAL_DATE_TIME:
                cell.setCellValue((LocalDateTime) cellValue);
                break;
            default:
                cell.setCellValue(cellValue.toString());
        }
    }

    /**
     * 生成excel
     *
     * @return excel文件
     */
    public File createExcel() {
        File file = new File(this.filePath + ".xls");
        return this.create(file);
    }

    /**
     * 生成excel
     *
     * @param file excel文件
     * @return excel文件
     */
    public File createExcel(File file) {
        this.create(file);
        this.filePath = file.getAbsolutePath();
        return file;
    }

    /**
     * 生成excel
     *
     * @param file excel文件
     * @return excel文件
     */
    public File create(File file) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            this.workbook.write(outputStream);
            this.workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (this.destroyWhenCreatedExcel) {
                this.destroy(outputStream);
            }
        }
        this.filePath = file.getAbsolutePath();
        return file;
    }


    /**
     * 关闭流
     *
     * @param outputStream 输出流
     */
    public void destroy(FileOutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (this.workbook != null) {
                this.workbook.close();
                this.workbook = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据类型转cell值类型
     *
     * @param simpleTypeName 数据类型
     * @return cell值类型
     */
    public CellType dataType2CellType(String simpleTypeName) {
        switch (simpleTypeName) {
            case "Boolean":
                return CellType.BOOLEAN;
            case "Date":
                return CellType.DATE;
            case "Double":
                return CellType.DOUBLE;
            case "String":
                return CellType.STRING;
            case "Calendar":
                return CellType.CALENDAR;
            case "LocalDate":
                return CellType.LOCAL_DATE;
            case "LocalDateTime":
                return CellType.LOCAL_DATE_TIME;
            default:
                return CellType.RICH_TEXT_STRING;
        }
    }

    /**
     * Cell值支持的类型
     */
    public enum CellType {
        /**
         * boolean类型
         */
        BOOLEAN("boolean"),
        /**
         * date类型
         */
        DATE("date"),
        /**
         * double类型
         */
        DOUBLE("double"),
        /**
         * string类型
         */
        STRING("string"),
        /**
         * calendar类型
         */
        CALENDAR("calendar"),
        /**
         * localDate类型
         */
        LOCAL_DATE("localDate"),
        /**
         * localDateTime类型
         */
        LOCAL_DATE_TIME("localDateTime"),
        /**
         * richTextString类型
         */
        RICH_TEXT_STRING("richTextString");

        private String name;

        CellType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
