package com.msun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msun.admin.common.utils.PageUtils;
import com.msun.admin.entity.po.UserRole;

import java.util.Map;
import java.util.List;

/**
 * 
 *
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
public interface IUserRoleService extends IService<UserRole> {

    PageUtils queryPage(Map<String, Object> params);

    List<UserRole> getAll();

}

