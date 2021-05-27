package cn.nj.demo2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author ZTY
 * @classname testlog
 * @description TODO
 * @date 2020/11/1017:07
 */
public class testlog2 {

    //模糊匹配日期格式：yyyy-MM-dd HH:mm:ss
    private final  static   String timeRegex = "^.*[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]).*$";

    private final  static  String LEFT="[";

    private final  static  String RIGHT="]";

    //读取日志的文件的地址
    private final static String txtpath="C:\\Users\\15895813957\\Downloads\\drp-project-service-store-pos+all+7841870001.txt";
    //生成excel的地址
    private final static String excelPath="C:\\Users\\15895813957\\Desktop\\LOGEXCEL\\mylog.xlsx";

    public static void main(String[] args) {
        File file = new File(txtpath);
        if(FileUtil.exist(excelPath)){
            FileUtil.del(excelPath);
        }
        //创建 XSSFWorkbook对象(excel的文档对象)
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        // 建立新的sheet对象（excel的表单）

        SXSSFSheet sheet = workbook.createSheet("一周的异常日志统计");
        // 在sheet里创建第一行，参数为行索引(excel的行)
        //第一行
        SXSSFRow row0 = sheet.createRow(0);
        // 设置行高
        row0.setHeight((short) 400);
        //第一行标题
        SXSSFCell cell = row0.createCell(0);
        cell.setCellValue("一周的异常日志统计");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cell.setCellStyle(cellStyle);
        //第2行添加表头的列表
        SXSSFRow row1 = sheet.createRow(1);
        // 添加表头
        row1.createCell(0).setCellValue("具体日期");
        row1.createCell(1).setCellValue("调用链ID");
        row1.createCell(2).setCellValue("异常信息");
        //BigExcelWriter bigWriter = ExcelUtil.getBigWriter(excelPath);
        //跳过当前行，既第一行，非必须
       // bigWriter.passCurrentRow();
        try {
            String s=null;
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            //List<String> row0 = CollUtil.newArrayList("具体日期", "调用链ID", "异常信息");
            //List<List<String>> rows = CollUtil.newArrayList();
            //rows.add(row0);
            AtomicInteger num = new AtomicInteger(1);
            while (null !=(s=bufferedReader.readLine())){
               // List<String> row= CollUtil.newArrayList();
                //匹配到包含日期格式：yyyy-MM-dd HH:mm:ss的那一行,塞到EXCEL中
                if(Pattern.matches(timeRegex,s)){
                  //获取第1个“]”的位置
                    int a = s.indexOf(RIGHT);
                    //获取第1个"["的位置
                    int b = s.indexOf(LEFT);
                    //获取第2个“[”的位置
                    int c = s.indexOf(LEFT, b + 1);
                    //截取日期
                    String s1 = s.substring(a+1, c);

                   // row.add(s1);
                    //获取第2个“]”的位置
                    int d = s.indexOf(RIGHT, a + 1);
                    String s2 = s.substring(c+1, d);
                   // row.add(s2);
                    String s3 = s.substring(a+1);
                    //row.add(s3);
                    //rows.add(row);
                    //创建新行
                    SXSSFRow newrow = sheet.createRow(num.incrementAndGet());//数据从第三行开始
                    newrow.createCell(0).setCellValue(s1);
                    newrow.createCell(1).setCellValue(s2);
                    newrow.createCell(2).setCellValue(s3);
                }
            }


            //设置第一、二的列宽
            sheet.setColumnWidth(0,9000);
            sheet.setColumnWidth(1,9000);

            //合并单元格，参数依次为起始行，结束行，起始列，结束列 （索引0开始）
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 12);
            sheet.addMergedRegion(cellRangeAddress);

            //合并单元格后的标题行，使用默认标题样式
            //bigWriter.merge(row0.size()-1,"一周的异常日志统计");
            //一次性写出内容，强制输出标题
            //bigWriter.write(rows, true);
            //关闭writer，释放内存
            //输出Excel文件
            FileOutputStream  outputStream= new FileOutputStream(excelPath);
            workbook.write(outputStream);
            outputStream.close();
            //bigWriter.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
