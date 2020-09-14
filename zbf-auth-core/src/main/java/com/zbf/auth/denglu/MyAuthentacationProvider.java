package com.zbf.auth.denglu;

import com.alibaba.fastjson.JSON;
import com.ycl.util.StringUtil;
import com.zbf.auth.mapper.MenuDao;
import com.zbf.auth.mapper.UserDao;
import com.zbf.common.entity.AllRedisKey;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.ResponseResultEnum;
import com.zbf.common.utils.Md5;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author: LCG
 * 作者: LCG
 * 日期: 2020/8/26 21:43
 * 描述: 这个类主要是用用来认证 密码的  有验证码什么的也可在此认证
 */

@Component
public class MyAuthentacationProvider implements AuthenticationProvider {

    private static final Pattern PATTERN_PHONE = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    @Autowired
    private MyUserServiceDetail myUserServiceDetail;

    @Autowired
    private PhoneYan phoneYan;

    @Autowired
    private EmailYan EmailYan;

    ResponseResult responseResult = new ResponseResult();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MenuDao menuDao;
    
    @Autowired
    private UserDao userDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //获取表单的用户名
        String userName = authentication.getPrincipal().toString();
        System.out.println("userName" + userName);
        //获取用户填写的密码
        String password = authentication.getCredentials().toString();
        System.out.println("pasword" + password);

        if(isPhone(userName)){
            Map<String, Object> userByPhone = userDao.getUserByPhone(userName);
            int status = (int)userByPhone.get("status");
            if(status==0){
                throw new BadCredentialsException("用户未激活请去激活");
            }
        }else if(StringUtil.gitEmail(userName)){
            Map<String, Object> userByPhone = userDao.getUserByEmail(userName);
            int status = (int)userByPhone.get("status");
            if(status==0){
                throw new BadCredentialsException("用户未激活请去激活");
            }
        }else{
                Map<String, Object> userByUserName = userDao.getUserByUserName(userName);
                int status = (int)userByUserName.get("status");
                System.out.println("status值"+status);
                if(status==0){
                    throw new BadCredentialsException("用户未激活请去激活");
                }
        }


        //手机号邮箱的验证方式
        if (isPhone(userName)||StringUtil.gitEmail(userName)) {
            //删除激活的redis
            redisTemplate.opsForHash().delete(AllRedisKey.ACTIVE_KEY.getKey(), userName);
            System.out.println("执行了操作号的操作");
            String s = redisTemplate.opsForValue().get("codes");
            System.out.println("code的执行方式" + s);
            if (s == null) {
                throw new BadCredentialsException("验证码过期或者错误");
            }
            UserDetails userDetails = null;
            if (s.equals(password)) {
                System.out.println("进入了Pro的判断");
                if(isPhone(userName)){
                    System.out.println("))))))))))))))))))))))00");
                    userDetails = phoneYan.loadUserByUsername(userName);

                }
                if(StringUtil.gitEmail(userName)){
                    System.out.println("--===--==-00="+userName);
                     userDetails = EmailYan.loadUserByUsername(userName);
                }

                //根据获取的用户信息获取用户的密码
                String password1 = userDetails.getPassword();
                System.out.println("getusername==" + userDetails.getUsername());
                //查询所有的权限是不是在Redis，如果不在Redis的话，从数据库查询然后更新到Redis
                if (!redisTemplate.hasKey("menuRole")) {
                    List<Map<String, String>> allMenus = menuDao.getAllMenus();
                    Map<String, String> urlRole = new HashMap<>();
                    allMenus.forEach(mapp -> {
                        urlRole.put(mapp.get("urlRoleCode"), "");
                    });
                    //将所有的权限存入Redis
                    redisTemplate.opsForHash().putAll("menuRole", urlRole);
                    redisTemplate.expire("menuRole", 60 * 60, TimeUnit.SECONDS);
                }
                //说明：
                //这里一步--》密码校验成功后可以加载一下 当前用户的权限角色 放入缓存中方便鉴权的时候使用
                //userDetails.getAuthorities();//这里存储的是当前用户的角色信息
                //缓存用户的权限信息
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                String string = JSON.toJSONString(usernamePasswordAuthenticationToken);
                //将当前用户的角色信息 放入缓存大key user-auth 小key用户登录名authentication.getPrincipal()，value用户的角色列表
                redisTemplate.opsForHash().put("user-auth", userDetails.getUsername(), string);
                return usernamePasswordAuthenticationToken;
            } else {
                throw new BadCredentialsException("验证码过期或者错误");
            }
        } else {
            //删除激活的redis
            redisTemplate.opsForHash().delete(AllRedisKey.ACTIVE_KEY.getKey(), userName);
            //加密密码
            Map<String, Object> userByUserName = userDao.getUserByUserName(userName);
            System.out.println("+++++++++++++++++=="+userByUserName);
            String encodePass = Md5.encode(password+userByUserName.get("salt"), "MD5");
            //String encode = bCryptPasswordEncoder.encode(password);
            //根据用户名获取用户的信息，这里调用根据用户名获取用户信息的UserServiceDetail类
            UserDetails userDetails = myUserServiceDetail.loadUserByUsername(userName);
            //根据获取的用户信息获取用户的密码
            String password1 = userDetails.getPassword();
            //说明：
            //这里一步--》密码校验成功后可以加载一下 当前用户的权限角色 放入缓存中方便鉴权的时候使用
            //userDetails.getAuthorities();//这里存储的是当前用户的角色信息
            //缓存用户的权限信息
            //查询所有的权限是不是在Redis，如果不在Redis的话，从数据库查询然后更新到Redis
            if (!redisTemplate.hasKey("menuRole")) {
                List<Map<String, String>> allMenus = menuDao.getAllMenus();
                Map<String, String> urlRole = new HashMap<>();
                allMenus.forEach(mapp -> {
                    urlRole.put(mapp.get("urlRoleCode"), "");
                });
                //将所有的权限存入Redis
                redisTemplate.opsForHash().putAll("menuRole", urlRole);
                redisTemplate.expire("menuRole", 60 * 60, TimeUnit.SECONDS);
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password1, userDetails.getAuthorities());
            String string = JSON.toJSONString(usernamePasswordAuthenticationToken);
            //将当前用户的角色信息 放入缓存大key user-auth 小key用户登录名authentication.getPrincipal()，value用户的角色列表
            redisTemplate.opsForHash().put("user-auth", authentication.getPrincipal().toString(), string);
            if (!encodePass.equals(password1)) {
                throw new BadCredentialsException("用户名或密码不正确");
            }
            return usernamePasswordAuthenticationToken;

        }


    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    public boolean isPhone(String phone) {
        return PATTERN_PHONE.matcher(phone).matches();
    }
}
