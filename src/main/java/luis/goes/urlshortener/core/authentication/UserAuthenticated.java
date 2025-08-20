package luis.goes.urlshortener.core.authentication;

import luis.goes.urlshortener.modules.user.domain.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserAuthenticated implements UserDetails {
    private final UserEntity user;

    public UserAuthenticated(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getUserAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthorityName().getValue()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getUserCredentials().getPassword().getValue();
    }

    @Override
    public String getUsername() {
        return user.getId().toString();
    }

    @Override
    public boolean isEnabled() {
        return user.getDateInfo().getDeletedAt() == null;
    }

}