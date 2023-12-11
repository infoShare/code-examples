package infoshare.springboot404.filter;

import infoshare.springboot404.proxy.AuthenticationExceptionProxy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class APIKeyAuthFilter extends RequestHeaderAuthenticationFilter {

    private final AuthenticationExceptionProxy authenticationExceptionProxy;
    private final String principalRequestHeader;

    @Autowired
    public APIKeyAuthFilter(ApiKeyAuthenticationManager apiKeyAuthenticationManager,
                            AuthenticationExceptionProxy authenticationExceptionProxy,
                            @Value("${config.auth.headerName:key}") String principalRequestHeader) {
        this.authenticationExceptionProxy = authenticationExceptionProxy;
        this.principalRequestHeader = principalRequestHeader;
        super.setPrincipalRequestHeader(principalRequestHeader);
        super.setAuthenticationManager(apiKeyAuthenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(request, response, chain);
        } catch (AccessDeniedException ex) {
            authenticationExceptionProxy.broadcast((HttpServletRequest) request, (HttpServletResponse) response, ex);
        }
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String principal = request.getHeader(principalRequestHeader);
        if (principal == null) {
            throw new AccessDeniedException("Authorization required");
        }
        return principal;
    }
}