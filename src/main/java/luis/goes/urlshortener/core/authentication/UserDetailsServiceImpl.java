package luis.goes.urlshortener.core.authentication;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        UserAuthenticated user = userRepository.findByUserCredentials_Email_Email(email)
                .map(UserAuthenticated::new)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided email."));

        if (!user.isEnabled()) throw HttpException.badRequest("This user account has been deactivated.");

        return user;
    }

}