package com.examplemynotes.notes.security;

import com.examplemynotes.notes.filter.CustomAuthenticationFilter;
import com.examplemynotes.notes.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;

        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        public SecurityConfiguration(UserDetailsService userDetailsService,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
                this.userDetailsService = userDetailsService;
                this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                CustomAuthenticationFilter customAuthFilter =
                                new CustomAuthenticationFilter(authenticationManagerBean());
                customAuthFilter.setFilterProcessesUrl("/api/login");
                http.csrf().disable();
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                // http.authorizeRequests().antMatchers("/api/login/**").permitAll();
                http.authorizeRequests().antMatchers("/api/user/{username}/**").access(
                                "hasRole('ROLE_ADMIN') or @userSecurity.hasUsername(authentication,#username)");
                http.authorizeRequests().antMatchers("/api/notes/user/{userId}/**").access(
                                "hasRole('ROLE_ADMIN') or @userSecurity.hasUserId(authentication,#userId)");

                http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/**")
                                .hasAnyAuthority(
                                                "ROLE_ADMIN");

                http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/notes/**")
                                .hasAnyAuthority("ROLE_ADMIN");

                http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/save/**")
                                .hasAnyAuthority("ROLE_ADMIN");

                http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/notes/save/**")
                                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");

                http.addFilter(customAuthFilter);
                http.addFilterBefore(customAuthorizationFilter(),
                                UsernamePasswordAuthenticationFilter.class);
        }

        @Bean
        public CustomAuthorizationFilter customAuthorizationFilter() {
                return new CustomAuthorizationFilter();
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
                return super.authenticationManager();
        }

        @Component("userSecurity")
        public class UserSecurity {
                public boolean hasUsername(Authentication authentication, String username) {
                        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

                        return user.getUsername().equals(username);
                }

                public boolean hasUserId(Authentication authentication, Long id) {
                        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
                        return user.getId().equals(id);
                }
        }

}
