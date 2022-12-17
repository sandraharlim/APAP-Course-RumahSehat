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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                    .antMatchers("/appointment/viewall").hasAnyAuthority("Admin","Dokter", "admin", "dokter")
                    .antMatchers("/appointment/view/{kode}").hasAnyAuthority("Admin","Dokter", "admin", "dokter")
                    .antMatchers("/appointment/finish").hasAnyAuthority("Dokter", "dokter")
                    .antMatchers("/resep/create/{idAppointment}").hasAnyAuthority("Dokter", "dokter")
                    .antMatchers("/resep").hasAnyAuthority("Admin","Apoteker","admin","apoteker")
                    .antMatchers("/resep/detail/{id}").hasAnyAuthority("Admin","Apoteker","admin","apoteker", "Dokter", "dokter")
                    .antMatchers("/resep/confirmation").hasAnyAuthority("Apoteker","apoteker")
                    .antMatchers("/appointment/finish/{kode}").hasAnyAuthority("Dokter", "dokter")

                    .antMatchers("/dokter/").hasAnyAuthority("Admin")
                    .antMatchers("/dokter/add").hasAnyAuthority("Admin")
                    .antMatchers("/dokter/update/{uuid}").hasAnyAuthority("Admin")
                    .antMatchers("/dokter/delete/{uuid}").hasAnyAuthority("Admin")

                    .antMatchers("/apoteker/").hasAnyAuthority("Admin")
                    .antMatchers("/apoteker/add").hasAnyAuthority("Admin")
                    .antMatchers("/apoteker/delete/{uuid}").hasAnyAuthority("Admin")

                    .antMatchers("/pasien/").hasAnyAuthority("Admin")
                    .antMatchers("/pasien/delete/{uuid}").hasAnyAuthority("Admin")

                    .antMatchers("/dokter/barchart").hasAnyAuthority("Admin", "admin")
                    .antMatchers("/chart/line/default").hasAnyAuthority("Admin", "admin")
                    .antMatchers("/chart/line/tahunan").hasAnyAuthority("Admin", "admin")

                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login").permitAll();
        }


        @Autowired
        private PasswordEncoder passwordEncoder;


       @Autowired
       public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
           auth.inMemoryAuthentication()
                   .passwordEncoder(passwordEncoder)
                   .withUser("rakha")
                   .password(passwordEncoder.encode("apapA"))
                   .roles("USER");
       }
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
                            .antMatchers("/api/appointment/doctors")
                            .antMatchers("/api/appointment/create")
                            .antMatchers("/api/appointment/viewall")
                            .antMatchers("/api/resep/detail/{id}")
                            .antMatchers("/api/pasien/profile")
                            .antMatchers("/api/pasien/profile/update-saldo")
                            .antMatchers("/sign-up/pasien")
                            .antMatchers("/api/pasien/tagihan")
                            .antMatchers("/api/pasien/tagihan/{kode}/bayar")

                    )
                    .authorizeRequests().antMatchers("/authenticate").permitAll()
                    .antMatchers("/sign-up").permitAll()

                    .anyRequest().authenticated().and()

                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }
}
