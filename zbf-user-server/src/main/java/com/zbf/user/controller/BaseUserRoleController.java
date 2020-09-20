package com.zbf.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.ResponseResultEnum;
import com.zbf.common.entity.my.BaseUserRole;
import com.zbf.user.service.IBaseUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-18
 */
@RestController
@RequestMapping("/baseUserRole")
public class BaseUserRoleController {

    @Autowired
    private IBaseUserRoleService userRoleService;

    ResponseResult responseResult = new ResponseResult();

    /**
     * @Author 袁成龙
     * @Description //TODO 为角色绑信息
     * @Date 16:23 2020/9/18
     * @Param
     * @return
     **/
    @RequestMapping("addUserRole")
    public ResponseResult addUserRole(Long userId, String value){
       String str = value.substring(0,value.length()-1);
        System.out.println("====="+str);
        String[] split = str.split(",");
        if(split!=null&&split.length>0){
            BaseUserRole baseUserRole = new BaseUserRole();
            for (String rid:split) {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("userId",userId);
                queryWrapper.eq("roleId",rid);
                BaseUserRole one = userRoleService.getOne(queryWrapper);
                if(one!=null){
                    responseResult.setCode(ResponseResultEnum.SECCESS.getCode());
                    System.out.println("1111");
                }else{
                    baseUserRole.setRoleId(Long.valueOf(rid).longValue());
                    baseUserRole.setUserId(Long.valueOf(String.valueOf(userId)).longValue());
                    boolean save = userRoleService.save(baseUserRole);
                    if(save){
                        responseResult.setCode(ResponseResultEnum.SECCESS.getCode());
                    }else{
                        responseResult.setCode(ResponseResultEnum.FAILURE.getCode());
                    }
                }
            }
        }
        return responseResult;
    }


}

