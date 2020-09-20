package com.ycl.deupload.controller;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.zbf.user.controller
 * @author:袁成龙
 * @createTime:2020/9/13 19:36
 * @version:1.0
 */
@RestController
public class UploadController {

    private static String url = "http://120.55.44.68:9000";
    private static String access = "minioadmin";
    private static String secret = "minioadmin";
    private static String bucket = "two";


    /**
     * @Author 袁成龙
     * @Description //TODO minio的图片上传的功能
     * @Date 9:54 2020/9/20
     * @Param 
     * @return 
     **/
    @RequestMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException, InvalidPortException, InvalidEndpointException {
        String fileName=null;
        try {
            //创建MinioClient对象
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(url)
                            .credentials(access, secret)
                            .build();
            InputStream in = file.getInputStream();
            fileName = file.getOriginalFilename();
            //String fileName = file.getName();
            //文件上传到minio上的Name把文件后缀带上，不然下载出现格式问题
            fileName = UUID.randomUUID() +"."+fileName.substring(fileName.lastIndexOf(".") + 1);
            //创建头部信息
            Map<String, String> headers = new HashMap<>(10);
            //添加自定义内容类型
            headers.put("Content-Type", "image/jpeg");
            //添加存储类
            headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
            //添加自定义/用户元数据
            Map<String, String> userMetadata = new HashMap<>(10);
            userMetadata.put("My-Project", "Project One");
            //上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(fileName).stream(
                            in, in.available(), -1)
                            .headers(headers)
                            .build());
            //.userMetadata(userMetadata)
            in.close();
            System.out.println(fileName);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println(url+"/"+bucket+"/"+fileName);
        return url+"/"+bucket+"/"+fileName;
    }
}
