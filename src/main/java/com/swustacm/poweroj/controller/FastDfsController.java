package com.swustacm.poweroj.controller;

import com.swustacm.poweroj.biz.FastDfsBiz;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.util.FastDfsUtils;
import com.swustacm.poweroj.entity.fastdfs.FastDfsInfo;
import com.swustacm.poweroj.entity.fastdfs.UploadReturnInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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
