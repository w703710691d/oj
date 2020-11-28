package com.swustacm.poweroj.common.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 图片参数类型
 * @author zcy
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class Captcha {
    private String verCode;
    private String base64;
    private String verKey;
}

