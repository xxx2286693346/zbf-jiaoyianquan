package com.zbf.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbf.common.entity.my.BaseUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-11
 */
@Mapper
public interface BaseUserMapper extends BaseMapper<BaseUser> {

    boolean adduserrole(long id);

    BaseUser maxid();

    IPage listpage(Page page, BaseUser vo);

    BaseUser finloginName(@Param("loginName") String loginName);

    boolean updatestatus(@Param("status") Integer status,@Param("id") Long id);

    List<BaseUser> getlist(@Param("pai")String pai,@Param("ord") String ord,@Param("limi") Integer limi,@Param("names")String names,@Param("rname")String rname);

}
