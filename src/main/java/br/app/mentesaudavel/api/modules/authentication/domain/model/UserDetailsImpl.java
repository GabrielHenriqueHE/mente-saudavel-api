package br.app.mentesaudavel.api.modules.authentication.domain.model;

import br.app.mentesaudavel.api.modules.user.domain.enums.UserRole;
import br.app.mentesaudavel.api.modules.user.domain.enums.UserStatus;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return this.user.getStatus() == UserStatus.ACTIVE;
    }

    public UserDetailsImpl(User user) {
        this.user = user;
    }
}
