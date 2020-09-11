package top.soliloquize.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import top.soliloquize.User;
import top.soliloquize.lang.ChainingList;

import java.io.File;
import java.util.List;

/**
 * HSSFWorkbook针对EXCEL 2003版本，扩展名为.xls，此种的局限就是导出的行数最多为65535行。因为导出行数受限，不足7万行，所以一般不会发生内存溢出(OOM)的情况
 * HSSFWorkbook的Excel Sheet导出条数上限(<=2003版)是65535行、256列,
 *
 * @author wb
 * @date 2020/7/29
 */
public class HExcels<T> extends AbstractExcels<T> {
    /**
     * 最大行数
     */
    private int maxRowNum = 65535;
    /**
     * 最大列数
     */
    private int maxColumnNum = 256;

    public HExcels(HSSFWorkbook workbook, String filePath) {
        super(workbook, filePath);
    }

    public HExcels(HSSFWorkbook workbook, String filePath, Class<T> clz) {
        super(workbook, filePath, clz);
    }

    public File data2Excel(List<T> data) {
        if (data.size() > this.maxRowNum || super.getClassStruct().getFieldStructList().size() > this.maxColumnNum) {
            return null;
        }
        super.createSheet("sheet1");
        super.fillData(data);
        return super.createExcel();
    }

    public static void main(String[] args) {
        HExcels abstractExcels = new HExcels(new HSSFWorkbook(), "test", User.class);
        abstractExcels.data2Excel(new ChainingList<User>().add(new User().setName("张三").setAge(18d)).add(new User().setName("张三").setAge(18d)).add(new User().setName("张三").setAge(18d)).getList());

    }
}
