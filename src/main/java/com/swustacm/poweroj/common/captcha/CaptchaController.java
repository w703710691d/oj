package com.swustacm.poweroj.common.captcha;

import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.params.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片验证码
 * @auhtor zcy
 */
@RestController
@RequestMapping("/captcha")

public class CaptchaController {

    @Autowired
    CaptchaBiz captchaBiz;

    /**
     * 获取验证码
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    public CommonResult getCaptcha(){
        Map<String,Object> map = new HashMap<>();
        Captcha captcha = captchaBiz.getCaptcha();
        map.put("base64",captcha.getBase64());
        map.put("verKey",captcha.getVerKey());
        return CommonResult.ok(map);
    }
}