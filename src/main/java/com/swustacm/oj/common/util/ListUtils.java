package com.swustacm.oj.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List相关工具类
 * @author xingzi
 */
public class ListUtils<T> {
    /**
     * 判断name 是否在names中
     *
     * @param names name集合
     * @param name  name
     * @return
     */
    public static boolean exist(String[] names, String name) {
        for (String s : names) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }
    public  static <T> boolean isEmpty(List<T> list){
        return list == null || list.isEmpty();
    }
}
