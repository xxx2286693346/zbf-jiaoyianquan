package com.zbf.user.service;

import com.zbf.common.entity.my.BaseUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-11
 */
public interface IBaseUserService extends IService<BaseUser> {
    boolean adduserrole(@Param("id") long id);

    BaseUser maxid();

}
