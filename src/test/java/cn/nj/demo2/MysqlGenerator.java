package cn.nj.demo2;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author ZTY
 * @classname MysqlGenerator
 * @description mybatis-plus自动生成代码
 * @date 2020/10/2015:54
 */
public class MysqlGenerator {

    public static void main(String[] args) {
        //全局配置
        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        //得到当前项目的路径
        String projectPath = System.getProperty("user.dir");
        //生成文件输出根目录
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("zty");   // 作者
        gc.setOpen(false);      //生成完成后不弹出文件框
        //文件覆盖
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
       // gc.setSwagger2(true);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setEntityName("%sBo");

        autoGenerator.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        autoGenerator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //父包路径
        pc.setParent("cn.nj.demo2");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("pojo");
        pc.setXml("mapper");
        autoGenerator.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(new String[]{"oper_log","oper_exc"});
//        strategy.setSuperServiceClass(null);
//        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);
        strategy.setEntityLombokModel(true);


        //去除表前缀
        //strategy.setTablePrefix("oper_");
        //去除字段前缀
        strategy.setFieldPrefix("");
        autoGenerator.setStrategy(strategy);

        // 执行生成
        autoGenerator.execute();


    }
}


