package com.swustacm.poweroj.notice.entity;

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
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Notice implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * notice id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 创建者uid
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 最近一次编辑的人的uid
     */
    @TableField("editorUid")
    private Integer editorUid;

    /**
     * 暂时 无用
     */
    @TableField("cid")
    private Integer cid;

    /**
     * notice标题
     */
    @TableField("title")
    private String title;

    /**
     * notice开始时间
     */
    @TableField("startTime")
    private Integer startTime;

    /**
     * notice截至时间
     */
    @TableField("endTime")
    private Integer endTime;

    /**
     * notice文本内容
     */
    @TableField("content")
    private String content;

    @TableField("atime")
    private Integer atime;

    /**
     * notice创建时间
     */
    @TableField("ctime")
    private Integer ctime;

    @TableField("mtime")
    private Integer mtime;

    /**
     * notice观看次数
     */
    @TableField("view")
    private Integer view;

    /**
     * notice状态
     */
    @TableField("status")
    private Boolean status;

    /**
     * notice发布者
     */
    @TableField(exist = false)
    private String publisher;


}
