package luis.goes.urlshortener.modules.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.modules.user.valueObject.Email;
import luis.goes.urlshortener.modules.user.valueObject.Password;

@Embeddable
@NoArgsConstructor
@Getter
public class UserCredentials {
    @Column(name = "email", nullable = false, unique = true)
    private Email email;

    @Column(name = "password", nullable = false)
    private Password password;

    public UserCredentials(String email, String password) {
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public void changeEmail(String newEmail) {
        this.email = new Email(newEmail);
    }

    public void changePassword(String newPassword) {
        this.password = new Password(newPassword);
    }

}