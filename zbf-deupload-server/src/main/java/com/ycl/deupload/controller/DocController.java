package com.ycl.deupload.controller;


import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseUser;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-11
 */
@RestController
public class DocController {

    ResponseResult responseResult = new ResponseResult();

    @RequestMapping("getDoc")
    public ResponseResult getDoc(@RequestBody BaseUser user) throws IOException, TemplateException {
        try {
            Map<String,String> dataMap = new HashMap<String,String>();
           /* if(user.getId() != null ){
                dataMap.put("id", String.valueOf(user.getId()));
            }else{
                dataMap.put("id", "该用户没有id");
            }*/
            if(user.getUserName() != null){
                dataMap.put("userName", user.getUserName());
            }else{
                dataMap.put("userName","该用户没有姓名");
            }
            if(user.getLoginName() != null){
                dataMap.put("loginName", user.getLoginName());
            }else{
                dataMap.put("loginName","该用户没有登录姓名");
            }
            if(user.getTel() != null ){
                dataMap.put("tel", user.getTel());
            }else{
                dataMap.put("tel","该用户没有手机号");
            }
            if(user.getEmail()!= null ){
                dataMap.put("email", user.getEmail());
            }else{
                dataMap.put("email","该用户没有邮箱");
            }
          /*  if(user.getStatus()!= null ){
                dataMap.put("image", Image2Base64(user.getImage()));
            }else{
                dataMap.put("image","该用户没有激活状态");
            }*/
            if(user.getCreateTime()!= null ){
                dataMap.put("createTime", String.valueOf(user.getCreateTime()));
            }else{
                dataMap.put("createTime","该用户没有创建时间");
            }
            if(user.getImage() != null){
                dataMap.put("image", Image2Base64(user.getImage()));
                //System.out.println(Image2Base64((user.getImage())));
            }else{
                dataMap.put("image","该用户没头像");
            }

            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            //指定模板路径的第二种方式,我的路径是D:/      还有其他方式
            configuration.setDirectoryForTemplateLoading(new File("I:/getDoc"));


            // 输出文档路径及名称
            File outFile = new File("I:/getDoc/"+user.getUserName()+".doc");
            //以utf-8的编码读取ftl文件
          /*  String templateName = "test.xml";
            if(templateName.endsWith(".ftl")) {
                templateName = templateName.substring(0, templateName.indexOf(".ftl"));
            }*/
            Template template = configuration.getTemplate("test.xml");
            //Template t =  configuration.getTemplate("docxml.xml","utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
            template.process(dataMap, out);
            out.close();
            responseResult.setCode(1006);
            return responseResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseResult;

    }



    /**
     * 获取图片对应的base64码
     * <p>
     * 图片
     *
     * @return 图片对应的base64码
     * @throws IOException
     * @date 2018/11/16 17:05
     */
    //获得图片的base64码
    public static String getImageBase(String src) throws Exception {
        if (src == null || src == "") {
            return "";
        }
        File file = new File(src);
        if (!file.exists()) {
            return "";
        }
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


    /**
     * 远程读取image转换为Base64字符串
     *
     * @param imgUrl
     * @return
     */
    public static String Image2Base64(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();

            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return new BASE64Encoder().encode(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }  //下载
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return imgUrl;
    }

}



