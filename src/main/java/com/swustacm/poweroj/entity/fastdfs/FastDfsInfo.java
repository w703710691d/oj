package com.swustacm.poweroj.entity.fastdfs;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xingzi
 * fastDFS 文件信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class FastDfsInfo {
    private String name;
    private byte[] content;
    private String ext;
    private String md5;
    private String author;
}
