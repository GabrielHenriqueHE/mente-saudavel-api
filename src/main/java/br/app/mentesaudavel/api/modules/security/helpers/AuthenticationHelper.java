package br.app.mentesaudavel.api.modules.security.helpers;

import br.app.mentesaudavel.api.modules.authentication.domain.model.UserDetailsImpl;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationHelper {

    public static User getAuthenticatedUser() {
        UserDetailsImpl authenticatedUserDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return authenticatedUserDetails.getUser();
    }
}
