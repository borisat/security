package com.example.pp3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям


    public SecurityConfig(UserDetailsService userDetailsService,
                          SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/users",
                        "/newUser",
                        "/deleteUser/{id}",
                        "/makeAdmin/{id}",
                        "/api/users",
                        "/api/users/account",
                        "/currentUser",
                        "/editUser/{id}").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/api/users/user",
                        "/user").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // разрешаем входить на /user пользователям с ролью User
                .antMatchers("/", "/api/users/account", "register").permitAll() // доступность всем
                .and().formLogin()  // Spring сам подставит свою логин форму
                .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправления по ролям

//        http.authorizeRequests()
//                .antMatchers("/users",
//                        "/newUser",
//                        "/deleteUser/{id}",
//                        "/makeAdmin/{id}",
//                        "/editUser/{id}",
//                        "/user",
//                        "/",
//                        "/api/user",
//                        "register").permitAll() // доступность всем
//                .and().formLogin()  // Spring сам подставит свою логин форму
//                .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправления по ролям
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
