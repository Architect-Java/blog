package com.zrkizzy.blog.controller;

import com.zrkizzy.blog.dto.MenuDTO;
import com.zrkizzy.blog.dto.PermissionDTO;
import com.zrkizzy.blog.service.MenuService;
import com.zrkizzy.blog.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author zhangrongkang
 * @date 2022/8/12
 */
@Api(tags = "MenuController")
@RestController
public class MenuController {
    @Resource
    private MenuService menuService;

    @ApiOperation("获取所有角色权限")
    @GetMapping("/admin/getAllPermission")
    public List<PermissionDTO> getAllPermission() {
        return menuService.getAllPermission();
    }

    @ApiOperation("获取所有菜单列表")
    @PostMapping("/admin/getAllMenus")
    public List<MenuDTO> getAllMenus(@RequestBody MenuVO menuVO) {
        return menuService.getAllMenus(menuVO);
    }
}
