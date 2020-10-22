package com.swustacm.poweroj.solution.entity;

import com.swustacm.poweroj.params.PageParam;
import lombok.Data;

/**
 * 查询提交记录时使用的自定义参数
 */
@Data
public class ShowSolutionParam extends PageParam {

    /**
     * result 提交结果
     */
    private String result;

    /**
     * language 代码语言
     */
    private String language;

    /**
     * pid 提交的题目
     */
    private String pid;

    /**
     * userName 提交的用户名（只能使用学号查询）
     */
    private String userName;
}
