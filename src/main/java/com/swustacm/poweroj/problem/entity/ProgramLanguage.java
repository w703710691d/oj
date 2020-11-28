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
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProgramLanguage extends Model<ProgramLanguage> {

    private static final long serialVersionUID=1L;
    public static final ProgramLanguage dao = new ProgramLanguage();
    /*
    程序语言id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /*
    程序语言名称
     */
    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("extTime")
    private Integer extTime;

    @TableField("extMemory")
    private Integer extMemory;

    @TableField("timeFactor")
    private Integer timeFactor;

    @TableField("memoryFactor")
    private Integer memoryFactor;

    @TableField("ext")
    private String ext;

    @TableField("exe")
    private String exe;

    @TableField("complieOrder")
    private Integer complieOrder;

    @TableField("compileCmd")
    private String compileCmd;

    /**
     * SyntaxHighlighter brush
     */
    @TableField("brush")
    private String brush;

    @TableField("script")
    private Boolean script;

    @TableField("status")
    private Boolean status;



}
