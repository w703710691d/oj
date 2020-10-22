package com.swustacm.poweroj.common;

/**
 * @author xingzi
 * 全局常量定义
 */
public interface GlobalConstant {
     String GIS_SETTING = "gis_set";
     String[] OVER_TYPE_NAME = {"未知","计量设备" ,"安全设备（阀门）","管件" ,"场站","文本","巡检点"};
     String[] PIPE_SHOW_TYPE_NAME = {"1级管道","2级管道","3级管道","4级管道","未定义"};
     String[] OVERLAY_SHOW_TYPE_NAME = {"1 级测压","2 级测压","3 级测压","4 级测压","未定义"};
     Long TOKEN_EXP = 2 * 60 * 60 * 1000L;

     String ADMIN = "admin";
     String TEACHER = "teacher";
}
