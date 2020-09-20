package com.zbf.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycl.util.RandomUtil;
import com.zbf.common.entity.AllRedisKey;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.ResponseResultEnum;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.common.utils.*;
import com.zbf.user.service.IBaseUserService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
public class ActivateController{


    @Value("${oK.path}")
    private String actipaht;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    IBaseUserService iBaseUserService;

    ResponseResult responseResult = new ResponseResult();

    private static String url = "http://120.55.44.68:9000";
    private static String access = "minioadmin";
    private static String secret = "minioadmin";
    private static String bucket = "two";




    /**
     * @Author 袁成龙
     * @Description 这个类是用来添加注册的用户的
     * @Date 18:11 2020/9/16
     * @Param 一个用户的实体
     * @return 返回的是一个ResponseResult一个自己封装的用来返回的类
     **/
    @RequestMapping("/Add")
    public ResponseResult add(@RequestBody BaseUser baseUser){
        int random = RandomUtil.random(1, 10000000);
        System.out.println(random);
        int random1 = RandomUtil.random(1, 99999);
        System.out.println(random1);
        String i = random +""+ random1;
        System.out.println(i);
        System.out.println("!!!)))!!!==================");
        //为了保证email和loginname不可以有重复的所以保证这两个值为唯一,所以判断
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email",baseUser.getEmail());
        BaseUser one = iBaseUserService.getOne(queryWrapper);

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("loginName",baseUser.getLoginName());
        BaseUser one1 = iBaseUserService.getOne(queryWrapper1);
        System.out.println(baseUser);
        System.out.println("------"+one);
        //查看db里是否有相同的email如果有返回1005提示有相同的用户名
        if(one!=null){
            System.out.println("================1");
            responseResult.setCode(1005);
            return responseResult;
            //判断是否有相同的登录名如果有返回1004提示有相同的登录名
        }else if(one1!=null){
            System.out.println("================2");
            responseResult.setCode(1004);
            return responseResult;
        }else{
            if(baseUser.getImage()!=null) {
                //如果用户名和email都没有的情况下执行添加操作
                System.out.println("==================3");
                String s = RandomUtil.randomNumber(4);
                //MD5加密
                String encodePass = Md5.encode(baseUser.getPassWord() + s, "MD5");
                System.out.println(encodePass);
                BaseUser user = new BaseUser(Long.valueOf(i).longValue(),
                        baseUser.getUserName(),
                        baseUser.getLoginName(),
                        encodePass,
                        baseUser.getTel(),
                        baseUser.getSex(),
                        baseUser.getEmail(),
                        s, baseUser.getStatus(),
                        baseUser.getImage(),
                        LocalDateTime.now());
                boolean save = iBaseUserService.save(user);
                //如果执行成功
                if (save) {
                    BaseUser maxid1 = iBaseUserService.maxid();
                    boolean adduserrole = iBaseUserService.adduserrole(maxid1.getId());
                    System.out.println("添加了中间表等操作" + adduserrole);
                    //如果成功提示1006提示返回成功
                    responseResult.setCode(1006);
                    return responseResult;
                }
            }else{
                //如果用户名和email都没有的情况下执行添加操作
                System.out.println("==================3");
                String s = RandomUtil.randomNumber(4);
                //MD5加密
                String encodePass= Md5.encode(baseUser.getPassWord()+s, "MD5");
                System.out.println(encodePass);
                BaseUser user = new BaseUser(Long.valueOf(i).longValue(),
                        baseUser.getUserName(),
                        baseUser.getLoginName(),
                        encodePass,
                        baseUser.getTel(),
                        baseUser.getSex(),
                        baseUser.getEmail(),
                        s,0);
                boolean save = iBaseUserService.save(user);
                //如果执行成功
                if(save){
                    BaseUser maxid1 = iBaseUserService.maxid();
                    boolean adduserrole = iBaseUserService.adduserrole(maxid1.getId());
                    System.out.println("添加了中间表等操作"+adduserrole);
                    //通过异步线程来操作发邮件的这项操作
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MailQQUtils.sendMessage("2286693346@qq.com",getActivate(actipaht,1*60*1000L,baseUser.getLoginName()),"NBA湖人招募信息","");
                        }
                    });
                    thread.start();
                    //如果成功提示1006提示返回成功
                    responseResult.setCode(1006);
                    return responseResult;
                }else{
                    responseResult.setCode(1008);
                    return responseResult;
                }
            }
            return responseResult;
        }
    }


    /**
     * @Author 袁成龙
     * @Description 生成一个激活链接
     * @Date 19:40 2020/9/13
     * @Param
     * @return
     **/
    public String getActivate(String baseactivePath,long timeout,String loginName) {

        System.out.println("===============!!!!!!!!!!!!"+loginName);
        System.out.println("===============!!!!!!!!!!!!"+timeout);
        System.out.println("===============!!!!!!!!!!!!"+baseactivePath);


        //生成一个16位的激活码
        String uuid16 = UID.getUUID16();
        System.out.println(AllRedisKey.ACTIVE_KEY.getKey());
        System.out.println("活码"+uuid16);
        //存入redis
        redisTemplate.opsForHash().put(AllRedisKey.ACTIVE_KEY.getKey(),loginName,uuid16);


        System.out.println("===16位激活码"+uuid16);
        StringBuffer stringBuffer = new StringBuffer(baseactivePath);
        System.out.println("===获得的链接"+stringBuffer);
        stringBuffer.append("?");
        stringBuffer.append("active="+loginName+"=");
        stringBuffer.append(JwtUtilsForOther.generateToken(uuid16,timeout));
        String s = stringBuffer.toString();
        System.out.println("===生成的完整的链接"+s);
        //最后在赋为空值为保证下一次开始还是空
        stringBuffer=null;
        return s;
    }


    @RequestMapping("/userActivate")
    public void getpath(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            String queryString = request.getQueryString();
            System.out.println("?后面的拼接的路径" + queryString);
            String[] split = queryString.split("=");
            System.out.println(split[0]);
            System.out.println(split[1]);
            System.out.println(split[2]);
            JSONObject jsonObject = JwtUtilsForOther.decodeJwtTocken(split[2]);
            System.out.println("解密成功的的信息" + jsonObject);
            System.out.println(AllRedisKey.ACTIVE_KEY.getKey());
            String o = (String) redisTemplate.opsForHash().get(AllRedisKey.ACTIVE_KEY.getKey(), split[1]);
            System.out.println(o);
            if (o.equals(jsonObject.get("info"))) {
                System.out.println("========ok");
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("loginName",split[1]);
                BaseUser user = new BaseUser(1);
                boolean update = iBaseUserService.update(user, queryWrapper);
                System.out.println(update);
                if (update) {
                    responseResult.setSuccess(ResponseResultEnum.SECCESS.getMessage());
                    responseResult.setCode(ResponseResultEnum.SECCESS.getCode());
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();

                    hashMap.put("loginPath","http://localhost:8080");
                    FreemarkerUtils.getStaticHtml(BaseUserController.class,
                            "/activePath/",
                            "OkgoLogin.html",hashMap, writer);
                    redisTemplate.opsForHash().delete(AllRedisKey.ACTIVE_KEY.getKey(), split[1]);
                }
            }else{
                System.out.println("激活失败了");

                hashMap.put("newActiveLink","http://192.168.180.1:10000/user/getNewActiveLink?loginName="+split[1]);
                FreemarkerUtils.getStaticHtml(BaseUserController.class,"/activePath/","ErrorLogin.html",hashMap,response.getWriter());
            }
        } catch (Exception e) {
            String queryString = request.getQueryString();
            String[] split = queryString.split("=");
            System.out.println(split[1]);
            HashMap<String, Object> newData = new HashMap<>();
            newData.put("newActiveLink","http://192.168.180.1:10000/user/getNewActiveLink?loginName="+split[1]);
            FreemarkerUtils.getStaticHtml(ActivateController.class,"/activePath/","ErrorLogin.html",newData,response.getWriter());
        }

    }


    /**
     * 作者: LCG
     * 日期: 2020/9/14  9:24
     * 描述: 激活失败重新获取激活链接邮件
     * @Param [request, response]
     * @Return void
     */
    @RequestMapping("getNewActiveLink")
    public void getNewActiveLink(HttpServletRequest request,HttpServletResponse response) throws Exception {

        System.out.println("=====进入了789789======");

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        //设置响应头的格式
        response.setContentType("text/html;charset=UTF-8");

        //如果jwt过期，重新的发激活邮件
        String loginName = request.getParameter("loginName");
        System.out.println("login的值失败后"+loginName);
        //根据loginName获取用户信息

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MailQQUtils.sendMessage("2286693346@qq.com",getActivate(actipaht,1*60*3000L,loginName),"NBA湖人招募信息","");
            }
        });
        thread.start();

        //3、扣扣邮箱发送激活邮件
        FreemarkerUtils.getStaticHtml(BaseUserController.class,
                "/activePath/",
                "sendOk.html",stringObjectHashMap, response.getWriter());
    }



/*    @RequestMapping("upload")
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
    }*/




}
