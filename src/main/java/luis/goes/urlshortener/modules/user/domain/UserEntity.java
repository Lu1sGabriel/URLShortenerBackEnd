package luis.goes.urlshortener.modules.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.core.shared.mapper.entityToDto.Mappable;
import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.url.domain.URLEntity;
import luis.goes.urlshortener.modules.userAuthority.domain.UserAuthorityEntity;
import luis.goes.urlshortener.modules.valueObject.Name;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_db")
@NoArgsConstructor
@Getter
public class UserEntity implements Mappable {

    @Id
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false, unique = true))
    private Name name;

    @Embedded
    private UserCredentials userCredentials;

    @Embedded
    public UserDateInfo dateInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<UserAuthorityEntity> userAuthorities = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<URLEntity> urls = new HashSet<>();

    public UserEntity(String name, String email, String password, List<AuthorityEntity> authorities) {
        this.id = UUID.randomUUID();
        this.name = new Name(name);
        this.userCredentials = new UserCredentials(email, password);
        this.dateInfo = new UserDateInfo();

        authorities.forEach(permission ->
                this.userAuthorities.add(new UserAuthorityEntity(this, permission))
        );

    }

    public void addAuthority(UserAuthorityEntity userAuthorityEntity) {
        this.userAuthorities.add(userAuthorityEntity);
    }

    public void removeAuthority(UserAuthorityEntity userAuthorityEntity) {
        this.userAuthorities.remove(userAuthorityEntity);
    }

    public void changeName(String name) {
        this.name = new Name(name);
        this.getDateInfo().update();
    }

    public void changeEmail(String email) {
        this.userCredentials.changeEmail(email);
        this.getDateInfo().update();
    }

    public void changePassword(String newPassword, String confirmedPassword) {
        if (!newPassword.equals(confirmedPassword)) throw HttpException.badRequest("Password are not equals");
        this.userCredentials.changePassword(newPassword);
        this.getDateInfo().update();
    }

    public void isPasswordMatches(String encodedPassword, String rawPassword) {
        this.userCredentials.getPassword().isPasswordMatches(encodedPassword, rawPassword);
    }

}