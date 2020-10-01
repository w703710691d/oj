package com.swustacm.poweroj.common.util;

import com.swustacm.poweroj.entity.fastdfs.FastDfsInfo;
import com.swustacm.poweroj.entity.fastdfs.UploadReturnInfo;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * fastDFS 工具
 *
 * @author xingzi
 */
@Slf4j
public class FastDfsUtils {
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;

    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ;
            ClientGlobal.init(filePath);
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getTrackerServer();
            storageServer = trackerClient.getStoreStorage(trackerServer);
        } catch (Exception e) {
            log.error("FastDFS Client Init Fail!", e);
        }
    }

    /**
     * 文件上传
     *
     * @param file 文件信息
     * @return
     */
    public static UploadReturnInfo upload(FastDfsInfo file) {
        log.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("author", file.getAuthor());
        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        try {
            storageClient = new StorageClient(trackerServer, storageServer);
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), metaList);
        } catch (IOException e) {
            log.error("IO Exception when uploadind the file:" + file.getName(), e);
        } catch (Exception e) {
            log.error("Non IO Exception when uploadind the file:" + file.getName(), e);
        }
        log.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");

        UploadReturnInfo uploadReturnInfo = new UploadReturnInfo();
        if (uploadResults == null) {
            log.error("upload file fail, error code:" + storageClient.getErrorCode());
            uploadReturnInfo.setCode((int) storageClient.getErrorCode());
            uploadReturnInfo.setMsg("文件上传失败");
            return uploadReturnInfo;
        }

        String groupName = Objects.requireNonNull(uploadResults)[0];
        String remoteFileName = uploadResults[1];

        log.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
        uploadReturnInfo.setCode(200);
        uploadReturnInfo.setMsg("文件上传成功");
        uploadReturnInfo.setGroupName(groupName);
        uploadReturnInfo.setRemoteFileName(remoteFileName);

        return uploadReturnInfo;
    }

    /**
     * 获取文件的基本信息 不包含文件
     * @param groupName      分组名称
     * @param remoteFileName 远程文件名称
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            storageClient = new StorageClient(trackerServer, storageServer);
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            log.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            log.error("Non IO Exception: Get File from Fast DFS failed", e);

        }
        return null;
    }

    /**
     * 下载文件
     * @param groupName 分组名称
     * @param remoteFileName 远程文件名称
     * @return
     */
    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            storageClient = new StorageClient(trackerServer, storageServer);
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            return new ByteArrayInputStream(fileByte);
        } catch (IOException e) {
            log.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            log.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    /**
     * 删除文件
     * @param groupName 分组名称
     * @param remoteFileName 远程文件名称
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName)
            throws Exception {
        storageClient = new StorageClient(trackerServer, storageServer);
        int i = storageClient.delete_file(groupName, remoteFileName);
        log.info("delete file successfully!!!" + i);
    }
}
