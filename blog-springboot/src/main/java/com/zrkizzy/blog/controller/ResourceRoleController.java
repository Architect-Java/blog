package com.zrkizzy.blog.controller;


import com.zrkizzy.blog.annotation.LogAnnotation;
import com.zrkizzy.blog.service.ResourceRoleService;
import com.zrkizzy.blog.vo.Result;
import com.zrkizzy.blog.vo.ResourceRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangrongkang
 * @since 2022-09-27
 */
@RestController
@Api(tags = "ResourceRoleController")
@RequestMapping("/resource-role")
public class ResourceRoleController {
    @Resource
    private ResourceRoleService resourceRoleService;

    @ApiOperation("更新角色资源权限")
    @LogAnnotation(module = "角色管理模块", description = "更新角色资源权限")
    @PostMapping("/updateResourceRole")
    public Result updateResourceRole(@RequestBody ResourceRoleVO resourceRoleVO) {
        return resourceRoleService.updateResourceRole(resourceRoleVO.getRoleId(), resourceRoleVO.getIds());
    }
}
