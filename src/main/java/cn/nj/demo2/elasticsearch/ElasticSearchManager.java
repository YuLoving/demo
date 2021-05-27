package cn.nj.demo2.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import javax.swing.text.Highlighter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ZTY
 * @classname ElasticSearchManager
 * @description ES的操作类
 * @date 2020/12/1017:52
 */
@Component
public class ElasticSearchManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    //******************************************************************************索引相关操作*****************************************************************************************************

    /**
     * 创建索引
     *
     * @param indexName 索引名
     */
    public void createIndex(String indexName) {
        try {
            logger.info("创建名字为={}的索引", indexName);
            //1.创建索引的新增请求
            CreateIndexRequest indexRequest = new CreateIndexRequest(indexName);
            //2.客户端执行请求，请求后获得响应
            CreateIndexResponse response = restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
            logger.info("创建索引成功,返回客户端响应值={}", JSON.toJSONString(response));

        } catch (Exception e) {
            logger.error("创建索引异常信息:{}", e.getMessage());
            throw new RuntimeException("创建索引失败");
        }
    }

    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名
     * @return true 存在    false 不存在
     */
    public boolean indexExist(String indexName) {
        boolean exists = false;
        try {
            //1.创建索引的查询请求
            GetIndexRequest request = new GetIndexRequest(indexName);
            //2.客户端执行请求,判断是否存在该索引
            exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
            logger.info("该名为:{}的索引是否存在:{}", indexName, exists);
        } catch (IOException e) {
            logger.error("异常信息:{}", e.getMessage());
            throw new RuntimeException("判断索引是否存在出现异常");
        }
        return exists;
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名
     */
    public void deleteIndex(String indexName) {
        try {
            logger.info("删除名字为={}的索引", indexName);
            //1.创建索引的删除请求
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            //2.客户端执行该请求
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            logger.info("删除索引成功,客户端返回值={}", JSON.toJSONString(delete));
        } catch (Exception e) {
            logger.error("删除索引失败的异常信息:{}", e.getMessage());
            throw new RuntimeException("删除索引失败");
        }
    }

    //******************************************************************************文档相关操作*****************************************************************************************************

    /**
     * 添加文档
     * @param indexName  索引名
     * @param document  文档对象
     */
    public void addDocument(String indexName, MyEsUser document) {
        try {
            logger.info("索引:{} 添加文档内容:{}",indexName,document);
            IndexRequest request = new IndexRequest(indexName);
            request.id(document.getId());
            //设置超时时间
            request.timeout("3s");
            //将数据放到json字符串
            request.source(JSON.toJSONString(document), XContentType.JSON);
            //发送请求
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            logger.info("添加文档成功,客户端返回值:{}",JSON.toJSONString(response));
        }catch (Exception e){
            logger.error("添加文档出现异常:"+e.getMessage());
            throw  new RuntimeException("索引为:"+indexName+"添加文档失败!!!");
        }
    }

    /**
     * 判断文档是否存在
     * @param indexName  索引名
     * @param documentId  文档id
     * @return  true  存在   false  不存在
     */
    public boolean existDocument(String indexName,String documentId){
        boolean exists=false;
        try {
            GetRequest request = new GetRequest(indexName);
            request.id(documentId);
             exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
            logger.info("该索引:{} 是否存在文档ID为{}的文档:{}",indexName,documentId,exists);
        }catch (Exception e){
            logger.error("查询文档是否存在出现异常:"+e.getMessage());
            throw  new RuntimeException("查询文档是否存在失败");
        }
        return exists;
    }

    /**
     * 查询文档
     * @param indexName   索引名
     * @param documentId  文档id
     * @return  文档内容
     */
    public String getDocument(String indexName,String documentId){
        try {
            GetRequest request = new GetRequest(indexName);
            request.id(documentId);
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            logger.info("获取文档的完整内容:"+JSON.toJSONString(response));
            logger.info("获取文档的Source内容:"+response.getSourceAsString());
            return response.getSourceAsString();
        }catch (Exception e){
            logger.error("获取文档内容异常:"+e.getMessage());
            throw  new RuntimeException("查询文档失败");
        }
    }

    /**
     * 修改文档
     * @param indexName  索引名
     * @param document  文档
     */
    public void  updateDocument(String indexName,MyEsUser document){
        try {
            //修改请求
            UpdateRequest request = new UpdateRequest(indexName,document.getId());
            request.timeout("3s");
            request.doc(JSON.toJSONString(document),XContentType.JSON);
            UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            logger.info("该索引:{} 修改文档成功，客户端响应值:{}",indexName,update);
        }catch (Exception e){
            logger.error("修改文档异常:"+e.getMessage());
            throw  new RuntimeException("修改文档失败");
        }
    }


    /**
     * 删除文档
     * @param indexName    索引名
     * @param documentId  文档id
     */
    public void deleteDocument(String indexName,String documentId){
        try {
            DeleteRequest request = new DeleteRequest(indexName,documentId);
            request.timeout("3s");
            DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            logger.info("该索引:{} 删除文档成功，客户端响应值:{}",indexName,delete);
        }catch (Exception e){
            logger.error("删除文档异常:"+e.getMessage());
            throw  new RuntimeException("删除文档失败");
        }
    }



    /**
     * 批量添加文档
     * @param indexName  索引名
     * @param list  文档对象集合
     * @return  true  成功     false  失败
     */
    public boolean batchAddDocument(String indexName, List<MyEsUser> list){
        boolean flag=false;
        try {
            //创建批量的请求
            BulkRequest request = new BulkRequest();
            request.timeout("3s");
            //批量处理请求
            list.forEach(a->{
                IndexRequest source = new IndexRequest(indexName).id(a.getId()).source(JSON.toJSONString(a), XContentType.JSON);
                request.add(source);
            });
            BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            logger.info("批量添加文档客户端返回值:{}",JSON.toJSONString(bulk));
            logger.info("批量添加文档客户端返回值是否是失败的:{}",bulk.hasFailures());
            if(!bulk.hasFailures()){
                //如果批量操作成功  bulk.hasFailures()返回的是false
                flag=true;
            }
        }catch (Exception e){
            logger.error("批量添加文档出现异常:"+e.getMessage());
            throw  new RuntimeException("索引为:"+indexName+"批量添加文档失败!!!");
        }
        return flag;
    }

    /**
     * 批量修改文档
     * @param indexName   索引名
     * @param list  文档集合
     * @return true  成功     false 失败
     */
    public  boolean batchUpdateDocument(String indexName, List<MyEsUser> list){
        boolean flag=false;
        try {
            BulkRequest request = new BulkRequest();
            request.timeout("3s");
            list.forEach(a->{
                UpdateRequest updateRequest = new UpdateRequest(indexName, a.getId()).doc(JSON.toJSONString(a),XContentType.JSON);
                request.add(updateRequest);
            });
            BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            logger.info("批量修改文档客户端返回值:{}",JSON.toJSONString(bulk));
            logger.info("批量修改文档客户端返回值是否是失败的:{}",bulk.hasFailures());
            if(!bulk.hasFailures()){
                //如果批量操作成功  bulk.hasFailures()返回的是false
                flag=true;
            }
        }catch (Exception e){
            logger.error("批量修改文档出现异常:"+e.getMessage());
            throw  new RuntimeException("索引为:"+indexName+"批量批量修改文档失败!!!");
        }
        return flag;
    }

    /**
     * 批量修改文档
     * @param indexName   索引名
     * @param ids  文档ID集合
     * @return  true 成功    false  失败
     */
    public  boolean batchDeleteDocument(String indexName, List<String> ids){
        boolean flag=false;
        try {
            BulkRequest request = new BulkRequest();
            request.timeout("3s");
            ids.forEach(a->{
                DeleteRequest deleteRequest = new DeleteRequest(indexName, a);
                request.add(deleteRequest);
            });
            BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            logger.info("批量修改文档客户端返回值:{}",JSON.toJSONString(bulk));
            logger.info("批量修改文档客户端返回值是否是失败的:{}",bulk.hasFailures());
            if(!bulk.hasFailures()){
                flag=true;
            }

        }catch (Exception e){
            logger.error("批量修改文档出现异常:"+e.getMessage());
            throw  new RuntimeException("索引为:"+indexName+"批量批量修改文档失败!!!");
        }
        return flag;
    }


 //*********************************************************************复杂查询操作*************************************************************************************************

    //SearchRequest  搜索请求
    //SearchSourceBuilder  条件构造
    //HighlightBuilder   构建高亮
    //TermQueryBuilder   精确查询
    //MatchAllQueryBuilder
    //xxx QueryBuilder  对应查询的指令

    /**
     * 搜索查询
     * @param indexName  索引名
     * @param queryParam   查询的指定参数
     * @param paramValue   查询的指定参数的值
     * @param flag  是否需要高亮查询
     */
    public  List< Map<String, Object>>  SearchDocument(String indexName,String queryParam,String paramValue,boolean flag,int pageNo,int pageSize){
        try {

            //创建搜索请求
            SearchRequest searchRequest = new SearchRequest(indexName);
            //构建条件构造
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

             // 分页
            sourceBuilder.from(pageNo);
            sourceBuilder.size(pageSize);

            //设置高亮
            if(flag){
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                highlightBuilder.field(queryParam);
                // 关闭多个高亮显示
                highlightBuilder.requireFieldMatch(false);
                highlightBuilder.preTags("<span style='color:red'>");
                highlightBuilder.postTags("</span>");
                sourceBuilder.highlighter(highlightBuilder);
            }

            //条件 name为a的
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(queryParam, paramValue);
            sourceBuilder.query(termQueryBuilder);
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            searchRequest.source(sourceBuilder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            logger.info("查询结果的完整响应值:{}",JSON.toJSONString(response));
            logger.info("查询结果的文档内容:{}",JSON.toJSONString(response.getHits()));
            //解析结果
            List< Map<String, Object>> list = Lists.newArrayList();
            for (SearchHit documentFields : response.getHits().getHits()) {
                System.out.println("测试查询文档--遍历参数--"+documentFields.getSourceAsMap());
                // 解析高亮的字段
                Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
                HighlightField highlightField = highlightFields.get(queryParam);
                // 原来的结果
                Map<String, Object> map = documentFields.getSourceAsMap();
                // 回填高亮的字段
                if(null !=highlightField){
                    // 将高亮的字段替换成原来没有高亮的字段
                    Text[] fragments = highlightField.fragments();
                    String newfield="";
                    for (Text text :fragments){
                        newfield += text;
                    }
                    map.put(queryParam,newfield);
                }
                list.add(map);
            }
            return list;
        }catch (Exception e){
            logger.error("搜索请求出现异常:"+e.getMessage());
            throw  new RuntimeException("搜索请求");
        }

    }
















}
