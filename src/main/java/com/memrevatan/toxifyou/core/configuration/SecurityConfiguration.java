package com.memrevatan.toxifyou.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity // Websecurity için bu class'ı enable et. Configuration'ı buradan al demiş olduk.
@EnableGlobalMethodSecurity(prePostEnabled = true) // UserController içerisinde anlatıldı..
public class SecurityConfiguration {

    private final UserAuthService userDetailsService;

    @Autowired
    public SecurityConfiguration(UserAuthService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable(); // Geneleksel yapıda form app arkasında client tarafına request atmak için gizli bir input(csrf) bulunur. Bunu disable ediyoruz. İhtiyacımız yok.

        //HttpSecurity belirli http istekleri için web tabanlı güvenliğin yapılandırılmasına izin verir.
        //httpBasic ile hangi authentication kullandığını belirttim.
        httpSecurity.httpBasic().authenticationEntryPoint(new AuthEntryPoint());

        httpSecurity // permitAll asagıda olmalı sıralamayı ters yaparsak permitAll digerlerini ezecektir.
                .authorizeRequests()// Request'in neye match edecegine bakması için antMatchers dedik.
                .antMatchers(HttpMethod.POST, "/api/1.0/auth").authenticated()//authenticated ile buraya gelen request içerisinde auth parametreleri bulundurmak zorunda dedik.
                .antMatchers(HttpMethod.PUT,"/api/1.0/users/{username}").authenticated()
                .and()
                .authorizeRequests().anyRequest().permitAll(); // herhangi bir request(anyRequest()) için artık authentication'a(permitAll()) bakma.

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Security ile ilgili session üretimini yapmıyor.

        return httpSecurity.build();
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
