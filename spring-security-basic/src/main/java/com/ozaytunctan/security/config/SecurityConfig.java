package com.ozaytunctan.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozaytunctan.security.filters.AuthoritiesLoggingAfterFilter;
import com.ozaytunctan.security.filters.JwtAuthenticationFilter;
import com.ozaytunctan.security.manager.EncryptionManager;
import com.ozaytunctan.security.manager.JwtTokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
//@EnableWebSecurity(debug = true)
@EnableWebSecurity()
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig
//        extends WebSecurityConfigurerAdapter
{
    private static final Long ONE_DAY = 60 * 60 * 24L;
    private final ObjectMapper objectMapper;


    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    /**
     * OLD VERSION
     *
     * @param http
     * @throws Exception
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .cors().configurationSource(corsConfiguration())//
//                .and().csrf().disable()
////                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
//                .authorizeRequests()
//                .antMatchers("/**/welcome/**").authenticated()//Secured
//                .antMatchers("/**/auth/login", "/**/auth/logout").permitAll()//Non-Secured
//                .antMatchers("/**/h2/**").permitAll()//Non-Secured
//                .and()
//                .addFilterAfter(new AuthoritiesLoggingAfterFilter(this.authenticationManagerBean()), BasicAuthenticationFilter.class)
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenManager(), objectMapper), UsernamePasswordAuthenticationFilter.class);
//    }

    /**
     * NEW VERSION
     *
     * @param http
     * @return
     * @throws Exception
     */

    @Bean
    public SecurityFilterChain httpSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.configurationSource(corsConfiguration()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//Rest api STATELESS,JSESSION cookie not created
                .authorizeRequests((auth) -> {
                    auth.antMatchers("/**/welcome/**").authenticated();//Secured
                    auth.antMatchers("/**/auth/login", "/**/auth/logout").permitAll();//Non-Secured
                    auth.antMatchers("/**/h2/**").permitAll();//Non-Secured
                })
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenManager(), objectMapper), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * NEW
     * Dao Authentication configure
     *
     * @param http
     * @param passwordEncoder
     * @param userDetailsService
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
/**
 *OLD
 */
    //    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//       auth.inMemoryAuthentication()
//               .withUser("test").password("1234").authorities("USER")
//               //.roles("READ")
//               .and().withUser("ozaytunctan").password("1234").authorities("ADMIN")
//               //.roles("READ","WRITE")
//               .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

    //Yöntem2
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        InMemoryUserDetailsManager userDetailsService=new InMemoryUserDetailsManager();
//        UserDetails admin= User.withUsername("ozaytunctan").password("1234").authorities("ADMIN").build();
//        UserDetails user= User.withUsername("test").password("1234").authorities("USER").build();
//        userDetailsService.createUser(admin);
//        userDetailsService.createUser(user);
//        auth.userDetailsService(userDetailsService);
//    }


    //Yöntem 3
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
//
//        auth.authenticationProvider(daoAuthenticationProvider);
//    }


    /**
     * Yöntem 4 Bu kısım yapıldı
     *
     * @param
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth){
//        auth.authenticationProvider(new LdapAuthenticationProviderConfigurer<>());
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public JwtTokenManager jwtTokenManager() {
        return new JwtTokenManager(encryptionManager());
    }

    @Bean
    public EncryptionManager encryptionManager() {
        return new EncryptionManager();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(
                Arrays.asList("Authorization", "Auth-Token", "Cache-Control", "Content-Type", "Cookie", "Set-Cookie"));
        configuration.setMaxAge(ONE_DAY); // cache option request for 24 hour
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
//        return new CorsFilter(source);
        return source;
    }


}
