package com.msun.admin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msun.admin.common.utils.PageUtils;
import com.msun.admin.common.utils.Query;

import com.msun.admin.dao.RolePermissionDao;
import com.msun.admin.entity.po.RolePermission;
import com.msun.admin.service.IRolePermissionService;


@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements IRolePermissionService {
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePermission> page = this.page(
                new Query<RolePermission>().getPage(params),
                new QueryWrapper<RolePermission>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<RolePermission> getAll() {
        return rolePermissionDao.getAll();
    }

}