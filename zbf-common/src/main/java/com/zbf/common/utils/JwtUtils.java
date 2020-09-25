package com.zbf.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: LCG
 * 作者: LCG
 * 日期: 2020/9/7  19:27
 * 描述:
 */
public class JwtUtils {

    /**
     * Jwt的加密串的过期时间，这里配置是30分钟
     */
    private static long timeout=1*60*1000;

    //设置用来接收cookie判断是否需要设置cookie时间
    private static String bo;

    /**
     * 生成JWT签名的一个Key可以使一个随机的字符串
     */
    private static String secretKey;

    static {
        secretKey = "sgefkewjhewlrlewuruewoudsbv";
    }




    /**
     * 使用JWT生成加密信息
     * @param boo  从redis取出的是否选中7天免登陆的信息
     * @return
     */
    public static void generateTokencookie(String boo) {
        bo=boo;
        System.out.println("boo=="+boo);
        System.out.println("bo=="+bo);
    }









    /**
     * 使用JWT生成加密信息
     * @param userinfo  用户的信息（该用户信息需要转为JSON字符串输入）
     * @return
     */
    public static String generateToken(String userinfo) {
        System.out.println("bbbbbbb="+bo);
        System.out.println(userinfo);
        if(bo!=null&&!bo.equals("")&&bo.equals("true")){
            System.out.println("进入了7天");
            timeout=7*24*60*60*1000L;
            Map<String, Object> map = new HashMap<String,Object>();
            //用户信息
            map.put("info", userinfo);
            //生成加密信息
            System.out.println("timeout是==="+timeout);
            String compact = Jwts.builder()
                    //payload 设置信息
                    .setClaims(map)
                    //过期时间
                    .setExpiration(new Date(System.currentTimeMillis() + timeout))
                    //加密算法名称                    //签名的键
                    .signWith(SignatureAlgorithm.HS512, secretKey).compact();

            bo="";
            return compact;
        }else{
            timeout=1*60*1000;
            Map<String, Object> map = new HashMap<String,Object>();
            //用户信息
            map.put("info", userinfo);
            //生成加密信息
            String compact = Jwts.builder()
                    //payload 设置信息
                    .setClaims(map)
                    //过期时间
                    .setExpiration(new Date(System.currentTimeMillis() + timeout))
                    //加密算法名称                    //签名的键
                    .signWith(SignatureAlgorithm.HS512, secretKey).compact();

            return compact;
        }
    }


    /**
     *
     * @param token 待解密的Token串（也就是生成的加密串）
     * @return
     */
    public static JSONObject decodeJwtTocken(String token){
        System.out.println(token);
        //根据签名的key解密token信息
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        //将用户信息转为JSONObject返回
        System.out.println(claims);
        JSONObject userinfo = new JSONObject();
        userinfo.put("info",claims.get ( "info" ).toString ());
        return userinfo;
    }

    /**
     * 作者: LCG
     * 日期: 2020/9/7  21:33
     * 描述: 进行解密
     */
    public static Claims decodeJwtTocken2(String token,String secretKey) throws UnsupportedEncodingException {
        //根据签名的key解密token信息
        Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 随机的生成一个签名的key
     * @param args
     */
    public static void main(String[] args) throws UnsupportedEncodingException {

        String iii="YW5nc2FuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiI2MWJlYWIzMy1lMTAyLTRlZjQtYWUzNy1hYTI5M2I0NGRiNGUiLCJjbGllbnRfaWQiOiJ6YmYtc2VydmVyMSIsInNjb3BlIjpbImFsbCJdfQ.iwt37kBXyYm4JaMDFr8N5yNaCtaiGrhccxBG-yfvYE0";


    }

}
