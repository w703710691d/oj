package com.swustacm.poweroj.common.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@Component
public class MailService {

    @Autowired
    private EmailConfig mail;

    private final static  String ADDRESS = "localhost:";
    private final static  String PORT = "8090";


    public void sendMailForActivate(String email, MailEnum mailEnum , Map<String,Object> model){

        if (email.isEmpty()){
            return;
        }
        if(model == null){
            return;
        }
        String url = ADDRESS+PORT+"/dev/user/email/verify?name="+model.get("name").toString()+"&token="+model.get("emailToken");
        model.put("url",url);
        model.put("data",new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        mail.sendMail(mailEnum,model,email);

    }



}
