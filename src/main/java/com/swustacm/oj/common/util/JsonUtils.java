package com.swustacm.oj.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xingzi
 * jackson json数据处理
 */

public class JsonUtils<T> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将字符串 转成json对象
     *
     * @param data 字符串
     * @param type 转换json对象类型
     * @return
     */
    public static <T> T toJson(String data, Class<T> type) {
        try {
            return objectMapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
