package com.swustacm.poweroj.solution.entity;

import com.swustacm.poweroj.params.PageParam;
import lombok.Data;

@Data
public class ShowSolutionParam extends PageParam {
    /**
     * result 提交结果
     * language 提交时使用的语言
     * pid 提交的题目
     * userName 提交的用户名（只能使用学号查询）
     */
    private String result;
    private String language;
    private String pid;
    private String userName;
}
