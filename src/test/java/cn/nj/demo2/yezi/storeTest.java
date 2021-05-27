package cn.nj.demo2.yezi;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ：zty
 * @date ：Created in 2021/2/5 10:11
 * @description ：
 */
public class storeTest {


    private static final String url = "http://10.177.194.177:2102/tools/synchronize/rpc/sosStore/search";

    private static final String cangkurl = "http://open-api-test.wanyol.com/open/mdm/distributor/depotStore/search";
    private static final String tokenUrl= "http://open-api-test.wanyol.com/open/oauth/token?grant_type=client_credentials&scope=read";

    private static final String COLON = ":";


    public static void main(String[] args) {
        //查询门店
      /*  SosStore sosStore = new SosStore();
        //sosStore.setStoreCodeList(Collections.singletonList("fhcs"));
        sosStore.setDepotIdList(Collections.singletonList("104186"));
        //sosStore.setTenantCode();
        String result = HttpUtil.post(url, JSON.toJSONString(sosStore), 5000);
        System.out.println(result);*/

        //查询仓库
        DepotStore depotStore = new DepotStore();

        depotStore.setDepotIdList(Collections.singletonList("104186"));
        depotStore.setTenantCodeList(Collections.singletonList("JXOPPO"));
        depotStore.setPageNo(1);
        depotStore.setPageSize(10);
        //depotStore.setTenantCodeList();
        String result2 = HttpRequest.post(cangkurl)
                .header("Content-Type", "application/json")
                .header("Timestamp", "1613986186")
                .body(JSON.toJSONString(depotStore))
                .timeout(3000)
                .execute().body();


        System.out.println(result2);

    }

    
