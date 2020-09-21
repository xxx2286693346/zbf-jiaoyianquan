package com.zbf.user.mapper;

import com.zbf.common.entity.my.BaseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.zbf.user.mapper
 * @author:袁成龙
 * @createTime:2020/9/21 7:30
 * @version:1.0
 */
@FeignClient(value = "zbf-deupload-server")
@Component
public interface FeignMapper {

    @RequestMapping("/exec/getexec")
    public boolean getlistexec(@RequestBody List<BaseUser> list);

    @RequestMapping("/exec/getname")
    public boolean getname(@RequestParam("coluName") String coluName, @RequestParam("fiedname") String fiedname);
}
