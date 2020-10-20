package com.swustacm.poweroj.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @program: multi-module
 * @description: 文件操作工具
 * @author: tangcan
 * @create: 2019-07-09 14:56
 **/
public class FileUtil {
    private static Logger log = LoggerFactory.getLogger(FileUtil.class);


    /*
    导出任意文件格式
     */
    public static void exportFile(HttpServletResponse response, String picPath, String fileName) throws IOException {
        // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
        fileExport(response, picPath);
    }

    /*
    导出图片
     */
    public static void exportPic(HttpServletResponse response, String picPath) throws IOException {
        response.setContentType("image/jpeg");
        fileExport(response, picPath);
    }

    /*
    导出视频
     */
    public static void exportVideo(HttpServletResponse response, String picPath) throws IOException {
        response.setContentType("application/octet-stream");
        fileExport(response, picPath);
    }

    /*
    文件导出具体实现
     */
    public static void fileExport(HttpServletResponse response, String filePath) throws IOException {
        File file = new File(filePath);
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        /*
          一次仅传输1K，不会溢出
         */
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len;
        while (-1 != (len = is.read(buffer, 0, buf_size))) {
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
    }



    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

    /*
    文件压缩并下载
    由于每个文件不方便进行中文命名，因此单独使用fileNameList对应每个文件的名称
     */
    public static void toZipAndDownload(HttpServletResponse response, List<File> files, List<String> fileNameList, String zipName) throws IOException {
        // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(zipName, "utf-8"));
        byte[] buffer = new byte[1024];
        try (ZipOutputStream zout = new ZipOutputStream(response.getOutputStream())) {
            int i = 0;
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                zout.putNextEntry(new ZipEntry(fileNameList.get(i)));
                i++;
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    zout.write(buffer, 0, len);
                }
                zout.flush();
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
