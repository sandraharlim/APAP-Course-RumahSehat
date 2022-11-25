package TA_A_ME_61.RumahSehat.security;

public class WebSecurityConfig {
}



//package TA_A_ME_61.RumahSehat.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/js/**").permitAll()
//                .antMatchers("/login-sso", "/validate-ticket").permitAll()
////                .antMatchers("/user/viewall").hasAuthority("Admin")
////                .antMatchers("/user/add").hasAuthority("Admin")
////                .antMatchers("/user/delete").hasAuthority("Admin")
////                .antMatchers("/penyelenggara/add").hasAuthority("Manajer")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login").permitAll();
//        return http.build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .passwordEncoder(encoder())
////                .withUser("rakha")
////                .password(encoder().encode("apapA"))
////                .roles("USER");
////    }
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//    }
//
//}
