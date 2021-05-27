package cn.nj.demo2;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：zty
 * @date ：Created in 2021/1/4 16:41
 * @description ：
 */
public class test05 {
    public static void main(String[] args) {

        Set<String> strings=new HashSet<>();
        strings.add("a");
        strings.add("b");
        strings.add("av");
        strings.add("c");
        strings.add("e");
        Object[] array = strings.toArray();
        System.out.println(JSON.toJSONString(array));
        String[] strings1 = strings.toArray(new String[0]);
        System.out.println(JSON.toJSONString(strings1));

    }
}
