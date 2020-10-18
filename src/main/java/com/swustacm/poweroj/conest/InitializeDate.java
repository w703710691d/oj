package com.swustacm.poweroj.conest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Slf4j
@Component
public class InitializeDate  {


    @PostConstruct
    public void init(){
        log.info("初始化数据");
        ConestVar.initJudgeResult();
        ConestVar.loadLanguage();
        log.info("完成初始化数据");
    }


}
