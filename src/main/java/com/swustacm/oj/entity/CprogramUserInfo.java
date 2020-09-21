package com.swustacm.oj.entity;

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
 * @author lixiaobo
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CprogramUserInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    @TableField("classes")
    private String classes;

    @TableField("stuid")
    private String stuid;

    @TableField("tid")
    private Integer tid;

    @TableField("class_week")
    private Integer classWeek;

    @TableField("class_lecture")
    private Integer classLecture;

    @TableField("ctime")
    private Integer ctime;


}
