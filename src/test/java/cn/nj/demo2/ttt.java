package cn.nj.demo2;

import com.google.common.base.Joiner;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZTY
 * @classname ttt
 * @description TODO
 * @date 2020/11/1117:58
 */
public class ttt {

    public static void main(String[] args) {
        List<pojo> list = new ArrayList<>();
        pojo pojo0 = new pojo();
        pojo0.setImei("");
        pojo0.setSn("SD12DS");
        pojo pojo1 = new pojo();
        pojo1.setImei("3123213");
        pojo1.setSn("");
        pojo pojo2 = new pojo();
        pojo2.setImei("");
        pojo2.setSn("");
        pojo pojo3 = new pojo();
        pojo3.setImei("45345345345");
        pojo3.setSn("SD12DSSDSD");
        pojo pojo4 = new pojo();
        pojo4.setImei("343434");
        pojo4.setSn("KK2212");
        list.add(pojo0);
        list.add(pojo1);
        list.add(pojo2);
        list.add(pojo3);
        list.add(pojo4);
        System.out.println(list);
        List<String> imeilist = list.stream()
                .map(pojo::getImei)
                .filter(StringUtils::isNotBlank)
                .distinct().collect(Collectors.toList());
        System.out.println(imeilist);
        List<String> snlist = list.stream().map(pojo::getSn).filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
        System.out.println(snlist);
        imeilist.addAll(snlist);
        String join = Joiner.on(",").join(imeilist);
        System.out.println(join);
        Map<String, String> map = list.stream().filter(a->StringUtils.isNotBlank(a.getImei())).collect(Collectors.toMap(pojo::getImei, pojo -> "adasd"));
        System.out.println(map);
    }


}
