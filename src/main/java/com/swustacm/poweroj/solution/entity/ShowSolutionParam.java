package com.swustacm.poweroj.solution.entity;

import com.swustacm.poweroj.common.params.PageParam;
import lombok.Data;

/**
 * 查询提交记录时使用的自定义参数
 */
@Data
public class ShowSolutionParam extends PageParam {

    /**
     * result 提交结果
     */
    private Integer result;

    /**
     * language 代码语言
     */
    private Integer language;

    /**
     * pid 提交的题目
     */
    private Integer pid;

    /**
     * userName 提交的用户名
     */
    private String userName;
}
