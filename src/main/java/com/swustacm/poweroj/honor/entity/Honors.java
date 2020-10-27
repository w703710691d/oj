package com.swustacm.poweroj.honor.entity;

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
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Honors implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("level")
    private String level;

    @TableField("contest")
    private String contest;

    @TableField("team")
    private String team;

    @TableField("player")
    private String player;

    @TableField("prize")
    private String prize;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


}
