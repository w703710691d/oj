package com.swustacm.poweroj.problem.entity;

import com.swustacm.poweroj.params.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Problem页面参数
 * @author zcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemSearchParam extends PageParam {
        /*
        题目id
         */
        private String pid;
        /*
        题目标题
         */
        private String title;
        /*
        题目分类
         */
        private String source;
}
