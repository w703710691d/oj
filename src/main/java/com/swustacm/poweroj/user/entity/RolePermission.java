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
public class RolePermission extends Model<RolePermission> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("rid")
    private Integer rid;
    @TableField("pid")
    private Integer pid;
}
