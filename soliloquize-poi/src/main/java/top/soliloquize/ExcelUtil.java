package top.soliloquize;

/**
 * @author wb
 * @date 20.019/12/18
 */
public class ExcelUtil {
//
//    private java.util.function.Function<String, Date> string2DateFunction = value -> FormatUtils.localDate2Date(FormatUtils.string2LocalDate(value, FormatUtils.DATE_FORMAT_NORMAL));
//
//    private Function<Date, String> date2StringFunction = FormatUtils::date2String;
//
//    private SXSSFWorkbook sXSSFWorkbook;
//
//    private String filePath;
//
//    private boolean destroyWhenCreatedExcel;
//
//    /**
//     * 初始化workbook
//     */
//    public ExcelUtil() {
//        this.sXSSFWorkbook = new SXSSFWorkbook();
//    }
//
//    /**
//     * 加载excel文件
//     *
//     * @param filePath 文件路径
//     */
//    public ExcelUtil(String filePath) {
//        this.filePath = filePath;
//        try {
//            this.sXSSFWorkbook = new SXSSFWorkbook(new XSSFWorkbook(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws JsonProcessingException {
//
//    }
//
//    public static <T> void objs2Excel(Supplier<List<T>> dataSupplier, Class<T> clz, Map<String, String> map) {
//        Objects.requireNonNull(dataSupplier);
//        Objects.requireNonNull(clz);
//        Objects.requireNonNull(map);
//        List<ColumnBean> columnBeanList = analysisClz(clz, "get");
//
//        ExcelUtil excelUtil = new ExcelUtil();
//        excelUtil.setDestroyWhenCreatedExcel(true);
//        SXSSFSheet sheet = excelUtil.initSheet("sheet1", clz, map);
//        excelUtil.fillData(sheet, dataSupplier, columnBeanList.stream().filter(each -> map.containsKey(each.getFieldName())).collect(Collectors.toList()), 0);
//        excelUtil.createExcel("./test", Const.XLS);
//
//    }
//
//    public static <T> void objs2Excel(Supplier<List<T>> dataSupplier, Class<T> clz) {
//        Objects.requireNonNull(dataSupplier);
//        Objects.requireNonNull(clz);
//        List<ColumnBean> columnBeanList = analysisClz(clz, "get");
//
//        ExcelUtil excelUtil = new ExcelUtil();
//        excelUtil.setDestroyWhenCreatedExcel(true);
//        SXSSFSheet sheet = excelUtil.initSheet("sheet1", columnBeanList.stream().map(ColumnBean::getFieldName).collect(Collectors.toList()));
//        excelUtil.fillData(sheet, dataSupplier, columnBeanList, 0);
//        excelUtil.createExcel("./test", Const.XLS);
//
//    }
//
//    private static <T> List<ColumnBean> analysisClz(Class<T> clz, String type) {
//        if (Iterables.contain(Arrays.asList("set", "get"), each -> each.equals(type))) {
//            Field[] fields = clz.getDeclaredFields();
//            Method[] methods = clz.getDeclaredMethods();
//            List<ColumnBean> columnBeanList = new ArrayList<>(fields.length);
//            for (int i = 0; i < fields.length; i++) {
//                int finalI = i;
//                Method method = Iterables.findFirst(Arrays.asList(methods), each -> (type + StringUtils.capitalize(fields[finalI].getName())).equals(each.getName()));
//                if (method != null) {
//                    columnBeanList.add(ColumnBean.builder().type(fields[i].getType().getSimpleName()).columnIndex(i).method(method).fieldName(fields[i].getName()).build());
//                }
//            }
//            return columnBeanList;
//        } else {
//            return new ArrayList<>();
//        }
//
//    }
//
//    /**
//     * 获取cell的值
//     *
//     * @param cell cell
//     * @return 转化成String类型的值
//     */
//    public static String getCellValue(Cell cell) {
//        if (cell == null) {
//            return "";
//        }
//        switch (cell.getCellType()) {
//            case NUMERIC:
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    return FormatUtils.date2String(cell.getDateCellValue());
//                } else {
//                    if (StringUtils.containsIgnoreCase(cell.toString(), "e")) {
//                        return FormatUtils.df.format(cell.getNumericCellValue());
//                    } else {
//                        return cell.getNumericCellValue() + "";
//                    }
//                }
//            case STRING:
//                return cell.getStringCellValue();
//            case BOOLEAN:
//                return cell.getBooleanCellValue() + "";
//            case FORMULA:
//                try {
//                    return String.valueOf(cell.getStringCellValue());
//                } catch (IllegalStateException e) {
//                    if (DateUtil.isCellDateFormatted(cell)) {
//                        return FormatUtils.date2String(cell.getDateCellValue());
//                    } else {
//                        FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper()
//                                .createFormulaEvaluator();
//                        evaluator.evaluateFormulaCell(cell);
//                        return FormatUtils.df.format(cell.getNumericCellValue());
//                    }
//                }
//            case BLANK:
//                return "";
//            case ERROR:
//                return "非法字符";
//            default:
//                return "未知类型";
//        }
//    }
//
//    /**
//     * 验证传入的格式是否支持
//     *
//     * @param suffix    文件后缀
//     * @param predicate predicate
//     * @return boolean
//     */
//    public static <T> boolean validatorSuffix(T suffix, Predicate<T> predicate) {
//        Objects.requireNonNull(suffix);
//        Objects.requireNonNull(predicate);
//        return predicate.test(suffix);
//    }
//
//    /**
//     * 验证传入的格式是否支持
//     *
//     * @param suffix 文件后缀
//     * @return boolean
//     */
//    public static <T> boolean validatorSuffix(String suffix) {
//        Objects.requireNonNull(suffix);
//        return suffix.equals(Const.XLSX) || suffix.equals(Const.XLSM) || suffix.equals(Const.XLSB)
//                || suffix.equals(Const.XLTX) || suffix.equals(Const.XLTM) || suffix.equals(Const.XLS)
//                || suffix.equals(Const.XLA) || suffix.equals(Const.XLAM) || suffix.equals(Const.XLR) ||
//                suffix.equals(Const.XLT) || suffix.equals(Const.XML) || suffix.equals(Const.XLW);
//    }
//
//    /**
//     * 初始化sheet
//     *
//     * @param title   sheet名
//     * @param columns sheet的列
//     * @return sheet
//     */
//    public SXSSFSheet initSheet(String title, List<String> columns) {
//        SXSSFSheet sheet = this.sXSSFWorkbook.createSheet(title);
//
//        Row row = sheet.createRow(0);
//        Iterables.forEach(columns, (i, each) -> {
//            Cell cell = row.createCell(i);
//            cell.setCellValue(columns.get(i));
//        });
//        return sheet;
//    }
//
//    /**
//     * 初始化sheet
//     *
//     * @param title sheet名
//     * @param clz   Class
//     * @return sheet
//     */
//    public <T> SXSSFSheet initSheet(String title, Class<T> clz) {
//        List<ColumnBean> columnBeanList = analysisClz(clz, "get");
//        return this.initSheet(title, columnBeanList.stream().map(ColumnBean::getFieldName).collect(Collectors.toList()));
//    }
//
//    /**
//     * 初始化sheet
//     *
//     * @param title sheet名
//     * @param clz   Class
//     * @param map   列对照
//     * @return sheet
//     */
//    public <T> SXSSFSheet initSheet(String title, Class<T> clz, Map<String, String> map) {
//        List<ColumnBean> columnBeanList = analysisClz(clz, "get");
//        List<String> columns = new ArrayList<>();
//        map.forEach((k, v) -> {
//            if (Iterables.contain(columnBeanList, each -> k.equals(each.getFieldName()))) {
//                columns.add(v);
//            }
//        });
//        return this.initSheet(title, columns);
//    }
//
//    /**
//     * 初始化sheet
//     *
//     * @param title sheet名
//     * @param map   列对照
//     * @param <T>   T
//     * @return sheet
//     */
//    public <T> SXSSFSheet initSheet(String title, Map<String, String> map) {
//        return this.initSheet(title, new ArrayList<>(map.values()));
//    }
//
//    /**
//     * 初始化sheet
//     *
//     * @param title        sheet名
//     * @param columns      sheet的列
//     * @param contentStyle 表格头部cell的样式
//     * @return sheet
//     */
//    public SXSSFSheet initSheetWithStyle(String title, List<String> columns, XSSFCellStyle contentStyle) {
//        SXSSFSheet sheet = this.sXSSFWorkbook.createSheet(title);
//
//        Row row = sheet.createRow(0);
//        Iterables.forEach(columns, (i, each) -> {
//            Cell cell = row.createCell(i);
//            cell.setCellStyle(contentStyle);
//            cell.setCellValue(columns.get(i));
//        });
//        return sheet;
//    }
//
//    /**
//     * 向sheet中填充数据
//     *
//     * @param sheet        sheet
//     * @param dataSupplier 数据
//     * @param rowNum       从第几行开始填充
//     */
//    public <T> void fillData(SXSSFSheet sheet, Supplier<List<T>> dataSupplier, List<ColumnBean> columnBeanList, int rowNum) {
//        List<T> data = dataSupplier.get();
//        if (data != null && data.size() > 0) {
//            Iterables.forEach(data, (i, each) -> {
//                fillRowData(sheet, rowNum + i, null, each, columnBeanList);
//            });
//        }
//        actionSheet(sheet);
//    }
//
//    /**
//     * 添加一行数据
//     *
//     * @param sheet  sheet
//     * @param rowNum 起始行
//     * @param <T>    T
//     */
//    public <T> void fillRowData(SXSSFSheet sheet, int rowNum, XSSFCellStyle contentStyle, T obj, List<ColumnBean> columnBeanList) {
//        Row rowBody = sheet.createRow(rowNum + 1);
//        if (obj != null) {
//            Iterables.forEach(columnBeanList, (i, each) -> {
//                Cell cell = rowBody.createCell(i);
//                if (contentStyle != null) {
//                    cell.setCellStyle(contentStyle);
//                }
//                try {
//                    setCellValue(cell, each.getMethod().invoke(obj), each.getType());
//                } catch (IllegalAccessException | InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//    }
//
//    private void setCellValue(Cell cell, Object cellValue, String type) {
//        Objects.requireNonNull(cell);
//        if (cellValue == null) {
//            cellValue = "";
//            type = "";
//        }
//        switch (type) {
//            case "Integer":
//                cell.setCellValue((Integer) cellValue);
//                break;
//            case "Long":
//                cell.setCellValue((Long) cellValue);
//                break;
//            case "Double":
//                cell.setCellValue((Double) cellValue);
//                break;
//            case "Boolean":
//                cell.setCellValue((Boolean) cellValue);
//                break;
//            case "Date":
//                cell.setCellValue(date2StringFunction.apply((Date) cellValue));
//                break;
//            default:
//                cell.setCellValue(ObjectUtils.defaultIfNull(cellValue, "").toString());
//        }
//    }
//
//    /**
//     * 向sheet中填充数据
//     *
//     * @param sheet        sheet
//     * @param dataSupplier 数据
//     * @param rowNum       从第几行开始填充
//     * @param contentStyle 每个cell的样式
//     */
//    public <T> void fillDataWithStyle(SXSSFSheet sheet, Supplier<List<T>> dataSupplier, int rowNum, XSSFCellStyle contentStyle, List<ColumnBean> columnBeanList) {
//        List<T> data = dataSupplier.get();
//        Iterables.forEach(data, (i, each) -> {
//            fillRowData(sheet, rowNum + i, contentStyle, each, columnBeanList);
//        });
//        actionSheet(sheet);
//    }
//
//    /**
//     * 生成excel
//     *
//     * @param fileName 待生成文件的文件名
//     * @return 生成的文件
//     */
//    public java.io.File createExcel(String fileName) {
//        fileName += Const.XLSX;
//        File file = new File(fileName);
//        return createExcel(file);
//    }
//
//    /**
//     * 生成excel
//     *
//     * @param fileName 生成的文件名
//     * @param suffix   文件后缀
//     * @return 生成的文件
//     */
//    public File createExcel(String fileName, String suffix) {
//        if (!validatorSuffix(suffix)) {
//            throw new RuntimeException("不支持的格式");
//        }
//        fileName += Const.DOT + suffix;
//        File file = new File("", fileName);
//        return createExcel(file);
//    }
//
//    /**
//     * 生成excel
//     *
//     * @param file 待生成的文件
//     * @return 生成文件
//     */
//    public File createExcel(File file) {
//        FileOutputStream outputStream = null;
//        try {
//            outputStream = new FileOutputStream(file);
//            this.sXSSFWorkbook.write(outputStream);
//            this.sXSSFWorkbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (this.destroyWhenCreatedExcel) {
//                this.destroy(outputStream);
//            }
//        }
//        this.filePath = file.getAbsolutePath();
//        return file;
//    }
//
//    /**
//     * excel文档转List对象
//     *
//     * @param clz 对象类型
//     * @param <T> T
//     * @return List对象
//     */
//    public <T> List<T> document2List(Class<T> clz) {
//        Objects.requireNonNull(clz);
//        Objects.requireNonNull(this.sXSSFWorkbook);
//        List<T> result = new ArrayList<>();
//        for (int i = 0; i < this.sXSSFWorkbook.getNumberOfSheets(); i++) {
//            result.addAll(sheet2List(this.sXSSFWorkbook.getSheetAt(i), clz, 1));
//        }
//        return result;
//    }
//
//    /**
//     * sheet转List对象
//     *
//     * @param sheet         sheet
//     * @param clz           对象类型
//     * @param map           对象字段和sheet列的对照
//     * @param startRowIndex sheet的起始行
//     * @param <T>           T
//     * @return List对象
//     */
//    public <T> List<T> sheet2List(Sheet sheet, Class<T> clz, Map<String, Integer> map, int... startRowIndex) {
//        if (sheet == null) {
//            return new ArrayList<>();
//        }
//        Objects.requireNonNull(clz);
//        Objects.requireNonNull(map);
//        if (startRowIndex.length > 1) {
//            throw new RuntimeException("可变参数长度不能大于1");
//        }
//        int start = 0;
//        if (startRowIndex.length == 1) {
//            start = startRowIndex[0];
//        }
//        Field[] fields = clz.getDeclaredFields();
//        Method[] methods = clz.getDeclaredMethods();
//        List<ColumnBean> columnBeanList = new ArrayList<>(map.size());
//        map.forEach((k, v) -> {
//            Field field = Iterables.findFirst(Arrays.asList(fields), each -> each.getName().equalsIgnoreCase(k));
//            if (field != null) {
//                ColumnBean columnBean = ColumnBean.builder().type(field.getType().getSimpleName()).columnIndex(v).build();
//                Method method = Iterables.findFirst(Arrays.asList(methods), each -> ("set" + StringUtils.capitalize(k.toLowerCase())).equals(each.getName()));
//                if (method != null) {
//                    columnBean.setMethod(method);
//                    columnBeanList.add(columnBean);
//                }
//            }
//        });
//        List<T> result = new ArrayList<>(sheet.getLastRowNum());
//
//        for (int i = start; i < sheet.getLastRowNum(); i++) {
//            result.add(row2Obj(sheet.getRow(i), clz, columnBeanList));
//        }
//        return result;
//    }
//
//    /**
//     * sheet转List对象,按照字段顺序对应sheet的列顺序
//     *
//     * @param sheet         sheet
//     * @param clz           对象类型
//     * @param startRowIndex sheet的起始行
//     * @param <T>           T
//     * @return List对象
//     */
//    public <T> List<T> sheet2List(Sheet sheet, Class<T> clz, int... startRowIndex) {
//        if (sheet == null) {
//            return new ArrayList<>();
//        }
//        Objects.requireNonNull(clz);
//        if (startRowIndex.length > 1) {
//            throw new RuntimeException("可变参数长度不能大于1");
//        }
//        int start = 0;
//        if (startRowIndex.length == 1) {
//            start = startRowIndex[0];
//        }
//        Field[] fields = clz.getDeclaredFields();
//        Method[] methods = clz.getDeclaredMethods();
//        List<ColumnBean> columnBeanList = new ArrayList<>(fields.length);
//        for (int i = 0; i < fields.length; i++) {
//            int finalI = i;
//            Method method = Iterables.findFirst(Arrays.asList(methods), each -> ("set" + StringUtils.capitalize(fields[finalI].getName())).equals(each.getName()));
//            if (method != null) {
//                columnBeanList.add(ColumnBean.builder().type(fields[i].getType().getSimpleName()).columnIndex(i).method(method).build());
//            }
//        }
//        List<T> result = new ArrayList<>(sheet.getLastRowNum());
//
//        for (int i = start; i < sheet.getLastRowNum(); i++) {
//            result.add(row2Obj(sheet.getRow(i), clz, columnBeanList));
//        }
//        return result;
//    }
//
//    /**
//     * row转对象
//     *
//     * @param row  row
//     * @param clz  对象类型
//     * @param list 对照
//     * @param <T>  T
//     * @return 对象
//     */
//    public <T> T row2Obj(Row row, Class<T> clz, List<ColumnBean> list) {
//        try {
//            T obj = clz.newInstance();
//            if (row == null || list == null || list.size() == 0) {
//                return obj;
//            }
//            list.forEach(each -> setValue(obj, each.getType(), each.getMethod(), getCellValue(row.getCell(each.getColumnIndex()))));
//            return obj;
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 为对象设值
//     *
//     * @param obj       对象
//     * @param paramType set方法的参数类型
//     * @param method    set方法
//     * @param value     参数
//     * @param <T>       T
//     */
//    public <T> void setValue(T obj, String paramType, Method method, String value) {
//        if (obj == null || method == null) {
//            return;
//        }
//        try {
//            switch (paramType) {
//                case "Integer":
//                    method.invoke(obj, ((Double) Double.parseDouble(value)).intValue());
//                    break;
//                case "Long":
//                    method.invoke(obj, Long.parseLong(value));
//                    break;
//                case "Double":
//                    method.invoke(obj, Double.parseDouble(value));
//                    break;
//                case "Boolean":
//                    method.invoke(obj, Boolean.parseBoolean(value));
//                    break;
//                case "Date":
//                    method.invoke(obj, string2DateFunction.apply(value));
//                    break;
//                default:
//                    method.invoke(obj, value);
//            }
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 关闭流
//     *
//     * @param outputStream 输出流
//     */
//    public void destroy(FileOutputStream outputStream) {
//        try {
//            if (outputStream != null) {
//                outputStream.close();
//                outputStream = null;
//            }
//            if (this.sXSSFWorkbook != null) {
//                this.sXSSFWorkbook.close();
//                this.sXSSFWorkbook = null;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 将sheet中内容写入磁盘
//     *
//     * @param sheet sheet
//     */
//    public void actionSheet(SXSSFSheet sheet) {
//        try {
//            sheet.flushRows();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
