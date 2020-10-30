package com.swustacm.poweroj.problem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("input")
    private String input;

    @TableField("output")
    private String output;

    @TableField("sampleInput")
    private String sampleInput;

    @TableField("sampleOutput")
    private String sampleOutput;

    @TableField("hint")
    private String hint;

    @TableField("source")
    private String source;

    @TableField("sampleProgram")
    private String sampleProgram;

    @TableField("timeLimit")
    private Integer timeLimit;

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

    @TableField("status")
    private Boolean status;

    @TableField(exist = false)
    private int sampleInputRows;

    @TableField(exist = false)
    private int sampleOutputRows;
}
