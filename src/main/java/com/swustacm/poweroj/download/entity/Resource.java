package com.swustacm.poweroj.download.entity;

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
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Resource implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("uid")
    private Integer uid;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("os")
    private String os;

    @TableField("arch")
    private String arch;

    @TableField("path")
    private String path;

    @TableField("ctime")
    private Integer ctime;

    @TableField("download")
    private Integer download;

    @TableField("access")
    private String access;


}
