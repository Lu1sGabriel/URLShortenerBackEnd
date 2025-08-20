package luis.goes.urlshortener.core.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import luis.goes.urlshortener.core.shared.dto.ErrorResponse;
import luis.goes.urlshortener.core.shared.utils.JwtUtils;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CheckUserEnabledFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public CheckUserEnabledFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Jwt) {
            var userOpt = userRepository.findById(JwtUtils.getSubject());
            if (userOpt.isEmpty() || userOpt.get().getDateInfo().getDeletedAt() != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                String jsonError = new ObjectMapper().writeValueAsString(
                        new ErrorResponse("This user account is deactivated. Contact us to enable it again.")
                );

                response.getWriter().write(jsonError);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}