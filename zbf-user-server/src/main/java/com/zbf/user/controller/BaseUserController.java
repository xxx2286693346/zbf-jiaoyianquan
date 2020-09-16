package com.zbf.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycl.util.RandomUtil;
import com.ycl.util.StringUtil;
import com.zbf.common.entity.AllRedisKey;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.ResponseResultEnum;
import com.zbf.common.entity.enums.SexEnum;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.common.utils.*;
import com.zbf.user.mapper.BaseUserMapper;
import com.zbf.user.service.IBaseUserService;
import com.zbf.user.service.ServiceYan;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-11
 */
@RestController
public class BaseUserController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${oK.path}")
    private String actipaht;

    @Autowired
    ServiceYan baseUser;

    @Autowired
    IBaseUserService iBaseUserService;

    ResponseResult responseResult = new ResponseResult();


    private static final Pattern PATTERN_PHONE = Pattern.compile("^-?\\d+(\\.\\d+)?$");



    /**
     * @Author 袁成龙
     * @Description //TODO * @param tel
    * @param code
     * @Date 8:26 2020/9/16
     * @Param 
     * @return 
     **/
    @RequestMapping("/register")
    public ResponseResult test01(String tel,String code){

        System.out.println("tel=="+tel+"code=="+code);
        //String s = redisTemplate.opsForValue().get("codes");
       // System.out.println("登录的验证"+s);
        if("1234".equals(code)){
            responseResult.setCode(1006);
            responseResult.setSuccess("1234");
            //redisTemplate.delete("codes");
            return responseResult;
        }else {
            responseResult.setCode(1004);
            return responseResult;
        }
    }


    @RequestMapping("/Yan")
    public String yanzheng(String phone){
        if(!StringUtil.gitPhone(phone)){
            return "1003";
        }
        System.out.println(phone);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tel",phone);
        BaseUser one = iBaseUserService.getOne(queryWrapper);
        System.out.println("==="+one);
        if(one==null){
            boolean loginyan = baseUser.loginyan(phone);
            System.out.println(loginyan);
            if(loginyan){
                return "1006";
            }else{
                return "1005";
            }
        }else {
            return "1004";
        }
    }



    @RequestMapping("/UpdateYan")
    public boolean UpdateYan(String phone){
        if(StringUtil.gitEmail(phone)){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("email",phone);
            BaseUser one = iBaseUserService.getOne(queryWrapper);
            if(one!=null){
                System.out.println("======"+phone);
                String s = RandomUtil.randomNumber(4);
                System.out.println(s);
                redisTemplate.opsForValue().set("codes",s);
                MailQQUtils.sendMessage(phone,s,"湖人招募","");
                return true;
            }
            return false;
        }else if(isPhone(phone)){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("tel",phone);
            BaseUser one = iBaseUserService.getOne(queryWrapper);
            System.out.println(one);
            if(one!=null){
                System.out.println(phone) ;
                System.out.println("++++++++++++++++=");
                boolean b = baseUser.loginyan(phone);
                return b;
            }else {
                return false;
            }

        }else{
            return false;
        }
    }



    @RequestMapping("/BiDui")
    public ResponseResult BiDui(String tel,String code){
            System.out.println("tel=="+tel+"code=="+code);
            String s = redisTemplate.opsForValue().get("codes");
            System.out.println("登录的验证"+s);
            if(s==null){
                s="1";
            }
            if(s.equals(code)){
                responseResult.setCode(1006);
                responseResult.setSuccess(s);
                redisTemplate.delete("codes");
                return responseResult;
            }else {
                responseResult.setCode(1004);
                return responseResult;
            }
        }


    @RequestMapping("/Sex")
    public SexEnum[] sexlist(){
        System.out.println("==================");
        SexEnum[] values = SexEnum.values();
        return values;
    }






/*
    @RequestMapping("/Add")
    public ResponseResult add(@RequestBody BaseUser baseUser){
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
                MailQQUtils.sendMessage("2286693346@qq.com",activateController.getActivate(actipaht,1*60*3000L,baseUser.getLoginName()),"NBA湖人招募信息","");
                responseResult.setCode(1006);
                return responseResult;
            }else{
                responseResult.setCode(1008);
                return responseResult;
            }
        }
    }
*/



    @RequestMapping("/Update")
    public boolean upda(String passWord,String phone){
        System.out.println("pass"+passWord+"ph"+phone);
        String s = RandomUtil.randomNumber(4);
        String encodePass= Md5.encode(passWord+s, "MD5");
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtil.gitEmail(phone)){
            queryWrapper.eq("email",phone);
        }
        if(isPhone(phone)){
            queryWrapper.eq("tel",phone);
        }
        BaseUser baseUser1 = new BaseUser(encodePass,s);
        boolean b = iBaseUserService.saveOrUpdate(baseUser1, queryWrapper);
        System.out.println(b);
        return b;
    }

    public boolean isPhone(String phone){
        return PATTERN_PHONE.matcher(phone).matches();
    }


    /**
     * @Author 袁成龙
     * @Description 生成一个激活链接
     * @Date 19:40 2020/9/13
     * @Param
     * @return
     **/
   /* public String getActivate(String baseactivePath,long timeout,String loginName) {
        //生成一个16位的激活码
        String uuid16 = UID.getUUID16();
        System.out.println(AllRedisKey.ACTIVE_KEY.getKey());
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
    public void getpath(HttpServletRequest request, HttpServletResponse response){
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
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("loginPath","http://localhost:8080");
                    FreemarkerUtils.getStaticHtml(BaseUserController.class,
                            "/activePath/",
                            "OkgoLogin.html",hashMap, writer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //失败的信息一般不会存在
            responseResult.setSuccess(ResponseResultEnum.FAILURE.getMessage());
            responseResult.setCode(ResponseResultEnum.FAILURE.getCode());
        }

    }*/





}

