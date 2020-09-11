package top.soliloquize;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.*;
import java.io.File;
import java.util.Objects;

/**
 * @author wb
 * @date 2019/12/20
 */
public class PdfUtil {
    private static PdfFont pdfFont;

    private static float fontSize = 15;

    static {
        try {
            pdfFont = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Document document = initPdfPage(PageSize.A4, true, new java.io.File("a.pdf"));
        Table table = initTable(5);
        fillDoc(document, table);

        setCenterHeader(document, "h", 1,
                20, TextAlignment.CENTER, VerticalAlignment.MIDDLE);

        setCenterFooter(document, "ha", 1,
                20, TextAlignment.CENTER, VerticalAlignment.MIDDLE);
        document.close();

    }

    public static java.io.File initFile(String filePath) {
        return new File(filePath);
    }

    /**
     * 初始化pdf
     *
     * @param pageSize pdf页面格式A4...
     * @param rotate   是否旋转
     * @param file 生成的文件
     * @return Document
     * @throws IOException IOException
     */
    public static Document initPdfPage(PageSize pageSize, boolean rotate, File file) throws IOException {
        if (rotate) {
            pageSize = pageSize.rotate();
        }
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
        return new Document(pdfDoc, pageSize);
    }

    /**
     * 初始化页面数据列
     *
     * @param columnSize 列数
     * @return Table
     */
    public static Table initTable(int columnSize) {
        if (columnSize == 0) {
            throw new RuntimeException("列数不能等于0");
        }
        Table table = new Table(columnSize);
        // pdf 页面居中
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return table;
    }

    /**
     * 向table中填充数据
     *
     * @param table 包含数据的table
     * @param data  数据
     */
    public static void fillData(Table table, java.util.List<String> data) {
        Objects.requireNonNull(table);
//        ObjectUtils.listRequireNonNull(data);
        data.stream().map(each -> new Paragraph(new Text(each).setFont(PdfUtil.pdfFont)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE)).forEach(table::addCell);
        actionTable(table);
    }

    /**
     * 向table中填充数据并设置字体
     *
     * @param table table
     * @param data  数据
     * @param font  字体
     */
    public static void fillData(Table table, java.util.List<String> data, PdfFont font) {
        Objects.requireNonNull(table);
//        ObjectUtils.listRequireNonNull(data);
        Objects.requireNonNull(font);

        data.stream().map(each -> new Paragraph(new Text(each).setFont(font))).forEach(table::addCell);
        actionTable(table);

    }

    /**
     * 将table挂载到document
     *
     * @param document document
     * @param table    table
     */
    public static void fillDoc(Document document, Table table) {
        document.add(table);
    }

    /**
     * 设置居中头部
     *
     * @param document          document
     * @param content           内容
     * @param pageIndex         第几个sheet,从1开始
     * @param margin            距离垂直边缘距离
     * @param textAlignment     textAlignment
     * @param verticalAlignment verticalAlignment
     */
    public static void setCenterHeader(Document document, String content, int pageIndex, float margin, TextAlignment textAlignment, VerticalAlignment verticalAlignment) {
        setHeader(document, content, pageIndex, document.getPdfDocument().getPage(1).getPageSize().getWidth() / 2,
                margin, textAlignment, verticalAlignment);
    }

    /**
     * 设置头部
     *
     * @param document          document
     * @param content           内容
     * @param pageIndex         第几个sheet,从1开始
     * @param x                 距离水平边缘距离
     * @param margin            距离垂直边缘距离
     * @param textAlignment     textAlignment
     * @param verticalAlignment verticalAlignment
     */
    public static void setHeader(Document document, String content, int pageIndex, float x, float margin, TextAlignment textAlignment, VerticalAlignment verticalAlignment) {
        Objects.requireNonNull(document);
        Objects.requireNonNull(content);
        Objects.requireNonNull(textAlignment);
        Objects.requireNonNull(verticalAlignment);
        document.showTextAligned(new Paragraph(content)
                .setFont(PdfUtil.pdfFont).setFontSize(PdfUtil.fontSize), x, document.getPdfDocument().getPage(1).getPageSize().getTop() - margin, pageIndex, textAlignment, verticalAlignment, 0);
    }

    /**
     * 设置居中底部
     *
     * @param document          document
     * @param content           内容
     * @param pageIndex         第几个sheet,从1开始
     * @param margin            距离垂直边缘距离
     * @param textAlignment     textAlignment
     * @param verticalAlignment verticalAlignment
     */
    public static void setCenterFooter(Document document, String content, int pageIndex, float margin, TextAlignment textAlignment, VerticalAlignment verticalAlignment) {
        setFooter(document, content, pageIndex, document.getPdfDocument().getPage(1).getPageSize().getWidth() / 2,
                margin, textAlignment, verticalAlignment);
    }

    /**
     * 设置底部
     *
     * @param document          document
     * @param content           内容
     * @param pageIndex         第几个sheet,从1开始
     * @param margin            距离垂直边缘距离
     * @param textAlignment     textAlignment
     * @param verticalAlignment verticalAlignment
     */
    public static void setFooter(Document document, String content, int pageIndex, float x, float margin, TextAlignment textAlignment, VerticalAlignment verticalAlignment) {
        Objects.requireNonNull(document);
        Objects.requireNonNull(content);
        Objects.requireNonNull(textAlignment);
        Objects.requireNonNull(verticalAlignment);
        document.showTextAligned(new Paragraph(content)
                .setFont(PdfUtil.pdfFont).setFontSize(PdfUtil.fontSize), x, document.getPdfDocument().getPage(1).getPageSize().getBottom() + margin, pageIndex, textAlignment, verticalAlignment, 0);
    }

    public static void actionTable(Table table) {
        table.flush();
    }

}
