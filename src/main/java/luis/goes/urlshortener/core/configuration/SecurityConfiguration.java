package luis.goes.urlshortener.core.configuration;

import luis.goes.urlshortener.core.exception.CustomAccessDeniedHandler;
import luis.goes.urlshortener.core.exception.CustomAuthenticationEntryPointHandler;
import luis.goes.urlshortener.core.filters.CheckUserEnabledFilter;
import luis.goes.urlshortener.core.authorities.IRouterAuthoritySecurity;
import luis.goes.urlshortener.core.filters.CookieJwtAuthenticationFilter;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final List<IRouterAuthoritySecurity> securityMatchers;
    private final UserRepository userRepository;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final JwtDecoder jwtDecoder;

    public SecurityConfiguration(
            List<IRouterAuthoritySecurity> securityMatchers,
            UserRepository userRepository,
            JwtAuthenticationConverter jwtAuthenticationConverter,
            JwtDecoder jwtDecoder
    ) {
        this.securityMatchers = securityMatchers;
        this.userRepository = userRepository;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
        this.jwtDecoder = jwtDecoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    for (IRouterAuthoritySecurity matcher : securityMatchers) {
                        matcher.configure(auth);
                    }
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(new CookieJwtAuthenticationFilter(jwtDecoder, jwtAuthenticationConverter), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CheckUserEnabledFilter(userRepository), CookieJwtAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPointHandler())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}