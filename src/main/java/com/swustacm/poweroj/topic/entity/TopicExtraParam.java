package com.swustacm.poweroj.topic.entity;

import com.swustacm.poweroj.params.PageParam;
import lombok.Data;

/**
 * topic请求参数
 * @author lizhihao
 */

@Data
public class TopicExtraParam extends PageParam {
    /**
     * 题目id
     */
    Integer pid;
    /**
     * 同一个topic下threadID唯一
     */
    Integer threadId;
}
