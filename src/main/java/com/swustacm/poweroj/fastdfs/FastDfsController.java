package com.swustacm.poweroj.fastdfs;

import com.swustacm.poweroj.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * fastDfs操作demo
 *
 * @author xingzi
 */
@RestController
@RequestMapping("/oj")
@Slf4j
public class FastDfsController {
    @Autowired
    FastDfsBiz fastDfsBiz;
    @PostMapping("/saveFile")
    public CommonResult<String> saveFile(MultipartFile multipartFile) {

       return fastDfsBiz.saveFile(multipartFile);
    }
}
