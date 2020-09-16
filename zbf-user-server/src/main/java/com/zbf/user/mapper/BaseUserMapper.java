package com.zbf.user.mapper;

import com.zbf.common.entity.my.BaseUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

}
