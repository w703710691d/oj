package com.swustacm.poweroj.problem.entity;


import lombok.Data;

@Data
public class ProblemStatus {
    /*
    提交次数
     */
    public Long count;
    /*
    解决状态
     */
    public int result;
    /*
    状态名简写
     */
    public String name;
    /*
    状态名全写
     */
    public String longName;

}
