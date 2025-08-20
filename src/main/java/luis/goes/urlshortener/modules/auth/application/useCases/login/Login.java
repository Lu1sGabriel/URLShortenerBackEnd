package luis.goes.urlshortener.modules.auth.application.useCases.login;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.core.authentication.AuthenticationService;
import luis.goes.urlshortener.core.authentication.UserAuthenticated;
import luis.goes.urlshortener.modules.auth.presentation.dto.AuthRequestDTO;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class Login implements ILogin {

    private final UserRepository repository;
    private final AuthenticationService authenticationService;

    public Login(UserRepository repository, AuthenticationService authenticationService) {
        this.repository = repository;
        this.authenticationService = authenticationService;
    }

    @Override
    public String login(AuthRequestDTO dto) {
        UserEntity user = repository
                .findByUserCredentials_Email_Email(dto.email())
                .orElseThrow(() -> HttpException.notFound("User not found with the given email."));

        if (user.getDateInfo().getDeletedAt() != null)
            throw HttpException.badRequest("This user is deactivated. Please contact us to enable it again.");

        user.isPasswordMatches(user.getUserCredentials().getPassword().getValue(), dto.password());

        UserDetails userDetails = new UserAuthenticated(user);

        return authenticationService.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
    }
}
