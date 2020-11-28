package com.swustacm.poweroj.topic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Topic implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("uid")
    private Integer uid;

    @TableField("pid")
    private Integer pid;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("atime")
    private Integer atime;

    @TableField("ctime")
    private Integer ctime;

    @TableField("mtime")
    private Integer mtime;

    @TableField("status")
    private Boolean status;

    @TableField("view")
    private Integer view;

    @TableField("threadId")
    private Integer threadId = 0;

    @TableField("parentId")
    private Integer parentId;

    @TableField("orderNum")
    private Integer orderNum;

    @TableField(exist = false)
    private String formedTime;

    /**
     * 回复人或发表人的用户名
     */
    @TableField(exist = false)
    private String name;
}
