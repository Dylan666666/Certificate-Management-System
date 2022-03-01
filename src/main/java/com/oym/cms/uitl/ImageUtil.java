package com.oym.cms.uitl;

import com.oym.cms.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 处理图片
 * @Author: Mr_OO
 * @Date: 2022/3/1 13:48
 */
public class ImageUtil {
    /**
     * 当前路径获取
     */
    private static String basePath01 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String basePath;
    /**
     * 日期格式
     */
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 随机数生成对象
     */
    private static final Random random = new Random();
    static {
        try {
            /**
             * 当前路径的编码规范，避免编码出错
             */
            basePath = URLDecoder.decode(basePath01,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理缩略图，并返回新生成图片的绝对路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            //对接收的图片做大小限制处理，并添加水印，存储到指定位置
            Thumbnails.of(thumbnail.getImage()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, null,0.25f)
                    .outputQuality(1f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 生成随机文件名，当前年月日小时分秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        //获取随机的五位数
        int ranNum = random.nextInt(89999) + 10000;
        //当前年月日小时分秒
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + ranNum;
    }

    /**
     * 获取输入流的扩展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName ) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及到的目录，即 /home/project/image/xxx.jpg 
     * 自动创建所需的三个文件夹
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * Path如果是文件路径，则删除该文件；如果是目录路径，则删除该目录下所有文件
     *
     * @param goodsPath
     */
    public static void deleteFileOrPath(String goodsPath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + goodsPath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File files[] = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
