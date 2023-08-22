package hr.algebra.javawebprojekt.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] resources = new String[]{
                "/",
                "/store/**",
                "/auth/login"
        };

        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user/**").hasAuthority("USER")
                        .requestMatchers(resources).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login/process")
                        .defaultSuccessUrl("/store", true)
                        .permitAll())
                .logout((logout) -> logout
                        .permitAll()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/store")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        final String sqlUserName = "select u.username, u.password, u.enabled from [users] u where u.username = ?";
        final String sqlAuthorities = "select a.username, a.authority from [authorities] a where a.username = ?";

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(sqlUserName)
                .authoritiesByUsernameQuery(sqlAuthorities)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
