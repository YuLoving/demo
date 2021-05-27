package cn.nj.demo2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author ZTY
 * @classname testlog
 * @description TODO
 * @date 2020/11/1017:07
 */
public class testlog {

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
        //通过hutool工具类创建BigExcelWriter
        BigExcelWriter bigWriter = ExcelUtil.getBigWriter(excelPath);
        //跳过当前行，既第一行，非必须
        bigWriter.passCurrentRow();
        try {
            String s=null;
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            List<String> row0 = CollUtil.newArrayList("具体日期", "调用链ID", "异常信息");
            List<List<String>> rows = CollUtil.newArrayList();
            rows.add(row0);
            while (null !=(s=bufferedReader.readLine())){
                List<String> row= CollUtil.newArrayList();
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
                    row.add(s1);
                    //获取第2个“]”的位置
                    int d = s.indexOf(RIGHT, a + 1);
                    String s2 = s.substring(c+1, d);
                    row.add(s2);
                    String s3 = s.substring(a+1);
                    row.add(s3);
                    rows.add(row);
                }
            }

            //合并单元格后的标题行，使用默认标题样式
            bigWriter.merge(row0.size()-1,"一周的异常日志统计");
            //一次性写出内容，强制输出标题
            bigWriter.write(rows, true);
            //关闭writer，释放内存
            bigWriter.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
