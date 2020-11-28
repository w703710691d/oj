package com.swustacm.poweroj.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.power.doc.builder.PostmanJsonBuilder;
import com.power.doc.model.ApiConfig;

/**
 * @author lixiaobo
 * @package: com.group.inspection
 * @creatTime 2020-07-13 16:21
 * @description:
 */
public class Genarator {
    public static void main(String[] args) {
        generatorTemple();
    }

    public static void generatorTemple(){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String path = System.getProperty("user.dir");
        path = path+"/src/main/java";
        String user = System.getProperty("user.name");


        //文件位置
        gc.setOutputDir(path);
        gc.setAuthor(user);
        gc.setOpen(true);
        gc.setBaseResultMap(true);
        gc.setFileOverride(false);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setIdType(IdType.AUTO);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.1.111:3306/oj?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("remote");
        dsc.setPassword("li112411");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.swustacm.poweroj");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setXml("mapper");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg =new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //包含表
        strategy.setInclude("topic");
        //lombok
        strategy.setEntityLombokModel(true);
        //restController
        strategy.setRestControllerStyle(true);
        //field doc
        strategy.setEntityTableFieldAnnotationEnable(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    public static void generatorDoc(){
        ApiConfig config = new ApiConfig();
        config.setServerUrl("http://localhost:8080");

        //设置为严格模式，Smart-doc将降至要求每个Controller暴露的接口写上标准文档注释
        config.setStrict(false);

        //当把AllInOne设置为true时，Smart-doc将会把所有接口生成到一个Markdown、HHTML或者AsciiDoc中
        config.setAllInOne(true);

        //Set the api document output path.
        config.setOutPath("d:\\md2");

        // 设置接口包扫描路径过滤，如果不配置则Smart-doc默认扫描所有的接口类
        // 配置多个报名有英文逗号隔开


        //since 1.7.9 新增是否显示接口作者 默认true
        config.setShowAuthor(false);
        //设置公共请求头.如果不需要请求头，则无需设置



        //不指定SourcePaths默认加载代码为项目src/main/java下的,如果项目的某一些实体来自外部代码可以一起加载
        config.setSourceCodePaths(
                //自1.7.0版本开始，在此处可以不设置本地代码路径，单独添加外部代码路径即可
                // SourceCodePath.path().setDesc("本项目代码").setPath("src/main/java"),
               // SourceCodePath.builder().setDesc("加载项目外代码").setPath("E:\\ApplicationPower\\ApplicationPower\\Common-util\\src\\main\\java")
        );

        PostmanJsonBuilder.buildPostmanCollection(config);
    }
}
