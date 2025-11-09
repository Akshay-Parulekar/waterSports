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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class MySecurityConfig
{
    @Autowired
    ConfigRepo configRepo;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests((request)->request
                .requestMatchers("/assets/**").permitAll()
                .requestMatchers("/water/**", "/para/**", "/db/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
        )
        .formLogin((login)->login
                //        .loginPage("/customlogin/")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
        )
        .logout((logout)->logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
        )
        .rememberMe((rememberMe)-> rememberMe
                .rememberMeParameter("remember_me")
                .key("mySecreteKey")
                .tokenValiditySeconds(60 * 60 * 60 * 24 * 7)
        )
        .csrf((csrf)->csrf.disable());

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

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}

