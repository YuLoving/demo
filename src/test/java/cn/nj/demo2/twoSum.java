package cn.nj.demo2;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.velocity.runtime.directive.contrib.For;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZTY
 * @classname twoSum   大多人的算法入门的第一题。
 * @description 给定一个数组，给定一个数字。返回数组中可以相加得到指定数字的两个索引。
 * 比如：给定nums = [2, 7, 11, 15], target = 9
 * 那么要返回 [0, 1]，因为2 + 7 = 9
 * @date 2020/11/2517:25
 */
public class twoSum {

    public static void main(String[] args) {
        Map<Integer, Integer> map = Maps.newHashMap();
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        //方式一  双循环
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length - 1; j++) {
                if (target == nums[i] + nums[j]) {
                    int[] a = {i, j};
                    System.out.println("双循环："+Arrays.toString(a));
                }
            }
        }

        //方式二  单循环➕ map
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                System.out.println("单循环+map："+Arrays.toString(new int [] { map.get(complement), i }));
            }
            map.put(nums[i], i);
        }

    }


}
