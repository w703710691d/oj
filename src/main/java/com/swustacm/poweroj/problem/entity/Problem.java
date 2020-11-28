package com.swustacm.poweroj.problem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张成云
 * @since 2020-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Problem extends Model<Problem> {

    private static final long serialVersionUID=1L;
    /*
        题目id
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;
    /*
    用户id
     */
    @TableField("uid")
    private Integer uid;
    /*
    问题标题
     */
    @TableField("title")
    private String title;
    /*
    问题描述
     */
    @TableField("description")
    private String description;
    /*
    输入
     */
    @TableField("input")
    private String input;
    /*
    输出
     */
    @TableField("output")
    private String output;

    @TableField("sampleInput")
    private String sampleInput;

    @TableField("sampleOutput")
    private String sampleOutput;

    @TableField("hint")
    private String hint;
    /*
    来源
     */
    @TableField("source")
    private String source;

    @TableField("sampleProgram")
    private String sampleProgram;
    /*
    时间限制
     */
    @TableField("timeLimit")
    private Integer timeLimit;
    /*
    时间限制
     */
    @TableField("memoryLimit")
    private Integer memoryLimit;

    @TableField("atime")
    private Integer atime;

    @TableField("ctime")
    private String ctime;

    @TableField("mtime")
    private Integer mtime;

    @TableField("stime")
    private Integer stime;

    @TableField("accepted")
    private Integer accepted;

    @TableField("solved")
    private Integer solved;

    @TableField("submission")
    private Integer submission;

    @TableField("submitUser")
    private Integer submitUser;

    @TableField("error")
    private Integer error;

    @TableField("ratio")
    private Integer ratio;

    @TableField("difficulty")
    private Integer difficulty;

    @TableField("view")
    private Integer view;
    /*
    问题状态 ：1 为所有用户可见；0：为管理员权限可见
     */
    @TableField("status")
    private Boolean status;

    @TableField(exist = false)
    private int sampleInputRows;

    @TableField(exist = false)
    private int sampleOutputRows;
}
