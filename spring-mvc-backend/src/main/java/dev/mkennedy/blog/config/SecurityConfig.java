package dev.mkennedy.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurity webSecurity() {
        return new WebSecurity();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/users/{username}/**")
                    .access("@webSecurity.checkUsername(authentication,#username)")
                .antMatchers(HttpMethod.PATCH, "/api/v1/users/{username}/**")
                    .access("@webSecurity.checkUsername(authentication,#username)")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/{username}/**")
                    .access("@webSecurity.checkUsername(authentication,#username)")
                .antMatchers("/api/v1/**").permitAll()
            .and()
            .logout()
                .logoutUrl("/api/v1/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
            .csrf()
                .disable();
    }

    protected class WebSecurity {
        public boolean checkUsername(Authentication authentication, String username) {
            return authentication
                .getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals(username));
        }
    }
}
