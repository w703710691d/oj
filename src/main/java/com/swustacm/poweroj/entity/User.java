package com.swustacm.poweroj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

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
public class User implements Serializable {

    /**
     * Unique user ID, internal use.
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private int uid;

    /**
     * refere team id.
     */
    @TableField("tid")
    private int tid;

    /**
     * unique user login name.
     */
    @NotEmpty(message = "用户名不能为空")
    @TableField("name")
    private String name;

    /**
     * User?s password (hashed).
     */
    @NotEmpty(message = "密码不能为空")
    @TableField("password")
    private String password;

    /**
     * nick
     */
    @TableField("nick")
    private String nick;

    @TableField("realName")
    private String realName;

    @TableField("regEmail")
    private String regEmail;

    /**
     * User’s e-mail address.
     */
    @TableField("email")
    private String email;

    @TableField("emailVerified")
    private Boolean emailVerified;

    @TableField("language")
    private Integer language;

    @TableField("school")
    private String school;

    /**
     * the number of problems user solved
     */
    @TableField("solved")
    private Integer solved;

    @TableField("accepted")
    private Integer accepted;

    /**
     * the number of user submit code
     */
    @TableField("submission")
    private Integer submission;

    /**
     * Timestamp for previous time user accessed the site.
     */
    @TableField("atime")
    private Integer atime;

    /**
     * Timestamp for when user was created.
     */
    @TableField("ctime")
    private Integer ctime;

    /**
     * Timestamp for when user edit its profile.
     */
    @TableField("mtime")
    private Integer mtime;

    /**
     * Timestamp for user last login.
     */
    @TableField("loginTime")
    private Integer loginTime;

    @TableField("loginIP")
    private String loginIP;

    @TableField("phone")
    private String phone;

    @TableField("qq")
    private String qq;

    @TableField("blog")
    private String blog;

    @TableField("gender")
    private String gender;

    @TableField("comeFrom")
    private String comeFrom;

    @TableField("online")
    private Integer online;

    @TableField("level")
    private Integer level;

    @TableField("credit")
    private Integer credit;

    @TableField("shareCode")
    private Boolean shareCode;

    /**
     * user avatar path
     */
    @TableField("avatar")
    private String avatar;

    @TableField("signature")
    private String signature;

    @TableField("checkin")
    private Integer checkin;

    @TableField("checkin_times")
    private Integer checkinTimes;

    /**
     * Whether the user is active(1) or blocked(0).
     */
    @TableField("status")
    private Integer status;

    @TableField("data")
    private Boolean data;

    @TableField("token")
    private String token;
//    private Long data1;
//    private Date data2;
//    private LocalDateTime data3;



}
