package com.msun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msun.admin.common.utils.PageUtils;
import com.msun.admin.entity.dto.PermissionWithRolesDto;
import com.msun.admin.entity.po.Permission;

import java.util.Map;
import java.util.List;

/**
 * 
 *
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
public interface IPermissionService extends IService<Permission> {

    PageUtils queryPage(Map<String, Object> params);

    List<Permission> getAll();

    List<PermissionWithRolesDto> getAllPermissionsWithRoles();

}

