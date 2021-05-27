package cn.nj.demo2.yezi;

import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author ：zty
 * @date ：Created in 2021/2/22 17:51
 * @description ：
 */
public class YeZiCSCSignUtil {

    public static String getSign(String clientId,String timestamp,String nonce,String secret,Object requestBody){
        Map<String,String> tmap=new TreeMap<>();
        tmap.put("ClientId",clientId);
        tmap.put("Nonce",nonce);
        tmap.put("Timestamp",timestamp);
        StringBuilder sb=new StringBuilder(secret);
        tmap.entrySet().forEach(entry->{
            System.out.println(entry.getKey()+":"+entry.getValue());
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        });
        if (!Objects.isNull(requestBody)) {
            sb.append(requestBody);
        }
        sb.append(secret);
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes()).toUpperCase();
    }
}
