package TA_A_ME_61.RumahSehat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static io.netty.util.CharsetUtil.encoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Configuration
    public static class UIWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/login-sso", "/validate-ticket").permitAll()
                    .antMatchers("/obat/").hasAnyAuthority("Admin","Apoteker")
                    .antMatchers("/obat/ubah-stok/{idObat}").hasAuthority("Apoteker")
                    .antMatchers("/appointment/view/{kode}").hasAnyAuthority("Admin","Dokter", "admin", "dokter")
                    .antMatchers("/appointment/finish").hasAnyAuthority("Dokter", "dokter")
//                .antMatchers("/penyelenggara/add").hasAuthority("Manajer")
//                .antMatchers("/user/viewall").hasAuthority("Admin")
//                .antMatchers("/user/add").hasAuthority("Admin")
//                .antMatchers("/dokter/viewall").hasAuthority("dokter")
//                .antMatchers("/apoteker/viewall").hasAuthority("apoteker")

//                .antMatchers("/user/delete").hasAuthority("Admin")
//                .antMatchers("/penyelenggara/add").hasAuthority("Manajer")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login").permitAll();
        }

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

//        @Autowired
//        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            auth.inMemoryAuthentication()
//                    .passwordEncoder(passwordEncoder)
//                    .withUser("rakha")
//                    .password(passwordEncoder.encode("apapA"))
//                    .roles("USER");
//        }
    }

    @Configuration
    @Order(1)
    public static class RestAPIWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{
        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        @Autowired
        private UserDetailsService jwtUserDetailsService;

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            // configure AuthenticationManager so that it knows from where to load
            // user for matching credentials
            // Use BCryptPasswordEncoder
            auth.userDetailsService(jwtUserDetailsService).passwordEncoder(encoder);
        }

        public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            // We don't need CSRF for this example
            httpSecurity.csrf().disable()
                    .requestMatchers(matchers -> matchers
                            .antMatchers("/authenticate")
                            .antMatchers("/sign-up")
                    )
                    // dont authenticate this particular request
                    .authorizeRequests().antMatchers("/authenticate").permitAll()
                    .antMatchers("/sign-up").permitAll()

                    // all other requests need to be authenticated
                    .anyRequest().authenticated().and()

                    // make sure we use stateless session; session won't be used to
                    // store user's state.
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // Add a filter to validate the tokens with every request
            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }
}






