package com.zrkizzy.blog.controller;

import com.zrkizzy.blog.dto.RoleDTO;
import com.zrkizzy.blog.service.RoleService;
import com.zrkizzy.blog.vo.Result;
import com.zrkizzy.blog.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色模块控制器
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Api(tags = "RoleController")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/admin/getAllRoles")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @ApiOperation("更新角色")
    @PutMapping("/admin/updateRole")
    public Result updateRole(@RequestBody RoleVO roleVO) {
        return roleService.updateRole(roleVO);
    }

    @ApiOperation("新增角色")
    @PostMapping("/admin/insertRole")
    public Result insertRole(@RequestBody RoleVO roleVO) {
        return roleService.insertRole(roleVO);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/admin/deleteRole/{id}")
    public Result deleteRoleById(@PathVariable Integer id) {
        return roleService.deleteRoleById(id);
    }

    @ApiOperation("获取当前登录用户可访问的页面")
    @GetMapping("/admin/getAccessPath")
    public List<String> getAccessPath() {
        return roleService.getAccessPath();
    }
}
