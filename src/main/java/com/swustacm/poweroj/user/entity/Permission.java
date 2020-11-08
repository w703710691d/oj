package com.swustacm.poweroj.user.entity;

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
 * @author 张成云
 * @since 2020-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;
    /*
        权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /*
    权限角色
     */
    @TableField("module")
    private String module;
    /*
    类型 默认为1
     */
    @TableField("type")
    private Integer type;
    /*
    权限名称
     */
    @TableField("name")
    private String name;
    /*
    权限描述
     */
    @TableField("title")
    private String title;

    @TableField("parentID")
    private Integer parentID;
    /*
    状态
     */
    @TableField("status")
    private Boolean status;


}
