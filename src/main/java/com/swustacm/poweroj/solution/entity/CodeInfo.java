package com.swustacm.poweroj.solution.entity;

import com.swustacm.poweroj.constant.ResultType;
import lombok.Data;

@Data
public class CodeInfo {
    /**
     * 提交代码
     */
    private String source;

    /**
     * 题目标题
     */
    private String problemTitle;

    /**
     * 代码运行耗时
     */
    private Integer time;

    /**
     *  代码运行结果
     */
    private ResultType result;

    /**
     * 代码语言
     */
    private Integer language;
}
