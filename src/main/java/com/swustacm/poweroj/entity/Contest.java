package com.swustacm.poweroj.entity;

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
 * @author xingzi
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Contest implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    @TableField("uid")
    private Integer uid;

    @TableField("title")
    private String title;

    @TableField("startTime")
    private Integer startTime;

    @TableField("endTime")
    private Integer endTime;

    @TableField("description")
    private String description;

    @TableField("report")
    private String report;

    @TableField("type")
    private Integer type;

    @TableField("password")
    private String password;

    @TableField("languages")
    private String languages;

    @TableField("lockBoard")
    private Boolean lockBoard;

    @TableField("atime")
    private Integer atime;

    @TableField("ctime")
    private Integer ctime;

    @TableField("mtime")
    private Integer mtime;

    @TableField("status")
    private Boolean status;

    @TableField("lockReport")
    private Boolean lockReport;

    @TableField("lockBoardTime")
    private Integer lockBoardTime;

    @TableField("unlockBoardTime")
    private Integer unlockBoardTime;


}
