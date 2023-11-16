package com.group4.logindemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll() // 允许访问H2控制台
                .antMatchers("/", "/home", "/register").permitAll() // 允许访问这些页面而无需认证
                .anyRequest().authenticated() // 其他所有请求都需要认证
                .and()
                .formLogin()
                .loginPage("/login") // 设置登录页面
                .permitAll() // 允许所有用户访问登录页面
                .and()
                .logout()
                .permitAll(); // 允许所有用户注销

        // 禁用CSRF保护以允许H2控制台的使用
        http.csrf().disable();
        // 同时禁用X-Frame-Options Header以允许H2控制台的Frames
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder()); // 使用BCrypt密码编码器
    }
}
