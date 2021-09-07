package com.msun.admin.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msun.admin.common.utils.R;
import com.msun.admin.component.security.CustomFilterInvocationSecurityMetadataSource;
import com.msun.admin.component.security.CustomUrlDecisionManager;
import com.msun.admin.component.security.MD5PasswordEncoder;
import com.msun.admin.entity.po.User;
import com.msun.admin.service.IUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@Data
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    @Value("${security.salt}")
    String salt;

    @Autowired
    IUserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        MD5PasswordEncoder md5PasswordEncoder = new MD5PasswordEncoder();
        md5PasswordEncoder.getDigester().setSalt(salt.getBytes());
        return md5PasswordEncoder;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        return object;
                    }
                })
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        User user = (User) authentication.getPrincipal();
//                        将返回对象的密码置空
                        user.setUserPassword(null);

                        R ok = R.ok("登录成功").put(user);
                        String s = new ObjectMapper().writeValueAsString(ok);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        R error = R.error("登录失败！");
                        if (exception instanceof InsufficientAuthenticationException){
                            R.error("非法请求，请联系管理员！");
                        }else if (exception instanceof LockedException){
                            error.error("账户被锁定，请联系管理员");
                        }else if (exception instanceof CredentialsExpiredException){
                            error.error("密码过期，请联系管理员！");
                        }else if (exception instanceof AccountExpiredException){
                            error.error("账户过期，请联系管理员！");
                        }else if (exception instanceof DisabledException){
                            error.error("用户被禁用，请联系管理员！");
                        }else if (exception instanceof BadCredentialsException){
                            error.error("用户名或密码错误，请重新输入！");
                        }
                        out.write(new ObjectMapper().writeValueAsString(error));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                //默认/logout
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(R.ok("注销成功")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable();
        /**
         * 没有登录时，即没有认证，此处处理跨域 不要重定向（直接返回错误信息）
         * .exceptionHandling()
         *                 .authenticationEntryPoint(new AuthenticationEntryPoint() {
         *                     @Override
         *                     public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
         *
         *                     }
         *                 })
         */


    }
}
