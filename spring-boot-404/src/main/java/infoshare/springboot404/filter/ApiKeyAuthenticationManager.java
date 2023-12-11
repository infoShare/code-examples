package infoshare.springboot404.filter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class ApiKeyAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal.equals("CORRECT")) {
            return new PreAuthenticatedAuthenticationToken(principal, principal, Collections.emptyList());
        }
        throw new AccessDeniedException("Authorization required");
    }
}
