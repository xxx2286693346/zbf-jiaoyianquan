package com.ycl.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbf.common.entity.my.BaseMenu;
import com.zbf.common.entity.my.BaseRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-16
 */
public interface BaseMenuMapper extends BaseMapper<BaseMenu> {

    List<BaseMenu> findall(@Param("loginName") String loginName, @Param("leval") Integer leval, @Param("code") Long code);

}
