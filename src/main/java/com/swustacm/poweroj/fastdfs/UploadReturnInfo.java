package com.swustacm.poweroj.fastdfs.fastdfs;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xingzi
 * 上传文件信息的返回类
 */
@Data
@Accessors(chain = true)
public class UploadReturnInfo {
    private Integer code;
    private String groupName;
    private String remoteFileName;
    private String msg;
}
