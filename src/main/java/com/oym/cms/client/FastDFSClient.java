package com.oym.cms.client;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import java.io.*;

/**
 * 分布式证书存储客户端类
 * @Author: Mr_OO
 * @Date: 2022/4/2 15:04
 */
public class FastDFSClient {

    private static StorageClient storageClient = null;

    /**
     * 只加载一次.
     */
    static {
        try {
            ClientGlobal.init("application.properties");
            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    /**
     * @param inputStream
     *    上传的文件输入流
     * @param fileName
     *    上传的文件原始名
     * @return
     */
    public static String[] uploadFile(InputStream inputStream, String fileName) {
        try {
            // 文件的元数据
            NameValuePair[] meta_list = new NameValuePair[2];
            // 第一组元数据，文件的原始名称
            meta_list[0] = new NameValuePair("file name", fileName);
            // 第二组元数据
            meta_list[1] = new NameValuePair("file length", inputStream.available()+"");
            // 准备字节数组
            byte[] file_buff = null;
            if (inputStream != null) {
                // 查看文件的长度
                int len = inputStream.available();
                // 创建对应长度的字节数组
                file_buff = new byte[len];
                // 将输入流中的字节内容，读到字节数组中。
                inputStream.read(file_buff);
            }
            // 上传文件。参数含义：要上传的文件的内容（使用字节数组传递），上传的文件的类型（扩展名），元数据
            String[] fileids = storageClient.upload_file(file_buff, getFileExt(fileName), meta_list);
            return fileids;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param file
     *            文件
     * @param fileName
     *            文件名
     * @return 返回Null则为失败
     */
    public static String[] uploadFile(File file, String fileName) {
        FileInputStream fis = null;
        try {
            NameValuePair[] metaList = null; 
            fis = new FileInputStream(file);
            byte[] fileBuff = null;
            if (fis != null) {
                int len = fis.available();
                fileBuff = new byte[len];
                fis.read(fileBuff);
            }
            String[] fileids = storageClient.upload_file(fileBuff, getFileExt(fileName), metaList);
            return fileids;
        } catch (Exception ex) {
            return null;
        } finally {
            if (fis != null) {
            try {
                fis.close(); 
            } catch (IOException e) {
                e.printStackTrace(); 
            } 
            }
        }
    }

    /**
     * 根据组名和远程文件名来删除一个文件
     *
     * @param groupName
     * 例如 "group1" 如果不指定该值，默认为group1
     * @param remoteFileName
     * 例如"M00/00/00/wKgxgk5HbLvfP86RAAAAChd9X1Y736.jpg"
     * @return 0为成功，非0为失败，具体为错误代码
     */
    private static int deleteFile(String groupName, String remoteFileName) {
        try {
            int result = storageClient.delete_file(groupName == null ? "group1" : groupName, remoteFileName);
            return result;
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 修改一个已经存在的文件
     *
     * @param oldGroupName
     *            旧的组名
     * @param oldFileName
     *            旧的文件名
     * @param file
     *            新文件
     * @param fileName
     *            新文件名
     * @return 返回空则为失败
     */
    private static String[] modifyFile(String oldGroupName, String oldFileName, File file, String fileName) {
        String[] fileids = null;
        try {
            // 先上传
            fileids = uploadFile(file, fileName);
            if (fileids == null) {
                return null;
            }
            // 再删除
            int delResult = deleteFile(oldGroupName, oldFileName);
            if (delResult != 0) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
        return fileids;
   }

    /**
    * 文件下载
    *
    * @param groupName 卷名
    * @param remoteFileName 文件名
    * @return 返回一个流
    */
    public static InputStream downloadFile(String groupName, String remoteFileName) {
        try {
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        } catch (Exception ex) {
            return null;
        }
    }

    public static NameValuePair[] getMetaDate(String groupName, String remoteFileName){
        try {
            NameValuePair[] nvp = storageClient.get_metadata(groupName, remoteFileName);
            return nvp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
    * 获取文件后缀名（不带点）.
    *
    * @return 如："jpg" or "png".
    */
    private static String getFileExt(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1); 
        }
    }

    public static void main(String[] args) {
        try {
//            File file = new File("C:/Users/14396/Desktop/my/图片/绿叶.png");
//            InputStream inputStream = new FileInputStream(file);
//            String fileName = "110429" + ImageUtil.getRandomFileName() + ".png"
//            String[] result = FastDFSClient.uploadFile(inputStream, fileName);
//            //[group1, M00/00/00/CgAYCGJFpQKACftIAAAbnjkyo8A652.png]
//            System.out.println(ImageUtil.IMAGE_URL_PRE + ImageUtil.getImageUrl(result));
//            //http://101.43.139.237:8081/group1/M00/00/00/CgAYCGJFsVmAWtitAAAbnjkyo8A098.png
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}
