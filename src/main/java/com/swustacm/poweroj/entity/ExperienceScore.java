package com.swustacm.poweroj.entity;

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
public class ExperienceScore implements Serializable {

    /**
     * 班级
     */
    String classes;

    /**
     * 学号
     */
    String name;

    /**
     * 真实姓名
     */
    String realName;

    /**
     * 第一次实验成绩
     */
    Integer ac1;

    /**
     * 第一次报告成绩
     */
    Integer rep1;

    /**
     * 第二次实验成绩
     */
    Integer ac2;

    /**
     * 第二次报告成绩
     */
    Integer rep2;

    /**
     * 第三次实验成绩
     */
    Integer ac3;

    /**
     * 第三次报告成绩
     */
    Integer rep3;

    /**
     * 第四次实验成绩
     */
    Integer ac4;

    /**
     * 第四次报告成绩
     */
    Integer rep4;

    /**
     * 第五次实验成绩
     */
    Integer ac5;

    /**
     * 第五次报告成绩
     */
    Integer rep5;

    /**
     * 第六次实验成绩
     */
    Integer ac6;

    /**
     * 第六次报告成绩
     */
    Integer rep6;

    /**
     * 第七次实验成绩
     */
    Integer ac7;

    /**
     * 第七次报告出成绩
     */
    Integer rep7;

    /**
     * 第八次实验成绩
     */
    Integer ac8;

    /**
     * 第八次报告成绩
     */
    Integer rep8;



}
