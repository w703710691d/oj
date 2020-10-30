package com.swustacm.poweroj.news.entity;

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
 * @since 2020-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class News implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 新闻id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 新闻标题
     */
    @TableField("title")
    private String title;

    /**
     * 新闻文本内容
     */
    @TableField("content")
    private String content;

    /**
     * 新闻发布时间
     */
    @TableField("time")
    private Integer time;

    /**
     * 新闻作者
     */
    @TableField("author")
    private String author;

    /**
     * 新闻图片路径
     */
    @TableField("image")
    private String image;

    /**
     * 新闻观看次数
     */
    @TableField("view")
    private Integer view;
}
