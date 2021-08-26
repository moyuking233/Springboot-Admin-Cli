package com.msun.admin.entity.dto;

import com.msun.admin.entity.po.Permission;
import com.msun.admin.entity.po.Role;
import lombok.Data;

import java.util.List;


@Data
public class PermissionDto extends Permission{
    private List<Role> roles;
}
