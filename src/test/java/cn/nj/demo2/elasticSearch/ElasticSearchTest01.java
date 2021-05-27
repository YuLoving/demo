package cn.nj.demo2.elasticSearch;

import cn.nj.demo2.Demo2Application;
import cn.nj.demo2.elasticsearch.ElasticSearchManager;
import cn.nj.demo2.elasticsearch.MyEsUser;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ZTY
 * @classname ElasticSearchTest01
 * @description ES单元测试
 * @date 2020/12/1510:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Demo2Application.class)
public class ElasticSearchTest01 {

    @Autowired
    private ElasticSearchManager elasticSearchManager;

    /**
     * 创建索引
     */
    @Test
    public void createIndex(){
        elasticSearchManager.createIndex("people");
    }

    /**
     * 判断索引是否存在
     */
    @Test
    public void ExistIndex(){
        boolean exist = elasticSearchManager.indexExist("people");
        System.err.println("是否存在:"+exist);
    }

    /**
     * 删除索引
     */
    @Test
    public void DeleteIndex(){
       elasticSearchManager.deleteIndex("people");
    }

    /**
     * 添加文档
     */
    @Test
    public  void createDocument(){
        MyEsUser myEsUser = new MyEsUser();
        myEsUser.setId("10");
        myEsUser.setName("华昊");
        myEsUser.setAge(25);
        elasticSearchManager.addDocument("people",myEsUser);
    }


    /**
     * 修改文档
     */
    @Test
    public  void updateDocument(){
        MyEsUser myEsUser = new MyEsUser();
        myEsUser.setId("1");
        myEsUser.setName("华昊");
        myEsUser.setAge(18);
        elasticSearchManager.updateDocument("people",myEsUser);
    }


    /**
     * 删除文档
     */
    @Test
    public  void deleteDocument(){
        elasticSearchManager.deleteDocument("people","1");
    }

    /**
     * 批量添加文档
     */
    @Test
    public void batchAddDocument(){
        List<MyEsUser> list = Lists.newArrayList();
        for (int i = 11; i <=15; i++) {
            MyEsUser myEsUser = new MyEsUser();
            myEsUser.setId(i+"");
            myEsUser.setName(i+"号");
            myEsUser.setAge(25);
            list.add(myEsUser);
        }
        boolean result = elasticSearchManager.batchAddDocument("people", list);
        System.err.println("批量添加文档结果:"+result);
    }


    /**
     * 批量更新文档
     */
    @Test
    public void batchUpdateDocument(){
        List<MyEsUser> list = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            MyEsUser myEsUser = new MyEsUser();
            myEsUser.setId(i+"");
            myEsUser.setName(i+"号测试更新");
            myEsUser.setAge(10+i);
            list.add(myEsUser);
        }
        boolean result = elasticSearchManager.batchUpdateDocument("people", list);
        System.err.println("批量更新文档结果:"+result);
    }


    /**
     * 批量删除文档
     */
    @Test
    public void batchDeleteDocument(){
        List<String> ids = Arrays.asList("0", "1", "2");
        boolean result = elasticSearchManager.batchDeleteDocument("people", ids);
        System.err.println("批量更新文档结果:"+result);
    }

    /**
     * 查询文档
     */
    @Test
    public void get(){
       elasticSearchManager.getDocument("people", "10");
    }

    /**
     * 复杂查询
     */
    @Test
    public void  search(){
        List<Map<String, Object>> list = elasticSearchManager.SearchDocument("people", "age", "25", true, 1, 10);
        System.out.println(list);
    }













}
