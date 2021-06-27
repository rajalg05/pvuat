package com.audit.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_LIST = { //
                     "/v2/api-docs", //
                     "/configuration/ui", //
                     "/swagger-resources", //
                     "/configuration/security", //
                     "/swagger-ui.html", //
                     "/webjars/**" //
     };

     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER").and().withUser("admin")
                             .password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
     }

     @Override
     protected void configure(HttpSecurity http) throws Exception {
            // http.authorizeRequests().antMatchers(AUTH_LIST).authenticated().and().httpBasic();
    	    //http.cors().and().csrf().disable();
    	 String[] AUTH_WHITELIST = {
    	            // -- swagger ui
    	            "/v2/api-docs", 
    	            "/swagger-resources/**", 
    	            "/configuration/ui",
    	            "/configuration/security", 
    	            "/swagger-ui.html",
    	            "/webjars/**",
    	            "/http://localhost:4200/*"
    	        };

    	        http
    	            .csrf().disable()
    	            .authorizeRequests()
    	            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    	            .antMatchers(AUTH_WHITELIST).permitAll() // whitelist URL permitted
    	            .antMatchers("/api").authenticated(); // others need auth
     }
     @Bean
     CorsConfigurationSource corsConfigurationSource() {
         CorsConfiguration configuration = new CorsConfiguration();
         configuration.setAllowedOrigins(Arrays.asList("*"));
         configuration.setAllowedMethods(Arrays.asList("*"));
         configuration.setAllowedHeaders(Arrays.asList("*"));
         configuration.setAllowCredentials(true);
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**", configuration);
         return source;
     }
     @Bean
     public PasswordEncoder passwordEncoder() {
             return new BCryptPasswordEncoder();
     }
}