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
            var adminKapital = "Admin";
            var admin = "admin";
            var dokterKapital = "Dokter";
            var dokter = "dokter";
            var apotekerKapital = "Apoteker";
            var apoteker = "apoteker";

            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/login-sso", "/validate-ticket").permitAll()
                    .antMatchers("/obat/").hasAnyAuthority(adminKapital, apotekerKapital)
                    .antMatchers("/obat/ubah-stok/{idObat}").hasAuthority(apotekerKapital)
                    .antMatchers("/appointment/viewall").hasAnyAuthority(adminKapital, dokterKapital, admin, dokter)
                    .antMatchers("/appointment/view/{kode}").hasAnyAuthority(adminKapital,dokterKapital, admin, dokter)
                    .antMatchers("/appointment/finish/{kode}").hasAnyAuthority(dokter, dokterKapital)
                    .antMatchers("/resep/create/{idAppointment}").hasAnyAuthority(dokter, dokterKapital)
                    .antMatchers("/resep").hasAnyAuthority(adminKapital, apotekerKapital, admin, apoteker)
                    .antMatchers("/resep/detail/{id}").hasAnyAuthority(adminKapital, apotekerKapital, admin, apoteker, dokter, dokterKapital)
                    .antMatchers("/resep/confirmation").hasAnyAuthority(apotekerKapital, apoteker)

                    .antMatchers("/dokter/").hasAnyAuthority(adminKapital)
                    .antMatchers("/dokter/add").hasAnyAuthority(adminKapital)
                    .antMatchers("/dokter/update/{uuid}").hasAnyAuthority(adminKapital)
                    .antMatchers("/dokter/delete/{uuid}").hasAnyAuthority(adminKapital)

                    .antMatchers("/apoteker/").hasAnyAuthority(adminKapital)
                    .antMatchers("/apoteker/add").hasAnyAuthority(adminKapital)
                    .antMatchers("/apoteker/delete/{uuid}").hasAnyAuthority(adminKapital)

                    .antMatchers("/pasien/").hasAnyAuthority(adminKapital)
                    .antMatchers("/pasien/delete/{uuid}").hasAnyAuthority(adminKapital)

                    .antMatchers("/dokter/barchart").hasAnyAuthority(adminKapital, admin)
                    .antMatchers("/chart/line/default").hasAnyAuthority(adminKapital, admin)
                    .antMatchers("/chart/line/tahunan").hasAnyAuthority(adminKapital, admin)

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
