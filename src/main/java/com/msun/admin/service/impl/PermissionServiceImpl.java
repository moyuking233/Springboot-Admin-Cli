package com.msun.admin.service.impl;

import com.msun.admin.entity.dto.PermissionWithRolesDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msun.admin.common.utils.PageUtils;
import com.msun.admin.common.utils.Query;

import com.msun.admin.dao.PermissionDao;
import com.msun.admin.entity.po.Permission;
import com.msun.admin.service.IPermissionService;


@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements IPermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Permission> page = this.page(
                new Query<Permission>().getPage(params),
                new QueryWrapper<Permission>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Permission> getAll() {
        return permissionDao.getAll();
    }

    @Override
    public List<PermissionWithRolesDto> getAllPermissionsWithRoles() {
        return permissionDao.getAllPermissionsWithRoles();
    }


}