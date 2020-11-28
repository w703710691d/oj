package com.swustacm.poweroj.contest.entity;

import lombok.Data;

@Data
public class Contests {
    /**
     * 题目id
     */
    private Integer cid;

    /**
     题目title
     */
    private String  title;
    /**
     * 状态:
     *
     */
    private Integer type;
}
