package com.zbf.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbf.common.entity.my.BaseUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    IPage listpage(Page page, BaseUser vo);

    BaseUser finloginName( String loginName);

    boolean updatestatus(Integer status,Long id);

    List<BaseUser> getlist(@Param("pai")String pai,String ord, @Param("limi") Integer limi, @Param("names")String names, @Param("rname")String rname);

}
