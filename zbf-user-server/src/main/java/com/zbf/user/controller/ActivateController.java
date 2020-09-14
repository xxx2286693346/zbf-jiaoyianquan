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
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

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




    @RequestMapping("/Add")
    public ResponseResult add(@RequestBody BaseUser baseUser){
        System.out.println("!!!)))!!!==================");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email",baseUser.getEmail());
        BaseUser one = iBaseUserService.getOne(queryWrapper);

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("loginName",baseUser.getLoginName());
        BaseUser one1 = iBaseUserService.getOne(queryWrapper1);
        System.out.println(baseUser);
        System.out.println("------"+one);
        if(one!=null){
            System.out.println("================1");
            responseResult.setCode(1005);
            return responseResult;
        }else if(one1!=null){
            System.out.println("================2");
            responseResult.setCode(1004);
            return responseResult;
        }else{
            System.out.println("==================3");
            String s = RandomUtil.randomNumber(4);
            String encodePass= Md5.encode(baseUser.getPassWord()+s, "MD5");
            System.out.println(encodePass);
            BaseUser user = new BaseUser(null,
                    baseUser.getUserName(),
                    baseUser.getLoginName(),
                    encodePass,
                    baseUser.getTel(),
                    baseUser.getSex(),
                    baseUser.getEmail(),
                    s,0);
            boolean save = iBaseUserService.save(user);
            if(save){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MailQQUtils.sendMessage("2286693346@qq.com",getActivate(actipaht,1*60*1000L,baseUser.getLoginName()),"NBA湖人招募信息","");
                    }
                });
                thread.start();
                responseResult.setCode(1006);
                return responseResult;
            }else{
                responseResult.setCode(1008);
                return responseResult;
            }
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

}