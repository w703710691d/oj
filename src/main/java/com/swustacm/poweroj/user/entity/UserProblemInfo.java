package com.swustacm.poweroj.user.entity;

import lombok.Data;

import java.util.List;

/**
 * 用于返回用户的答题信息
 * @author zcy
 */
@Data
public class UserProblemInfo {
    /**
     * 用户信息
     */
    private User user;
    /**
     * 排名
     */
    private Integer rank;

    /**
     * 提交问题的状态
     */
    private List<ProblemList> submittedProblem;
    /**
     * 题目状态
     */
    @Data
    public class ProblemList{
       /**
       题目title
        */
        private String  title;
        /**
         * 题目id
         */
        private Integer pid;
        /**
         * 状态:   0:已解决    result >0 ：未解决
         *
         */
        private Integer result;

    }
    /**
     * 参加比竞赛的问题
     */
    private List<Contests> attendedContests;
    /**
     * 题目状态
     */
//    @Data
//    public class ContestsList{
//
//        /**
//         * 题目id
//         */
//        private Integer cid;
//
//        /**
//         题目title
//         */
//        private String  title;
//        /**
//         * 状态:
//         *
//         */
//        private Integer type;
//
//    }
}
