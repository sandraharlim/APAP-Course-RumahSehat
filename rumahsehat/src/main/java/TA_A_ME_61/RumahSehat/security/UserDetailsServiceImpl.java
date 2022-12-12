package TA_A_ME_61.RumahSehat.security;

import TA_A_ME_61.RumahSehat.model.*;
import TA_A_ME_61.RumahSehat.repository.AdminDb;
import TA_A_ME_61.RumahSehat.repository.ApotekerDb;
import TA_A_ME_61.RumahSehat.repository.DokterDb;
import TA_A_ME_61.RumahSehat.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private DokterDb dokterDb;

    @Autowired
    private ApotekerDb apotekerDb;

    @Autowired
    private PasienDb pasienDb;

    @Autowired
    private AdminDb adminDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DokterModel dokter = dokterDb.findByUsername(username);
        ApotekerModel apoteker = apotekerDb.findByUsername(username);
        PasienModel pasien = pasienDb.findByUsername(username);
        AdminModel admin = adminDb.findByUsername(username);
        if (dokter != null){
            Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
            grantedAuthorities.add(new SimpleGrantedAuthority(dokter.getRole()));
            return new User(dokter.getUsername(), dokter.getPassword(), grantedAuthorities);
        } else if (apoteker != null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
            grantedAuthorities.add(new SimpleGrantedAuthority(apoteker.getRole()));
            return new User(apoteker.getUsername(), apoteker.getPassword(), grantedAuthorities);
        } else if (pasien != null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
            grantedAuthorities.add(new SimpleGrantedAuthority(pasien.getRole()));
            return new User(pasien.getUsername(), pasien.getPassword(), grantedAuthorities);
        } else{
            Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
            grantedAuthorities.add(new SimpleGrantedAuthority(admin.getRole()));
            return new User(admin.getUsername(), admin.getPassword(), grantedAuthorities);
        }
    }
}
