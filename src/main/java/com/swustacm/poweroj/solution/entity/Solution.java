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

    @TableId(value = "sid", type = IdType.AUTO)
    private Integer sid;

    @TableField("uid")
    private Integer uid;

    @TableField("pid")
    private Integer pid;

    @TableField("cid")
    private Integer cid;

    @TableField("num")
    private Integer num;

    @TableField("time")
    private Integer time;

    @TableField("memory")
    private Integer memory;

    @TableField("result")
    private Integer result;

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

    @TableField("error")
    private String error;

    @TableField("source")
    private String source;

    @TableField("codeLen")
    private Integer codeLen;

    @TableField("systemError")
    private String systemError;

    @TableField("status")
    private Boolean status;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String nick;

    @TableField(exist = false)
    private String stime;

    @TableField(exist = false)
    private String userName;


}
