package my.com.fotia.osdec.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import my.com.fotia.osdec.user.model.Authority;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.model.UserAuthorities;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getOld_password(),
                mapToGrantedAuthorities(user.getUserAuthLs()),
                user.getEnabled(),
                user.getLastPasswordResetDate(),
                user.getAgency(),
                user.getImage()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserAuthorities> list) {
        return list.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getName().name()))
                .collect(Collectors.toList());
    }
}
