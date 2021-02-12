package com.umbrellait.spring_Security.configs;

import com.umbrellait.spring_Security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/news/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/sign-up").not().fullyAuthenticated()
//                .antMatchers("/read_profile/**").hasAuthority("READ_PROFILE")
                .and()
                .formLogin(form -> form
                        .loginPage("/sign-in")
                        .permitAll())
//                .loginProcessingUrl("/sign-in")
//                .defaultSuccessUrl("/")
//                .and()
                .logout().logoutSuccessUrl("/");
    }

//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2y$12$AwazSWTcOqAeRmJAnXyf0uLZ78OQICYF.NT4jJe6AuLtMnIm9pC/i")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2y$12$AwazSWTcOqAeRmJAnXyf0uLZ78OQICYF.NT4jJe6AuLtMnIm9pC/i")
//                .roles("ADMIN", "USER")
//                .build();
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        if(jdbcUserDetailsManager.userExists(user.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(user.getUsername());
//        }
//        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(admin.getUsername());
//        }
//        jdbcUserDetailsManager.createUser(user);
//        jdbcUserDetailsManager.createUser(admin);
//        return jdbcUserDetailsManager;
//    }

//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2y$12$AwazSWTcOqAeRmJAnXyf0uLZ78OQICYF.NT4jJe6AuLtMnIm9pC/i")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2y$12$AwazSWTcOqAeRmJAnXyf0uLZ78OQICYF.NT4jJe6AuLtMnIm9pC/i")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return  new BCryptPasswordEncoder();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
       DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
       authenticationProvider.setPasswordEncoder(passwordEncoder());
       authenticationProvider.setUserDetailsService(userService);
       return authenticationProvider;
    }
}
