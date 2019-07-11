package com.sxbang.friday.security;

import com.sxbang.friday.security.authentication.MyAuthenctiationFailureHandler;
import com.sxbang.friday.security.authentication.MyAuthenticationSuccessHandler;
import com.sxbang.friday.security.authentication.MyLogoutSuccessHandler;
import com.sxbang.friday.security.exception.RestAuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
/**
 * @author kaneki
 */
@Component
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    // 密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 身份验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // http安全请求处理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 解决set 'X-Frame-Options' to 'deny'问题
        http.headers().frameOptions().sameOrigin();
        // authorizeRequests()开始请求权限配置
        // antMatchers().permitAll() 允许某些请求不需要验证
        // anyRequest().authenticated() 任何请求都需要验证
        http.authorizeRequests()
                .antMatchers("/login.html", "/xadmin/**", "/treetable-lay/**", "/ztree/**", "/static/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        // invalidateHttpSession(true) 使session失效
        // deleteCookies("JSESSIONID") 删除cookie
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
                .and().logout().permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(myLogoutSuccessHandler);
        // 处理异常跳转到403.html
        http.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler);
    }
}
