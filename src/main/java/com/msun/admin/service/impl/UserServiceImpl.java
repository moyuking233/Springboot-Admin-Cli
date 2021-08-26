package com.msun.admin.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msun.admin.common.utils.PageUtils;
import com.msun.admin.common.utils.Query;

import com.msun.admin.dao.UserDao;
import com.msun.admin.entity.po.User;
import com.msun.admin.service.IUserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {
    @Autowired
    private UserDao userDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<User> page = this.page(
                new Query<User>().getPage(params),
                new QueryWrapper<User>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }
}