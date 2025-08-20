package luis.goes.urlshortener.core.shared.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

public final class JwtUtils {

    private JwtUtils() {
    }

    public static Jwt getCurrentJwt() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("Não há JWT no SecurityContext");
        }
        return jwt;
    }

    public static UUID getSubject() {
        return UUID.fromString(getCurrentJwt().getSubject());
    }

    public static List<String> getCurrentPermissions() {
        Object claim = getCurrentJwt().getClaim("permission");

        if (claim instanceof Collection<?> list) {
            return list.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .toList();
        }

        return List.of();
    }

}