package com.swustacm.poweroj.biz;

import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.util.FastDfsUtils;
import com.swustacm.poweroj.entity.fastdfs.FastDfsInfo;
import com.swustacm.poweroj.entity.fastdfs.UploadReturnInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * fastDfs处理类
 * @author xingzi
 */
@Component
@Slf4j
public class FastDfsBiz {
    public CommonResult<String> saveFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
            inputStream.close();
//            fileName, file_buff, ext
            FastDfsInfo file = FastDfsInfo.builder()
                    .name(fileName)
                    .content(file_buff)
                    .ext(ext)
                    .build();
            try {
                //upload to fastdfs
                UploadReturnInfo uploadReturnInfo = FastDfsUtils.upload(file);
            } catch (Exception e) {
                log.error("upload file Exception!", e);
            }

            return CommonResult.ok();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.error("保存失败");
        }
    }
}
