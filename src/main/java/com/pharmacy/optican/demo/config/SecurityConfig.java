package com.pharmacy.optican.demo.config;
import com.pharmacy.optican.demo.security.SecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@PropertySource("classpath:static/property/security.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .formLogin().loginPage(sp().getLoginPage())
                .defaultSuccessUrl(sp().getMainPage(),true)
                .and()
                .authorizeRequests()
                .mvcMatchers(sp().getLoginPage(),sp().getRegistrationPage()).anonymous()
                .mvcMatchers(sp().getCssDir(),sp().getMainPage()).permitAll()
                .anyRequest().authenticated();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityProperty sp(){
        return new SecurityProperty();
    }
}
