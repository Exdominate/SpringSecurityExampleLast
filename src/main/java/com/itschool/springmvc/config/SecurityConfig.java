package com.itschool.springmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends  WebSecurityConfigurerAdapter{
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        auth.inMemoryAuthentication().withUser("user").password("1").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("1").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("dba").password("1").roles("DBA","ADMIN");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/","/home").access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
                .and().formLogin().loginPage("/login")
                .usernameParameter("ssoId").passwordParameter("password")
                .and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }
}
