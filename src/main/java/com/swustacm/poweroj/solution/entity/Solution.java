package com.swustacm.poweroj.solution.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Solution implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 题目提交id
     */
    @TableId(value = "sid", type = IdType.AUTO)
    private Integer sid;

    /**
     * 提交者id
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 题目的id
     */
    @TableField("pid")
    private Integer pid;

    @TableField("cid")
    private Integer cid;

    @TableField("num")
    private Integer num;

    /**
     * 题目代码运行时间
     */
    @TableField("time")
    private Integer time;

    /**
     *代码运行时占用内存
     */
    @TableField("memory")
    private Integer memory;

    /**
     * 运行结果
     */
    @TableField("result")
    private Integer result;

    /**
     * 代码语言
     */
    @TableField("language")
    private Integer language;

    @TableField("ctime")
    private Integer ctime;

    @TableField("mtime")
    private Integer mtime;

    @TableField("test")
    private Integer test;

    @TableField("wrong")
    private String wrong;

    /**
     * 错误的类型
     */
    @TableField("error")
    private String error;

    /**
     * 代码
     */
    @TableField("source")
    private String source;

    /**
     * 代码长度
     */
    @TableField("codeLen")
    private Integer codeLen;

    @TableField("systemError")
    private String systemError;

    @TableField("status")
    private Boolean status;

}
