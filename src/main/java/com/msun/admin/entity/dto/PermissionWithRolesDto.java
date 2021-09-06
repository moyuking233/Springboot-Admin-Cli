package com.msun.admin.entity.dto;

import com.msun.admin.entity.po.Permission;
import com.msun.admin.entity.po.Role;
import lombok.Data;

import java.util.List;

//继承了Permission,表示每个Permission分别对应了多个角色
@Data
public class PermissionWithRolesDto extends Permission{
    private List<Role> roles;
}
