package com.swustacm.poweroj.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role extends Model<Role> {
    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("status")
    private Boolean status;
}
