package com.example.waterSports.security;

import com.example.waterSports.repo.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class MySecurityConfig
{
    @Autowired
    ConfigRepo configRepo;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/assets/**").permitAll()
                .requestMatchers("/water/**", "/para/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                //    .loginPage("customLoginPage.html")
                //    .usernameParameter("customHtmlNameValueForUsername")
                //    .passwordParameter("customHtmlNameValueForPassword")
                //    .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        //        .logoutSuccessUrl("/login").permitAll()
                .and()
                .rememberMe().rememberMeParameter("remember_me").key("mySecreteKey").tokenValiditySeconds(60 * 60 * 60 * 24 * 7);

        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        List<UserDetails> listUser = new ArrayList<>();
        listUser.add(
                User.builder()
                        .username(getPasswordEncoder().encode(configRepo.findOneByProp("username").getVal()))
                        .password(getPasswordEncoder().encode(configRepo.findOneByProp("password").getVal()))
                        .roles("ADMIN")
                        .build()
        );

        return new InMemoryUserDetailsManager(listUser);
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

