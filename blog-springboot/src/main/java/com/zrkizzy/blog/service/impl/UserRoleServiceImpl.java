package com.zrkizzy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrkizzy.blog.annotation.LogAnnotation;
import com.zrkizzy.blog.entity.UserRole;
import com.zrkizzy.blog.mapper.UserRoleMapper;
import com.zrkizzy.blog.service.UserRoleService;
import com.zrkizzy.blog.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户ID获取角色ID
     *
     * @param userId 用户ID
     * @return 角色ID
     */
    @Override
    public Integer getRoleByUserId(Integer userId) {
        return userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", userId)).getRoleId();
    }

    /**
     * 更新用户的角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 前端响应对象
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    @LogAnnotation(module = "角色模块", description = "用户更新角色")
    public Result updateUserRole(Integer userId, Integer roleId) {
        UserRole userRole = userRoleMapper.selectById(userId);
        userRole.setRoleId(roleId);
        int count = userRoleMapper.updateById(userRole);
        if (count > 0) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
}
