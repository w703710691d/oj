package com.swustacm.poweroj.biz;

import com.swustacm.poweroj.common.CodeEnum;
import com.swustacm.poweroj.common.CustomException;
import com.swustacm.poweroj.common.util.RedisUtils;
import com.swustacm.poweroj.params.Captcha;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author zvy
 */
@RestController
@RequestMapping("/captcha")

public class CaptchaBiz {

    @Autowired
    RedisUtils redisUtils;
    public Captcha getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130,48,5);
        String verCode = specCaptcha.text().toLowerCase();
        System.out.println("验证码："+verCode);
        String verKey = UUID.randomUUID().toString();
        redisUtils.set(verKey,verCode,5*60);
        return new Captcha().setVerKey(verKey).setBase64(specCaptcha.toBase64()).setVerCode(verCode);
    }


    public void verify(String verCode, String verKey) {
        if(verCode == null)
            throw new CustomException(CodeEnum.CODE_ERROR);
        String code =  redisUtils.get(verKey);
        if(code == null)
            throw  new CustomException(CodeEnum.CODE_EXPIRE);
        if( !(code.equals(verCode.trim().toLowerCase())))
            throw new CustomException(CodeEnum.CODE_ERROR);

    }
}