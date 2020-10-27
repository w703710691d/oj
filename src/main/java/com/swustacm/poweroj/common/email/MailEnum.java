package com.swustacm.poweroj.common.email;

import lombok.Data;

/**
 *
 * @author zcy
 */

public enum MailEnum {
    /*
    PowerOJ账号激活
     */
    CODE_MAIL("PowerOJ账号激活","code_email.ftl")

    ;
    String type;
    String ftl;
    MailEnum(){

    }

    MailEnum(String type,String ftl){
        this.type = type;
        this.ftl = ftl;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFtl() {
        return ftl;
    }

    public void setFtl(String ftl) {
        this.ftl = ftl;
    }



}

